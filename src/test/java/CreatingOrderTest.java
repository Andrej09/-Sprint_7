import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.OrderGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class CreatingOrderTest{

    private final List<String> color;

    public CreatingOrderTest( List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "цвет: {0}")
    public static Object[][] getTestAccordion() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("BLACK ", "GREY")},
                {List.of("")},
        };
    }

    @Test
    @DisplayName("1)можно указать один из цветов — BLACK или GREY, " +
            "2)можно указать оба цвета, " +
            "3)можно совсем не указывать цвет, " +
            "4)тело ответа содержит track")
    public void checkOrderGenerator() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        var order = new OrderGenerator().creatingOrder();
        order.setColor(color);
        given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().statusCode(201)
                .log().all()
                .body("track", greaterThan(0));
    }
}