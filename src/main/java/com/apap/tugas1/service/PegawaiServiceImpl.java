package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
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
        try{
            return Optional.of(pegawaiDB.findByNip(nip));
        } catch (Exception e) {
            return Optional.of(null);
        }
    }

    @Override
    public void addPegawai(PegawaiModel pegawai) {
        pegawaiDB.save(pegawai);
    }

    @Override
    public List<PegawaiModel> findAllByTanggalLahirAndTahunMasuk(Date tanggalLahir, String tahunMasuk) {
        return pegawaiDB.findAllByTanggalLahirAndTahunMasuk(tanggalLahir, tahunMasuk);
    }

    @Override
    public List<PegawaiModel> findAllByInstansiOrderByTanggalLahir(InstansiModel instansi) {
        return pegawaiDB.findAllByInstansiOrderByTanggalLahir(instansi);
    }

    @Override
    public List<PegawaiModel> getAll() {
        return pegawaiDB.findAll();
    }

    @Override
    public List<PegawaiModel> findAllByInstansi(InstansiModel instansi) {
        return pegawaiDB.findAllByInstansi(instansi);
    }

    @Override
    public List<PegawaiModel> findAllByListJabatan(JabatanPegawaiModel jabatanPegawaiModel) {
        return pegawaiDB.findAllByListJabatan(jabatanPegawaiModel);
    }

    @Override
    public void editPegawai(PegawaiModel pegawai, String oldNip) {
        PegawaiModel updatedPegawai = pegawaiDB.findByNip(oldNip);
        updatedPegawai.setNama(pegawai.getNama());
        updatedPegawai.setTempatLahir(pegawai.getTempatLahir());
        updatedPegawai.setTanggalLahir(pegawai.getTanggalLahir());
        updatedPegawai.setTahunMasuk(pegawai.getTahunMasuk());
        updatedPegawai.setInstansi(pegawai.getInstansi());
        updatedPegawai.setListJabatan(pegawai.getListJabatan());
        updatedPegawai.setNip(pegawai.getNip());
        pegawaiDB.save(updatedPegawai);
    }
}
