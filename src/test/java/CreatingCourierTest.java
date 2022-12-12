import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.CourierClient;
import org.example.CourierGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CreatingCourierTest {

    private final CourierClient client = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("1)курьера можно создать, " +
                 "2)успешный запрос возвращает ok: true, " +
                 "3)запрос возвращает правильный код ответа")
    public void creatingCourier() {
        var courier = new CourierGenerator().random();
        client.create(courier)
                .statusCode(201)
                .assertThat().body("ok", is(true));
        int courierId = client.login(courier)
                .log().all().extract().path("id");
        client.delete(courierId);
    }

    @Test
    @DisplayName("1)нельзя создать двух одинаковых курьеров, " +
                 "2)если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void twoIdenticalLogins() {
        var courier = new CourierGenerator().random();
        client.create(courier);
        client.create(courier)
                .assertThat().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        int courierId = client.login(courier)
                .log().all().extract().path("id");
        client.delete(courierId);
    }

    @Test
    @DisplayName("если одного из полей нет, запрос возвращает ошибку")
    public void creatingCareeWithNotAllData() {
        var courier = new CourierGenerator().random();
        courier.setPassword(null);
        client.create(courier)
                .statusCode(400).assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}