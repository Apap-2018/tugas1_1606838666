package com.apap.tugas1.repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PegawaiDB extends JpaRepository<PegawaiModel, Long> {
    PegawaiModel findByNip(String nip);
    List<PegawaiModel> findAllByTanggalLahirAndTahunMasuk(Date tanggalLahir, String tahunMasuk);
    List<PegawaiModel> findAllByInstansiAndTanggalLahirAndTahunMasukOrderByNipDesc(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
    List<PegawaiModel> findAllByInstansiOrderByTanggalLahir(InstansiModel instansi);
    List<PegawaiModel> findAllByInstansi(InstansiModel instansi);
    List<PegawaiModel> findAllByListJabatan(JabatanPegawaiModel jabatanPegawaiModel);
}

