package day5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SerializationDeserialization {


    //SERIALIZATION PROCESS
    @Test
    public void convertPOJOtoJson() throws JsonProcessingException {

        //create java object using pojo
        OrderData od = new OrderData();
        od.setBookId("1");
        od.setCustomerName("karan");

        //convert java object to json object
        ObjectMapper objmapper = new ObjectMapper();

        String jsondata = objmapper.writerWithDefaultPrettyPrinter().writeValueAsString(od);

        System.out.println(jsondata);
    }


    //DE-SERIALIZATION PROCESS
    @Test
    public void convertJsonToPojo() throws JsonProcessingException {
        String jsonData = "{\n" +
                "  \"bookId\" : \"1\",\n" +
                "  \"customerName\" : \"karan\"\n" +
                "}";


//        convert json data to pojo object
        ObjectMapper obj = new ObjectMapper();
        OrderData od = obj.readValue(jsonData,OrderData.class);

        System.out.println(od.getBookId());
        System.out.println(od.getCustomerName());
    }

}
