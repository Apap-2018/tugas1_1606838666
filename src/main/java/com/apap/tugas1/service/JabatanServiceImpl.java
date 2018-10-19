package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
    @Autowired
    private JabatanDB jabatanDB;

    @Override
    public void addJabatan(JabatanModel jabatan) {
        jabatanDB.save(jabatan);
    }

    @Override
    public List<JabatanModel> getAllJabatan() {
        return jabatanDB.findAll();
    }

    @Override
    public Optional<JabatanModel> getJabatanById(BigInteger id) {
        return jabatanDB.findById(id);
    }

    @Override
    public void editJabatan(JabatanModel jabatan) {
        JabatanModel updatedJabatan = jabatanDB.findById(jabatan.getId()).get();
        updatedJabatan.setNama(jabatan.getNama());
        updatedJabatan.setDeskripsi(jabatan.getDeskripsi());
        updatedJabatan.setGajiPokok(jabatan.getGajiPokok());
        jabatanDB.save(updatedJabatan);
    }

    @Override
    public void deleteJabatan(BigInteger id) {
        jabatanDB.deleteById(id);
    }

    @Override
    public JabatanModel findByNama(String nama) {
        return jabatanDB.findByNama(nama);
    }
}
