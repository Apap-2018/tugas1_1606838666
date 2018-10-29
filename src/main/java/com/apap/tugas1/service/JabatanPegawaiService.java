package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;

import java.util.ArrayList;

public interface JabatanPegawaiService {
    ArrayList<JabatanPegawaiModel> findAllByJabatan(JabatanModel jabatan);
    void addJabatanPegawai(JabatanPegawaiModel jabatanPegawaiModel);
    void deleteByPegawai(PegawaiModel pegawai);

}
