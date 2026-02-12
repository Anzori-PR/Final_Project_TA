package api.test;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("API Testing")
@Feature("Brands")
public class API_TC03_GetAllBrandsListTest {

    String baseUrl = "https://automationexercise.com/api";

    @Test(description = "Verify that the API returns the full list of brands")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Case 3: GET All Brands List")
    public void testGetAllBrandsList() {
        // 1. Send GET request
        Response response = RestAssured.get(baseUrl + "/brandsList");

        // 2. Attach Response to Allure Report (CRITICAL for Requirement #4)
        Allure.addAttachment("API Response Body", "application/json", response.getBody().asString());

        // 3. Verify Status Code is 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        // 4. Verify JSON content
        Assert.assertTrue(response.asString().contains("brands"), "Response does not contain 'brands' list");
    }
}