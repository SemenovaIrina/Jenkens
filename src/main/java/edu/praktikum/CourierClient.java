package edu.praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class CourierClient {
    public static final String COURIER_CREATE_REQUEST = "/api/v1/courier";
    public static final String COURIER_DELETE_REQUEST = "/api/v1/courier";
    public static final String COURIER_LOGIN_REQUEST = "/api/v1/courier/login";

    @Step("Creating a courier with POST request to /api/v1/courier")
    public static Response create(Courier courier) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(COURIER_CREATE_REQUEST);
    }

    @Step("Delete a courier with DELETE request to /api/v1/courier")
    public static void delete(CourierAfterLogin courier) {
        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(COURIER_DELETE_REQUEST + "/" + courier.getId());
    }

    @Step("Login a courier with POST request to /api/v1/courier/login")
    public static Response login(CourierForLogin courier) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(COURIER_LOGIN_REQUEST);
    }
}
