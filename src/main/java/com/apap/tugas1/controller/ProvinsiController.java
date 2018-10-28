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

        List<InstansiModel> listInstansi = new ArrayList<>();
        for (InstansiModel instansi: provinsi.getListInstansi()) {
            InstansiModel addInstansi = new InstansiModel(instansi.getNama(), instansi.getDeskripsi(), provinsi);
            addInstansi.setId(instansi.getId());
            listInstansi.add(addInstansi);
        }

        result.setMap(data);
        result.setListOfInstansiModel(listInstansi);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
