package api.client;

import io.restassured.response.Response;

public final class ProductsApi {
    private ProductsApi() {}

    public static Response getAllProducts() {
        return ApiBase.req()
                .when()
                .get("/productsList")
                .then()
                .extract().response();
    }
}
