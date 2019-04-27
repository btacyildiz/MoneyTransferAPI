package apicontroller;

public enum HTTPCodes {
    SUCCESS(200),
    CREATED(201),
    BAD_REQUEST(400);
    private int code;

    HTTPCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
