import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.CourierClient;
import org.example.CourierGenerator;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class ListOrdersTest {

    private final CourierClient client = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("в теле ответа возвращается список заказов")
    public void authorizationVerification(){
        var courier = new CourierGenerator().random();
        client.create(courier);
        int courierId = client.login(courier)
                .log().all().extract().path("id");
        client.gettingListOrders(courierId).statusCode(200).body("orders", notNullValue());
        client.delete(courierId);
    }
}