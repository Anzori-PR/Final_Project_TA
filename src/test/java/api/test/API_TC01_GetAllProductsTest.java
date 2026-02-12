package api.test;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("API Tests")
@Feature("Products API")
public class API_TC01_GetAllProductsTest {

    @Test(description = "API 1: Get All Products List")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify GET /api/productsList returns 200 and non-empty products list")
    public void getAllProductsList() {

        // Base URI
        RestAssured.baseURI = "https://automationexercise.com";

        // Send request
        Response response =
                RestAssured
                        .given()
                        .when()
                        .get("/api/productsList")
                        .then()
                        .extract()
                        .response();

        // Attach request & response to Allure
        Allure.addAttachment("Request", "GET /api/productsList");
        Allure.addAttachment("Response", response.asPrettyString());

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

        String body = response.getBody().asString();
        Assert.assertTrue(body.contains("products"),
                "Response should contain 'products' list. Actual body: " + body);

        // Optional: Validate that list is not empty
        int productsCount = response.jsonPath().getList("products").size();
        Assert.assertTrue(productsCount > 0, "Products list should not be empty");
    }
}
