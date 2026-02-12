package api.test;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
@Epic("API Tests")
@Feature("Product Search")
public class API_TC05_SearchProductTest {@Test(description = "API 5: POST To Search Product")
@Severity(SeverityLevel.CRITICAL)
@Description("Verify that a POST request with the 'search_product' parameter returns a list of searched products.")
public void testPostSearchProduct() {
    String baseUri = "https://automationexercise.com/api/searchProduct";
    String searchKeyword = "tshirt";

    // Step: Send POST request with form parameter
    Response response = RestAssured.given()
            .contentType("application/x-www-form-urlencoded")
            .formParam("search_product", searchKeyword)
            .post(baseUri);

    // Allure Requirement: Attach Request/Response to Report
    Allure.addAttachment("API Request URL", baseUri);
    Allure.addAttachment("Search Parameter", searchKeyword);
    Allure.addAttachment("API Response Body", response.getBody().asPrettyString());

    // Assertions
    Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

    // Verify response contains the products list
    String responseBody = response.getBody().asString();
    Assert.assertTrue(responseBody.contains("products"), "Response body does not contain 'products' key.");
    Assert.assertTrue(responseBody.contains("name"), "Response body does not contain product 'name' details.");
}

}
