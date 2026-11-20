package API_Chaining;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class deleteOrder {

    @Test
    public void deleteData(ITestContext context){

        String token = (String) context.getAttribute("bearerToken");
        String orderId = (String) context.getAttribute("orderId");

        given()
                .baseUri("https://simple-books-api.click")
                .header("Authorization",token)
                .pathParam("id",orderId)
                .when()
                .delete("/orders/{id}")
                .then()
                .log()
                .body();


    }

}
