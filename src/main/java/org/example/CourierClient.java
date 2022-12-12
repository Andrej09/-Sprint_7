package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient {
    public ValidatableResponse create(Courier courier){
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().log().all();
    }

    public ValidatableResponse login(Courier courier){
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().log().all();
    }

    public ValidatableResponse delete(int courierId){
        DeleteId json = new DeleteId(courierId);
        return given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then().log().all();
    }

    public ValidatableResponse gettingListOrders(int courierId){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .queryParam("?courierId=" + courierId)
                .get("/api/v1/orders")
                .then().log().all();
    }

}