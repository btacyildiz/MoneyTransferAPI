package api;


import apicontroller.HTTPCodes;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestAccountCreate extends ApiTestSuite{

    @Test
    public void testAccountIsCreated_ShouldSuccess() throws UnirestException{
        String testJson = "{\n" +
                "\"accountID\" : 5,\n" +
                "\"currency\": 1001,\n" +
                "\"balance\" : 200\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ "/account")
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.CREATED.getCode());
    }

    @Test
    public void testAccountIsCreated_InvalidBalance() throws UnirestException{
        String testJson = "{\n" +
                "\"accountID\" : 5,\n" +
                "\"currency\": 1001,\n" +
                "\"balance\" : 200.777\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ "/account")
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());
    }


    @Test
    public void testAccountIsCreated_NegativeBalance() throws UnirestException{
        String testJson = "{\n" +
                "\"accountID\" : 5,\n" +
                "\"currency\": 1001,\n" +
                "\"balance\" : -5\n" + // negative balance
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ "/account")
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());
    }

    @Test
    public void testAccountIsCreated_AlreadyCreated() throws UnirestException{
        String testJson = "{\n" +
                "\"accountID\" : 6,\n" +
                "\"currency\": 1001,\n" +
                "\"balance\" : 200\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ "/account")
                .header("accept", "application/json")
                .body(testJson)
                .asJson();

        HttpResponse<JsonNode> jsonResponseSecond = Unirest.post( TestConstants.BASE_URL+ "/account")
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(jsonResponseSecond.getStatus(), HTTPCodes.BAD_REQUEST.getCode());
    }

    @Test
    public void testAccountIsCreated_MissingElement() throws UnirestException{
        
        String []jsonCases = {
                "{\n" +
                        "\"currency\": 1001,\n" +
                        "\"balance\" : 200\n" +
                        "}",
                "{\n" +
                        "\"accountID\" : 5,\n" +
                        "\"balance\" : 200\n" +
                        "}",
                "{\n" +
                        "\"accountID\" : 5,\n" +
                        "\"currency\": 1001,\n" +
                        "}"
        };

        for(int i=0; i<jsonCases.length ; i++) {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(TestConstants.BASE_URL + "/account")
                    .header("accept", "application/json")
                    .body(jsonCases[i])
                    .asJson();
            Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());
        }
    }
    @Test
    public void testAccountIsCreated_BadType() throws UnirestException{
        String testJson = "{\n" +
                "\"accountID\" : 5,\n" +
                "\"currency\": 1001,\n" +
                "\"balance\" : \"asd\"\n" + // string is assigned
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ "/account")
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());
    }
    @Test
    public void testAccountIsCreated_BadCurrencyType() throws UnirestException{
        String testJson = "{\n" +
                "\"accountID\" : 5,\n" +
                "\"currency\": 99,\n" + // Bad currency type
                "\"balance\" : 100\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ "/account")
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());
    }
}
