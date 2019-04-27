package apicontroller;

public enum HTTPCodes {
    SUCCESS(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    FORBIDDEN(403),
    NOT_FOUND(404);

    private int code;

    HTTPCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
