package day1;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

import day2.OrderData;
import  org.testng.annotations.Test;

import java.util.HashMap;

public class HttpRequest {

    String token;
    String orderId;

    @Test(priority = -1)
    public void getBooks(){
        get("https://simple-books-api.click/books")
                .then().log().all();
    }

    @Test(priority = 0)
    public void getBook(){
        given().pathParam("Id",1).when()

        .get("https://simple-books-api.click/books/{Id}")

        .then()
                .statusCode(200)
                .body("id",equalTo(1))
                .body("name",equalTo("The Russian"))
                .body("available",equalTo(true))
                .log()
                .all();
    }

    @Test(priority = 1)
    public void getAvailableBooks(){

        given().queryParam("available",true).queryParam("type","fiction")

        .when()
                .get("https://simple-books-api.click/books")

        .then()
                .body("type",hasItem("fiction"))
                .body("available",hasItem(true))
                .log()
                .all();

    }

    @Test(priority = 2)
    public void postRequest(){

        HashMap<String,String> clientData = new HashMap();

        String email = "piyush" + System.currentTimeMillis() + "@gmail.com";

        clientData.put("clientName","piyushk");
        clientData.put("clientEmail", email);

        String Token =
        given()
                .baseUri("https://simple-books-api.click")
                .contentType(JSON)
                .body(clientData)

        .when().post("/api-clients/")

        .then()
                .statusCode(201)
                .extract()
                .path("accessToken");

        System.out.println(Token);
        token =  Token;
    }


    @Test(priority = 2)
    public void submitOrder(){

//        HashMap data = new HashMap();
//        data.put("bookId","1");
//        data.put("customerName","Rayy");

        OrderData data = new OrderData();

        data.setBookId("1");
        data.setCustomerName("PiyushKere");

        String orderID =
        given().baseUri("https://simple-books-api.click")
                .header("Authorization","Bearer "+token)
                .contentType(JSON)
                .body(data)

        .when().post("/orders")

        .then().statusCode(201).log().all().extract().path("orderId");

        orderId =  orderID;
    }

    @Test(priority = 3)
    public void getOrders(){

        given().baseUri("https://simple-books-api.click")
                .header("Authorization","Bearer "+token)
                .pathParam("orderId",orderId)

        .when().get("/orders/{orderId}")

        .then().log().all().statusCode(200);
    }

    @Test(priority = 4)
    public void updateOrder(){

        String newBody = """
                {
                  "customerName": "KerePiyush"
                }
                """;

        given().baseUri("https://simple-books-api.click")
                .header("Authorization","Bearer "+token)
                .pathParam("orderId",orderId)
                .contentType(JSON)
                .body(newBody)

        .when().patch("orders/{orderId}")

        .then().log().all().statusCode(204);
    }

    @Test(priority = 5)
    public void deleteOrder(){
        given().baseUri("https://simple-books-api.click")
                .header("Authorization","Bearer "+token)
                .pathParam("orderId",orderId)

                .when().delete("orders/{orderId}")

                .then().log().all().statusCode(204);
    }
}
