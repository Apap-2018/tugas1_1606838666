package com.apap.tugas1.model;

import java.util.List;
import java.util.Map;

public class AjaxResponseBody {
    private Map map;
    private List<String> listOfString;
    private List<PegawaiModel> listOfPegawaiModel;
    private List<InstansiModel> listOfInstansiModel;
    private String message;

    public List<InstansiModel> getListOfInstansiModel() {
        return listOfInstansiModel;
    }

    public void setListOfInstansiModel(List<InstansiModel> listOfInstansiModel) {
        this.listOfInstansiModel = listOfInstansiModel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PegawaiModel> getListOfPegawaiModel() {
        return listOfPegawaiModel;
    }

    public void setListOfPegawaiModel(List<PegawaiModel> listOfPegawaiModel) {
        this.listOfPegawaiModel = listOfPegawaiModel;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List getListOfString() {
        return listOfString;
    }

    public void setListOfString(List listOfString) {
        this.listOfString = listOfString;
    }
}
