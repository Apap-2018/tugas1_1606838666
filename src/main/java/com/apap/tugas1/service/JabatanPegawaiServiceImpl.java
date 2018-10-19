package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService {
    @Autowired
    private JabatanPegawaiDB jabatanPegawaiDB;

    @Override
    public ArrayList<JabatanPegawaiModel> findAllByJabatan(JabatanModel jabatan) {
        return jabatanPegawaiDB.findAllByJabatan(jabatan);
    }
}
