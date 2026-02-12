package api.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

@Epic("API Testing")
@Feature("Login Operations")
public class API_TC09_DeleteVerifyLoginTest {

    @Test(description = "API 9: DELETE To Verify Login")
    @Description("Verify that DELETE method is not supported for verifyLogin endpoint")
    public void deleteToVerifyLogin() {
        // 1. Set Base URI
        RestAssured.baseURI = "https://automationexercise.com/api";

        // 2. Send DELETE Request
        Response response = RestAssured
                .given()
                .when()
                .delete("/verifyLogin")
                .then()
                .extract()
                .response();

        // 3. Verify Response Code is 405 (Not Supported)
        // Note: The API documentation specifies it returns 405 within the JSON body for this specific site
        Assert.assertEquals(response.getStatusCode(), 200, "HTTP Status should be 200 (Site design)");

        int responseCode = response.jsonPath().get("responseCode");
        String message = response.jsonPath().get("message");

        // 4. Verify Requirements
        Assert.assertEquals(responseCode, 405, "API Response Code should be 405");
        Assert.assertEquals(message, "This request method is not supported.", "Response message mismatch");
    }
}