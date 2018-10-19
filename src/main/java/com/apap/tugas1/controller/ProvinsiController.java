package com.apap.tugas1.controller;

import com.apap.tugas1.model.AjaxResponseBody;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProvinsiController {
    @Autowired
    ProvinsiService provinsiService;

    @RequestMapping(value = "/provinsi/get/instansi", method = RequestMethod.POST)
    public ResponseEntity<Object> getInstansi(@RequestBody Map<String, String> data) {
        AjaxResponseBody result = new AjaxResponseBody();
        ProvinsiModel provinsi = provinsiService.getProvinsiByNama(data.get("nama")).get();
        List<String> listNamaInstansi = new ArrayList<>();
        List<InstansiModel> listInstansi = provinsi.getListInstansi();

        for (InstansiModel instansi: listInstansi) {
            listNamaInstansi.add(instansi.getNama());
        }

        result.setMessage(data);
        result.setResult(listNamaInstansi);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
