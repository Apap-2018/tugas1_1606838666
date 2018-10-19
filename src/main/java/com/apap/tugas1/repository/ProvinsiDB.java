package com.apap.tugas1.repository;

import com.apap.tugas1.model.ProvinsiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvinsiDB extends JpaRepository<ProvinsiModel, Long> {
    List<ProvinsiModel> findAll();
    Optional<ProvinsiModel> findByNama(String nama);


}
