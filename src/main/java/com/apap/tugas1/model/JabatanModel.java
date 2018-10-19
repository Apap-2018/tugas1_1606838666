package com.apap.tugas1.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "jabatan")
public class JabatanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @NotNull
    @Size(max = 255)
    @Column(name= "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name= "deskripsi", nullable = false)
    private String deskripsi;

    @NotNull
    @Column(name = "gaji_pokok", nullable = false)
    private double gajiPokok;

    @OneToMany(mappedBy = "jabatan", fetch = FetchType.LAZY,  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<JabatanPegawaiModel> listJabatan;

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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(double gaji_pokok) {
        this.gajiPokok = gaji_pokok;
    }

    public List<JabatanPegawaiModel> getListJabatan() {
        return listJabatan;
    }

    public void setListJabatan(List<JabatanPegawaiModel> listJabatan) {
        this.listJabatan = listJabatan;
    }
}
