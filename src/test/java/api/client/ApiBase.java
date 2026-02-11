package api.client;

import core.config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public final class ApiBase {
    private ApiBase() {}

    public static RequestSpecification req() {
        RestAssured.baseURI = ConfigReader.get("apiBaseUrl");
        return RestAssured
                .given()
                .filter(new AllureRestAssured()); // attaches request/response to Allure
    }
}
