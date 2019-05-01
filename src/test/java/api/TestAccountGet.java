package api;

import apicontroller.HTTPCodes;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAccountGet extends ApiTestSuite{
    @Test
    public void testAccountGetShouldSuccess() throws UnirestException {
        String testJson = "{\n" +
                "\"accountID\" : \""+TestConstants.ACCOUNT_ID_FOR_GET+"\",\n" +
                "\"currency\": 1001,\n" +
                "\"balance\" : 200\n" +
                "}";
        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.ACCOUNT_PATH)
                .header("accept", "application/json")
                .body(testJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.CREATED.getCode());

        HttpResponse<JsonNode> jsonResponseDelete =
                Unirest.get( TestConstants.BASE_URL+ TestConstants.ACCOUNT_PATH + "/" + TestConstants.ACCOUNT_ID_FOR_GET)
                        .header("accept", "application/json")
                        .asJson();
        Assert.assertEquals(jsonResponseDelete.getStatus(), HTTPCodes.SUCCESS.getCode());
    }

    @Test
    public void testAccountGet_NotFound() throws UnirestException {
        HttpResponse<JsonNode> jsonResponseDelete =
                Unirest.get( TestConstants.BASE_URL+ TestConstants.ACCOUNT_PATH + "/" + TestConstants.ACCOUNT_ID_FOR_GET_NOT_FOUNDED)
                        .header("accept", "application/json")
                        .asJson();
        Assert.assertEquals(jsonResponseDelete.getStatus(), HTTPCodes.NOT_FOUND.getCode());
    }
}
