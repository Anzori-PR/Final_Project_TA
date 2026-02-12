package api.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import core.config.ConfigReader;

@Epic("API Testing")
@Feature("Account Management")
public class API_TC12_DeleteAccountTest {

    @Test(description = "API: DELETE To Delete Account")
    public void deleteAccountTest() {
        // Try using the full absolute URL to bypass baseURI issues
        Response response = RestAssured
                .given()
                .filter(new io.qameta.allure.restassured.AllureRestAssured())
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", ConfigReader.get("testUserEmail"))
                .formParam("password", ConfigReader.get("testUserPassword"))
                .when()
                .delete("https://automationexercise.com/api/deleteAccount"); // Full URL

        // Log the body so you can see the error message in the console
        System.out.println("Response Body: " + response.asString());

        int responseCode = response.jsonPath().get("responseCode");
        Assert.assertEquals(responseCode, 200, "API Response code should be 200");
    }
}