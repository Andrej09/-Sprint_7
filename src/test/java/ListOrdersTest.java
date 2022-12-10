import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.CourierClient;
import org.example.CourierGenerator;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class ListOrdersTest {

    private final CourierClient client = new CourierClient();

    @Test
    @DisplayName("в теле ответа возвращается список заказов")
    public void authorizationVerification(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        var courier = new CourierGenerator().generic();
        client.create(courier);
        int courierId = client.login(courier)
                .log().all().extract().path("id");
        client.gettingListOrders(courierId).statusCode(200).body("orders", notNullValue());
        client.delete(courierId);
    }
}