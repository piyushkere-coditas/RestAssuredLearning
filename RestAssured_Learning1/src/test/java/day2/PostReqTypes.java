package day2;

import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class PostReqTypes {

    String token;
    String orderId;

//    @Test(priority = 0)
//    public void HashMapPostRequest(){
//        HashMap<String, String> data = new HashMap<>();
//
//        data.put("clientName", "ray");
//        data.put("clientEmail", email);
//
//        String Token =
//                given()
//                        .baseUri("https://simple-books-api.click")
//                        .contentType(JSON)
//                        .body(data)
//
//                        .when().post("/api-clients/")
//
//                        .then()
//                        .statusCode(201)
//                        .extract()
//                        .path("accessToken");
//
//        System.out.println(Token);
//    }

    @Test(priority = 1)
    public void JsonPostRequest(){
        JSONObject data = new JSONObject();
        String email = "piyush" + System.currentTimeMillis() + "@gmail.com";

        data.put("clientName", "ray");
        data.put("clientEmail", email);

        String Token =
                given()
                        .baseUri("https://simple-books-api.click")
                        .contentType(JSON)
                        .body(data.toString())

                        .when().post("/api-clients/")

                        .then()
                        .statusCode(201)
                        .extract()
                        .path("accessToken");

        System.out.println(Token);
        token = Token;
    }

    @Test(priority = 2)
    public void PojoPostRequest(){
        OrderData data = new OrderData();
        data.setBookId("3");
        data.setCustomerName("rayy");

        String orderID =
                given().baseUri("https://simple-books-api.click")
                        .header("Authorization","Bearer "+ token)
                        .contentType(JSON)
                        .body(data)

                        .when().post("/orders")

                        .then().log().all().extract().path("orderId");

        orderId =  orderID;
    }
}
