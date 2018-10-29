package com.apap.tugas1.service;


import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PegawaiService {
    Optional<PegawaiModel> findPegawaiByNIP(String nip);
    void addPegawai(PegawaiModel pegawai);
    void editPegawai(PegawaiModel pegawai, String oldNip);
    List<PegawaiModel> findAllByTanggalLahirAndTahunMasuk(Date tanggalLahir, String tahunMasuk);
    List<PegawaiModel> findAllByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
    List<PegawaiModel> findAllByInstansiOrderByTanggalLahir(InstansiModel instansi);
    List<PegawaiModel> getAll();
    List<PegawaiModel> findAllByInstansi(InstansiModel instansi);
    List<PegawaiModel> findAllByListJabatan(JabatanPegawaiModel jabatanPegawaiModel);

}
