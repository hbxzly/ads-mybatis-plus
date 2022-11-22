package com.hbx.ads.domain.sinoclick;

public class OverView {

    private String code;
    private String message;
    private Result result;
    private String traceId;
    private String success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "OverView{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                ", traceId='" + traceId + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
