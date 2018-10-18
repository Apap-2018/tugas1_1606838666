package com.apap.tugas1.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "provinsi")
public class ProvinsiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "presentase_tunjangan", nullable = false)
    private double presentase_tunjangan;

    @OneToMany(mappedBy = "provinsi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InstansiModel> listInstansi;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getPresentase_tunjangan() {
        return presentase_tunjangan;
    }

    public void setPresentase_tunjangan(double presentase_tunjangan) {
        this.presentase_tunjangan = presentase_tunjangan;
    }

    public List<InstansiModel> getListInstansi() {
        return listInstansi;
    }

    public void setListInstansi(List<InstansiModel> listInstansi) {
        this.listInstansi = listInstansi;
    }
}