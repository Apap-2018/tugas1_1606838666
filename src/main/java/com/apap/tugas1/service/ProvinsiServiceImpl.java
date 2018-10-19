package com.apap.tugas1.service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
    @Autowired
    ProvinsiDB provinsiDB;

    @Override
    public List<ProvinsiModel> getAllProvinsi() {
        return provinsiDB.findAll();
    }

    @Override
    public Optional<ProvinsiModel> getProvinsiByNama(String nama) {
        return provinsiDB.findByNama(nama);
    }
}
