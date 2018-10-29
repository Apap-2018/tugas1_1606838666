package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

import java.math.BigInteger;
import java.util.List;

public interface InstansiService {
    InstansiModel getInstansiById(long id);
    InstansiModel findByNama(String nama);
    List<InstansiModel> findAllByProvinsi(ProvinsiModel provinsi);
    List<InstansiModel> getAll();
}
