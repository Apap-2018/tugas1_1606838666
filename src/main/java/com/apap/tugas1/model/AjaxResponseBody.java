package com.apap.tugas1.model;

import java.util.List;
import java.util.Map;

public class AjaxResponseBody {
    private Map map;
    private List<String> result;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }
}
