package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
    @Autowired
    InstansiDB instansiDB;

    @Override
    public InstansiModel getInstansiById(long id) {
        return instansiDB.findById(id).get();
    }

    @Override
    public InstansiModel findByNama(String nama) {
        return instansiDB.findByNama(nama);
    }

    @Override
    public List<InstansiModel> findAllByProvinsi(ProvinsiModel provinsi) {
        return instansiDB.findAllByProvinsi(provinsi);
    }

    @Override
    public List<InstansiModel> getAll() {
        return instansiDB.findAll();
    }
}
