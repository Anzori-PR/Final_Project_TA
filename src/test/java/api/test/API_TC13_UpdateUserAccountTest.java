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
public class API_TC13_UpdateUserAccountTest {

    String baseUrl = "https://automationexercise.com/api";

    @Test(description = "Verify that user account details can be updated via API")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Case 13: PUT METHOD To Update User Account")
    public void testUpdateUserAccount() {
        // --- STEP 1: SETUP - Create a user first so we have someone to update ---
        // We use a unique email so this test can run multiple times without conflict
        String userEmail = "ani_update" + System.currentTimeMillis() + "@test.com";
        String userPassword = "password123";

        // Create Data (Initial values)
        Map<String, String> createData = new HashMap<>();
        createData.put("name", "Ani Original");
        createData.put("email", userEmail);
        createData.put("password", userPassword);
        createData.put("title", "Mrs");
        createData.put("birth_date", "11");
        createData.put("birth_month", "January");
        createData.put("birth_year", "2005");
        createData.put("firstname", "Ani");
        createData.put("lastname", "Original");
        createData.put("company", "CX Hub");
        createData.put("address1", "Old Address St");
        createData.put("country", "United States");
        createData.put("zipcode", "00000");
        createData.put("state", "Old State");
        createData.put("city", "Old City");
        createData.put("mobile_number", "111111111");

        // Send Create Request (This is just setup, so no assertions needed here)
        RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(createData)
                .post(baseUrl + "/createAccount");

        // --- STEP 2: TEST - Now Update that user ---
        // Prepare New Data (Updated values)
        Map<String, String> updateData = new HashMap<>();
        updateData.put("name", "Ani Updated");          // Changing Name
        updateData.put("email", userEmail);             // MUST match the created email
        updateData.put("password", userPassword);
        updateData.put("title", "Mrs");
        updateData.put("birth_date", "11");
        updateData.put("birth_month", "January");
        updateData.put("birth_year", "2005");
        updateData.put("firstname", "Ani");
        updateData.put("lastname", "Updated");          // Changing Lastname
        updateData.put("company", "CX Hub Updated");    // Changing Company
        updateData.put("address1", "New Address St");   // Changing Address
        updateData.put("address2", "Apt 5");
        updateData.put("country", "United States");
        updateData.put("zipcode", "99999");
        updateData.put("state", "New State");
        updateData.put("city", "New City");
        updateData.put("mobile_number", "999999999");

        // Attach Request Data to Allure Report
        Allure.addAttachment("Update Request Data", updateData.toString());

        // Send PUT request
        Response response = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(updateData)
                .put(baseUrl + "/updateAccount");

        // Attach Response to Allure Report
        Allure.addAttachment("API Response Body", "application/json", response.getBody().asString());

        System.out.println("TC13 Response: " + response.getBody().asString());

        // 3. Verify Status Code 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        // 4. Verify Message "User updated!"
        Assert.assertTrue(response.asString().contains("User updated!"), "Response message incorrect");
    }
}