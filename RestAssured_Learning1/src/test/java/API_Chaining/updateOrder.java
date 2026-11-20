package API_Chaining;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class updateOrder {

    @Test
    public void updateData(ITestContext context){

        String changes = """
                {
                    "customerName": "Jimmy"
                }
                """;

        String token = (String) context.getAttribute("bearerToken");
        String orderId = (String) context.getAttribute("orderId");
        System.out.println(orderId);
        given()
                .baseUri("https://simple-books-api.click")
                .header("Authorization",token)
                .pathParam("id",orderId)
                .contentType(JSON)
                .body(changes)

                .when()
                .patch("/orders/{id}")

                .then()
                .log()
                .body()
                .statusCode(204);
    }
}
