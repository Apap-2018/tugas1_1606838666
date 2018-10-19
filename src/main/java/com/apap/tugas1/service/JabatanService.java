package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface JabatanService {
    void addJabatan(JabatanModel jabatan);
    List<JabatanModel> getAllJabatan();
    Optional<JabatanModel> getJabatanById(BigInteger id);
    void editJabatan(JabatanModel jabatan);
    void deleteJabatan(BigInteger id);
}
