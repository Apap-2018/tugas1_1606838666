package com.apap.tugas1.service;

import com.apap.tugas1.model.ProvinsiModel;

import java.util.List;
import java.util.Optional;

public interface ProvinsiService {
    List<ProvinsiModel> getAllProvinsi();
    Optional<ProvinsiModel> getProvinsiByNama(String nama);
}
