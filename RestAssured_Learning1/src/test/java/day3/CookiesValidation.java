package day3;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CookiesValidation {

    @Test(priority = 0)
    public void cookieValidation(){
        given()
                .when()
                .get("https://google.com/")

                .then()
                .log()
                .all();
    }

    @Test(priority = 1)
    public void getCookie(){
        Response res = given()
                .when()
                .get("https://google.com/");

        //get cookie info
//        String cookie = res.getCookie("AEC");
//        System.out.println("The cookie: "+cookie);

        //get all cookies information
        Map<String,String> cookies = res.getCookies();

        for (String k : cookies.keySet()){
            String cookieValue = res.getCookie(k);
            System.out.println(k+"    :   "+cookieValue);
        }
    }

    @Test(priority = 2)
    public void TestHeader(){
        given()
                .when()
                .get("https://google.com")

                .then()
                .header("Content-Type","text/html; charset=ISO-8859-1")
                .and()
                .header("Expires","-1");
    }

    @Test(priority = 3)
    public void getHeader(){
        Response res =
        given()
                .when()
                .get("https://google.com/");

        //get single header
//        String headerValue = res.getHeader("Content-Type");
//        System.out.println("Content-Type: "+headerValue);

        //get all headers
        Headers headers = res.getHeaders();

        for (Header h: headers){
            System.out.println(h.getName()+"      "+ h.getValue());
        }
    }

    @Test
    public void logging(){
        given()
                .when()
                .get("https://simple-books-api.click/books")
                .then()
                .log().body();
//                .log().cookies();
//                .log().headers();

    }



}
