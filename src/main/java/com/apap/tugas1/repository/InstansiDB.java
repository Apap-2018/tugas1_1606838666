package com.apap.tugas1.repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface InstansiDB extends JpaRepository<InstansiModel, Long> {
    InstansiModel findById(BigInteger id);
    InstansiModel findByNama(String nama);
    List<InstansiModel> findAllByProvinsi(ProvinsiModel provinsi);
}
