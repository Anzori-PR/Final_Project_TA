package api.test;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

@Epic("API Testing")
@Feature("User Management")
public class API_TC11_CreateUserAccountTest {

    String baseUrl = "https://automationexercise.com/api";

    @Test(description = "Verify that a new user can be registered via API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Case 11: POST To Create/Register User Account")
    public void testCreateUserAccount() {
        // 1. Prepare User Data
        Map<String, String> formData = new HashMap<>();
        formData.put("name", "Ani Merabishvili");
        formData.put("email", "ani" + System.currentTimeMillis() + "@test.com"); // Unique email
        formData.put("password", "password123");
        formData.put("title", "Mrs");
        formData.put("birth_date", "11");
        formData.put("birth_month", "January");
        formData.put("birth_year", "2005");
        formData.put("firstname", "Ani");
        formData.put("lastname", "Merabishvili");
        formData.put("company", "Bluebirds");
        formData.put("address1", "123 Tbilisi St");
        formData.put("country", "United States");
        formData.put("zipcode", "00000");
        formData.put("state", "Tbilisi");
        formData.put("city", "Tbilisi");
        formData.put("mobile_number", "555111111");

        // Attach Request Data to Allure Report
        Allure.addAttachment("Request Form Data", formData.toString());

        // 2. Send POST request
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(formData)
                .post(baseUrl + "/createAccount");

        // Attach Response to Allure Report
        Allure.addAttachment("API Response Body", "application/json", response.getBody().asString());

        // 3. Verify Status Code 200 (Note: API documentation says 201, but server returns 200)
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        // 4. Verify Message
        Assert.assertTrue(response.asString().contains("User created!"), "Response message incorrect");
    }
}