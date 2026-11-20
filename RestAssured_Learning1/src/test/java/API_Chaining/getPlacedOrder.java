package API_Chaining;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class getPlacedOrder {

    @Test
    public void getPlacedOrders(ITestContext context){

        String token = (String) context.getAttribute("bearerToken");
        String orderId = (String) context.getAttribute("orderId");

        given()
                .baseUri("https://simple-books-api.click")
                .header("Authorization",token)
                .pathParam("id",orderId)

                .when()
                .get("/orders/{id}")

                .then()
                .log()
                .body();

    }

}

