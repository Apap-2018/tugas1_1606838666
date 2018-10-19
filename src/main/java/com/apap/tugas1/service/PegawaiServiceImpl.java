package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
    @Autowired
    private PegawaiDB pegawaiDB;

    @Override
    public Optional<PegawaiModel> findPegawaiByNIP(String nip) {
        return Optional.of(pegawaiDB.findByNip(nip));
    }

    @Override
    public void addPegawai(PegawaiModel pegawai) {
        pegawaiDB.save(pegawai);
    }

    @Override
    public List<PegawaiModel> findAllByTanggalLahirAndTahunMasuk(Date tanggalLahir, String tahunMasuk) {
        return pegawaiDB.findAllByTanggalLahirAndTahunMasuk(tanggalLahir, tahunMasuk);
    }
}
