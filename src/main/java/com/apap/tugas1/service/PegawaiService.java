package com.apap.tugas1.service;


import com.apap.tugas1.model.PegawaiModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PegawaiService {
    Optional<PegawaiModel> findPegawaiByNIP(String nip);
    void addPegawai(PegawaiModel pegawai);
    List<PegawaiModel> findAllByTanggalLahirAndTahunMasuk(Date tanggalLahir, String tahunMasuk);
}
