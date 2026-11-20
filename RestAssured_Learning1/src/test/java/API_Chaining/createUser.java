package API_Chaining;

import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class createUser {

    @Test(priority = 0)
    public void generateToken(ITestContext context){

        JSONObject data = new JSONObject();

        String email = "piyush" + System.currentTimeMillis() + "@gmail.com";
        data.put("clientName","arjun");
        data.put("clientEmail",email);

        String token =
                given()
                        .baseUri("https://simple-books-api.click")
                        .contentType(JSON)
                        .body(data.toString())

                        .when()
                        .post("/api-clients")

                        .then()
                        .extract()
                        .path("accessToken");

        context.setAttribute("bearerToken","Bearer "+token);
        System.out.println("Received bearer token: "+token);

    }
}
