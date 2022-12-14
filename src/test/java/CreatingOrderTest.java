import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.order.OrderGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class CreatingOrderTest{

    private final OrderGenerator orderGenerator = new OrderGenerator();

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

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("1)можно указать один из цветов — BLACK или GREY, " +
            "2)можно указать оба цвета, " +
            "3)можно совсем не указывать цвет, " +
            "4)тело ответа содержит track")
    public void checkOrderGenerator() {
        var order = new OrderGenerator().creatingOrder();
        order.setColor(color);
        orderGenerator.colorSelection(order).body("track", greaterThan(0)).extract().path("track");
    }
}