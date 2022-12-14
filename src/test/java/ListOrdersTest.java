import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.courier.CourierClient;
import org.example.courier.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class ListOrdersTest {

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
    @DisplayName("в теле ответа возвращается список заказов")
    public void authorizationVerification(){
        var courier = new CourierGenerator().random();
        client.create(courier);
        courierId = client.login(courier).log().all().extract().path("id");
        client.gettingListOrders(courierId).statusCode(200).body("orders", notNullValue());
    }
}