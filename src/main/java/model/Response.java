package model;

import apicontroller.ApiResult;

public class Response {
    private int status;
    private String description;

    public Response(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public Response(ApiResult apiResult){
        this.status = apiResult.getStatus();
        this.description = apiResult.getDescription();
    }


}
