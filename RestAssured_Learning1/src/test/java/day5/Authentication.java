package day5;

import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.core.IsEqual.equalTo;

public class Authentication {

    String token;
    String orderId;

    @Test(priority = 0)
    public void generateToken(){

        JSONObject data = new JSONObject();

        String email = "piyush" + System.currentTimeMillis() + "@gmail.com";
        data.put("clientName","arjun");
        data.put("clientEmail",email);

        token =
                given()
                        .baseUri("https://simple-books-api.click")
                        .contentType(JSON)
                        .body(data.toString())

                        .when()
                        .post("/api-clients")

                        .then()
                        .extract()
                        .path("accessToken");

        System.out.println("Generated token: "+token);
    }

    @Test(priority = 1)
    public void bearerTokenAuthentication(){

        OrderData od = new OrderData();
        od.setBookId("5");
        od.setCustomerName("ned");

        orderId =
        given()
                .baseUri("https://simple-books-api.click")
                .header("Authorization","Bearer "+token)
                .contentType(JSON)
                .body(od)

                .when()
                .post("/orders")

                .then()
                .extract()
                .path("orderId");

        System.out.println("Created order id: "+orderId);
    }

    @Test(priority = 2)
    public void testDigestAuthentication(){
        given()
                .auth().digest("postman","password")

                .when()
                .get("https://postman-echo.com/basic-auth")

                .then()
                .body("authenticated",equalTo(true));
    }

    @Test(priority = 3)
    public void testPreemptiveAuthentication(){
        given()
                .auth().preemptive().basic("postman","password")

                .when()
                .get("https://postman-echo.com/basic-auth")

                .then()
                .body("authenticated",equalTo(true));
    }

    public void OAuth1Authentication(){
        given()
                .auth().oauth("consumerKey","consumerSecret","accessToken","tokenSecret")

                .when()
                .get("");
    }

    @Test
    public void OAuth2Authentication(){
        given()
                .auth().oauth2("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")

                .when()
                .get("https://api.github.com/user/repos")

                .then()
                .log()
                .body();
    }

    @Test
    public void ApiKeyAuthentication(){
        given()
                .header("Authorization","02ff02d977204adfbef55872ef8b1f2a")

                .when()
                .get("https://newsapi.org/v2/top-headlines?country=us")

                .then()
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    public void test_createUser(){
        Faker faker = new Faker();
        JSONObject data = new JSONObject();

        data.put("name",faker.name().fullName());
        data.put("gender","male");
        data.put("email",faker.internet().emailAddress());
        data.put("status","inactive");

        String bearerToken;
    }

}