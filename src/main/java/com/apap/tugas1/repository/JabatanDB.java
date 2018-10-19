package com.apap.tugas1.repository;

import com.apap.tugas1.model.JabatanModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface JabatanDB extends JpaRepository<JabatanModel, Long> {
    Optional<JabatanModel> findById(BigInteger id);
}
