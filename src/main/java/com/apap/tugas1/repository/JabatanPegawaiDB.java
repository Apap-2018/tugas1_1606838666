package com.apap.tugas1.repository;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface JabatanPegawaiDB extends JpaRepository<JabatanPegawaiModel, Long> {
    ArrayList<JabatanPegawaiModel> findAllByJabatan(JabatanModel jabatan);
}
