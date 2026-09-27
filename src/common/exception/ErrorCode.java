package common.exception;

public enum ErrorCode {
    INVALID_INPUT("E001", "Invalid Input"),
    NOT_FOUND("E002", "Not Found"),
    UNKNOWN_ERROR("E003", "Unknown Error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
