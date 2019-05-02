package api;

import apicontroller.HTTPCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.Account;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestTransfer extends ApiTestSuite {
    @Test
    public void transfer_ShouldSuccess() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amount\" : \"2\",\n" +
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.SUCCESS.getCode());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }

    @Test
    public void transfer_BalancesAreSet() throws UnirestException, IOException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amount\" : \"2\",\n" +
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.SUCCESS.getCode());

        HttpResponse<JsonNode> getSourceAccountResult = Unirest.get(TestConstants.BASE_URL+ TestConstants.ACCOUNT_PATH + "/" + TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE)
                .header("accept", "application/json")
                .asJson();
        HttpResponse<JsonNode> getDestinationAccountResult = Unirest.get(TestConstants.BASE_URL+ TestConstants.ACCOUNT_PATH + "/" + TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION)
                .header("accept", "application/json")
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.SUCCESS.getCode());
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.SUCCESS.getCode());

        Account source = getAccountFromJson(getSourceAccountResult.getBody().toString());
        Account destination = getAccountFromJson(getDestinationAccountResult.getBody().toString());

        Assert.assertEquals(198.0,source.getBalance());
        Assert.assertEquals(202.0, destination.getBalance());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }

    @Test
    public void transfer_InvalidAmount() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amount\" : \"2.666\",\n" +
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }

    @Test
    public void transfer_NotEnoughFunds() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amount\" : \"2000\",\n" +
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.FORBIDDEN.getCode());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }

    @Test
    public void transfer_NegativeAmount() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amount\" : \"-1\",\n" +
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }

    @Test
    public void transfer_ZeroAmount() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amount\" : \"0\",\n" +
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }

    @Test
    public void transfer_AccountDoesNotExist() throws UnirestException {

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amount\" : \"2\",\n" +
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());
    }


    @Test
    public void transfer_Typo() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amunt\" : \"2\",\n" + // amunt
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }

    @Test
    public void transfer_WrongCurrency() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION+"\",\n" +
                "\t\"amount\" : \"2\",\n" +
                "\t\"currency\" : 888\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }

    @Test
    public void transfer_SameAccountID() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());

        String transferCreateJson= "{\n" +
                "\t\"sourceAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"destinationAccountID\" : \""+TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE+"\",\n" +
                "\t\"amount\" : \"2\",\n" +
                "\t\"currency\" : 1001\n" +
                "}";

        HttpResponse<JsonNode> jsonResponse = Unirest.post( TestConstants.BASE_URL+ TestConstants.TRANSFER_PATH)
                .header("accept", "application/json")
                .body(transferCreateJson)
                .asJson();
        Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());

        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }


    @Test
    public void transfer_MissingElement() throws UnirestException {
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.CREATED.getCode());
        Assert.assertEquals(createAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.CREATED.getCode());

        String []jsonCases = {
                 "{\n" +
                        "\t\"sourceAccountID\" : \"" + TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE + "\",\n" +
                        "\t\"destinationAccountID\" : \"" + TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION + "\",\n" +
                        "\t\"currency\" : 1001\n" +
                        "}",
                "{\n" +
                        "\t\"destinationAccountID\" : \"" + TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION + "\",\n" +
                        "\t\"amount\" : \"2\",\n" +
                        "\t\"currency\" : 1001\n" +
                        "}",
                "{\n" +
                        "\t\"sourceAccountID\" : \"" + TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE + "\",\n" +
                        "\t\"amount\" : \"2\",\n" +

                        "\t\"currency\" : 1001\n" +
                        "}",
                "{\n" +
                        "\t\"sourceAccountID\" : \"" + TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE + "\",\n" +
                        "\t\"destinationAccountID\" : \"" + TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION + "\",\n" +
                        "\t\"amount\" : \"2\",\n" +
                        "}"
        };

        for(int i=0; i<jsonCases.length; i++) {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(TestConstants.BASE_URL + TestConstants.TRANSFER_PATH)
                    .header("accept", "application/json")
                    .body(jsonCases[i])
                    .asJson();
            Assert.assertEquals(jsonResponse.getStatus(), HTTPCodes.BAD_REQUEST.getCode());
        }



        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_SOURCE).getStatus(), HTTPCodes.NO_CONTENT.getCode());
        Assert.assertEquals(deleteAccount(TestConstants.ACCOUNT_ID_FOR_TRANFER_DESTINATION).getStatus(), HTTPCodes.NO_CONTENT.getCode());
    }



    private  HttpResponse<JsonNode> deleteAccount(String accountID) throws UnirestException{
        return Unirest.delete( TestConstants.BASE_URL+ TestConstants.ACCOUNT_PATH + "/" + accountID)
                .header("accept", "application/json")
                .asJson();
    }

    private  HttpResponse<JsonNode> createAccount(String accountID) throws UnirestException{
        String sourceAccountJSON = createJSONForAccountCreation(accountID);
        return Unirest.post( TestConstants.BASE_URL+ TestConstants.ACCOUNT_PATH)
                .header("accept", "application/json")
                .body(sourceAccountJSON)
                .asJson();
    }
    private String createJSONForAccountCreation(String accountID){
        return "{\n" +
                "\"accountID\" : \""+accountID+"\",\n" +
                "\"currency\": 1001,\n" +
                "\"balance\" : 200\n" +
                "}";

    }

    private Account getAccountFromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Account.class);
    }

}
