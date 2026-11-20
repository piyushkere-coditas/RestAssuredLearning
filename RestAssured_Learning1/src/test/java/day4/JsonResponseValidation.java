package day4;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class JsonResponseValidation {

    @Test
    public void getBooks(){


//        Approach 1
//        given()
//                .when()
//                .get("https://simple-books-api.click/books")
//                .then()
//                .body("[4].name",equalTo("Just as I Am"));


//        Approach 2
        Response res = given().when().get("https://simple-books-api.click/books");
//        Assert.assertEquals(res.statusCode(),200);
//        Assert.assertEquals(res.jsonPath().get("[4].name"),"Just as I Am");

        JSONArray ja = new JSONArray(res.asString());
        for (int i = 0; i < ja.length(); i++) {
            System.out.println(ja.getJSONObject(i).get("name"));
        }

//        List<String> books = res.jsonPath().getList("name");
//        for (String book: books){
//            System.out.println(book);
//        }
    }

    @Test
    public void getXMLResponse(){
        Response res =
        given()

                .when()
                .get("https://gorest.in/public/v2/users.xml");

        XmlPath Xmlobj = new XmlPath(res.asString());

        List<String> names = Xmlobj.getList("users.user.name");

        for (String name: names){
            System.out.println(name);
        }
    }


    String filename;

    @Test
    public void uploadFileRequest(){
        File file = new File("C:\\Users\\Coditas-Admin\\Desktop\\API for L2.txt");
        File file1 = new File("C:\\Users\\Coditas-Admin\\Desktop\\API for L2.txt");

        File files[] = {file,file1};

        filename =
            given()
                .multiPart("files",files)
                .when()
                .post("https://api.escuelajs.co/api/v1/files/upload")
                    .jsonPath()
                    .get("filename");
//                    .toString();

        System.out.println(filename);
    }

    @Test
    public void downloadFile(){
        Response res = given().when().get("https://easysend.co/api/v1/download/3403");
        System.out.println(res);
    }

    @Test
    public void jsonSchemaValidation(){
        given()
                .when()
                .get("http://localhost:3000/0")
                .then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("jsonSchema.json"));
    }

    @Test
    public void xmlSchemaValidation(){
        given()
                .when()
                .get("https://gorest.in/public/v2/users.xml")
                .then()
                .assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("traveller.xsd"));
    }
}
