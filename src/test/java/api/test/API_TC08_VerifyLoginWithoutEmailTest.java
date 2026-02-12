package api.test;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

@Epic("API Testing")
@Feature("Login")
public class API_TC08_VerifyLoginWithoutEmailTest {

    String baseUrl = "https://automationexercise.com/api";

    @Test(description = "Verify that login fails when the email parameter is missing")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Case 8: POST To Verify Login without email parameter")
    public void testVerifyLoginWithoutEmail() {
        // 1. Prepare Data (Only Password, NO Email)
        Map<String, String> formData = new HashMap<>();
        formData.put("password", "password123");

        // Attach Request Data to Allure Report
        Allure.addAttachment("Request Form Data", formData.toString());

        // 2. Send POST request
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(formData)
                .post(baseUrl + "/verifyLogin");

        // Attach Response to Allure Report
        Allure.addAttachment("API Response Body", "application/json", response.getBody().asString());

        // 3. Print response for debugging
        System.out.println("TC08 Response: " + response.getBody().asString());

        // 4. Verify Status Code is 200 or 400
        // NOTE: The documentation says 400, but sometimes this API returns 200 even for errors.
        // We will assert 200 because this specific API usually returns 200 OK with an error message in the body.
        // If your specific requirement strict check is 400, change the number below to 400.
        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch!");

        // 5. Verify Response Message
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Bad request, email or password parameter is missing in POST request."),
                "Error message is incorrect! Actual: " + responseBody);
    }
}