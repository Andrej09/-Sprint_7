import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.courier.CourierClient;
import org.example.courier.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CouirerLoginTest {

    private final CourierClient client = new CourierClient();
    private int courierId;
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @After
    public void courierDelete() {
        if (courierId > 0) {
            client.delete(courierId).statusCode(200);
        }
    }

    @Test
    @DisplayName("1)курьер может авторизоваться, " +
                 "2)успешный запрос возвращает id")
    public void authorizationVerification(){
        var courier = new CourierGenerator().random();
        client.create(courier);
        courierId = client.login(courier)
                .statusCode(200).body("id", notNullValue())
                .log().all().extract().path("id");
    }

    @Test
    @DisplayName("если какого-то поля нет, запрос возвращает ошибку")
    public void authorizationVerificationNoRequiredFields(){
        var courier = new CourierGenerator().random();
        courier.setLogin(null);
        client.login(courier)
                .statusCode(400)
                .assertThat()
                .body("message",equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("1)система вернёт ошибку, если неправильно указать логин или пароль," +
                 "2)если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void authorizationVerificationNoUser() {
        var courier = new CourierGenerator().random();
        courier.setLogin("logon");
        client.login(courier)
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

    }
}