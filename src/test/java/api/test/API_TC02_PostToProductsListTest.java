package api.test;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("API Tests")
@Feature("Products API")
public class API_TC02_PostToProductsListTest {

    @Test(description = "API 2: POST To All Products List")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify POST /api/productsList returns responseCode=405 and method not supported message")
    public void postToAllProductsList_shouldReturn405InBody() {

        RestAssured.baseURI = "https://automationexercise.com";

        Response response =
                RestAssured
                        .given()
                        .contentType("application/x-www-form-urlencoded")
                        .when()
                        .post("/api/productsList")
                        .then()
                        .extract()
                        .response();

        Allure.addAttachment("Request", "POST /api/productsList");
        Allure.addAttachment("HTTP Status", String.valueOf(response.getStatusCode()));
        Allure.addAttachment("Response", response.asPrettyString());

        // ✅ On this platform HTTP may be 200, but logical responseCode should be 405 (per api_list)
        int httpStatus = response.getStatusCode();
        Assert.assertTrue(httpStatus == 200 || httpStatus == 405,
                "Unexpected HTTP status. Expected 200 or 405, got: " + httpStatus);

        // ✅ Validate response body fields (what the documentation expects)
        int responseCode = response.jsonPath().getInt("responseCode");
        String message = response.jsonPath().getString("message");

        Assert.assertEquals(responseCode, 405, "responseCode should be 405");
        Assert.assertTrue(message != null && message.toLowerCase().contains("not supported"),
                "Message should say method not supported. Actual: " + message);
    }
}
