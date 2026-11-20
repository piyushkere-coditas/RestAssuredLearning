package API_Chaining;

import day5.OrderData;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class createOrder {
    @Test
    public void generateOrderId(ITestContext context){

        OrderData data = new OrderData();
        data.setCustomerName("rohan");
        data.setBookId("4");

        String token = (String) context.getAttribute("bearerToken");

        String orderId =
        given()
                .baseUri("https://simple-books-api.click")
                .header("Authorization", token)
                .contentType(JSON)
                .body(data)

                .when()
                .post("/orders")

                .then()
                .log()
                .body()
                .statusCode(201)
                .extract()
                .path("orderId");

        System.out.println("Generated order id "+orderId);
        context.setAttribute("orderId",orderId);
    }
}
