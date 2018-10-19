package com.apap.tugas1.model;

import java.util.List;
import java.util.Map;

public class AjaxResponseBody {
    Map message;
    List<String> result;

    public Map getMessage() {
        return message;
    }

    public void setMessage(Map message) {
        this.message = message;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }
}
