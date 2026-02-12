package ui.tests;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("API Tests")
public class API_TC14_GetUserDetailTest {

    @Test(description = "API 14: GET user account detail by email")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserDetailByEmail() {
        String baseUri = "https://automationexercise.com/api/getUserDetailByEmail";
        // Recommendation: Use an email you know exists, or handle the 404
        String userEmail = "test123@gmail.com";

        Response response = RestAssured.given()
                .queryParam("email", userEmail)
                .get(baseUri);

        Allure.addAttachment("API Response", response.getBody().asPrettyString());

        int responseCode = response.jsonPath().get("responseCode");

        if (responseCode == 200) {
            Assert.assertTrue(response.asString().contains("user"), "Response should contain 'user' object");
        } else if (responseCode == 404) {
            String message = response.jsonPath().get("message");
            Assert.assertEquals(message, "Account not found with this email, try another email!",
                    "Incorrect error message for missing account.");
        } else {
            Assert.fail("Unexpected response code: " + responseCode);
        }
    }
}