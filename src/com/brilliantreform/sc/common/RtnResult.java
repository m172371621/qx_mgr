package com.brilliantreform.sc.common;

public class RtnResult {

    private Boolean result;

    private String msg;

    private Object data;

    public RtnResult() {

    }

    public RtnResult(boolean result) {
        this.result = result;
    }

    public RtnResult(boolean result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public RtnResult(boolean result, String msg, Object data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
