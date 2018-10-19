package com.apap.tugas1.repository;

import com.apap.tugas1.model.InstansiModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstansiDB extends JpaRepository<InstansiModel, Long> {
}
