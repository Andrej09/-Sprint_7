package org.example.order;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderGenerator{

    public CreatingOrder creatingOrder(){
        return new CreatingOrder(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                null);
    }

    public ValidatableResponse colorSelection(CreatingOrder order){
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().statusCode(201)
                .log().all();
    }


}