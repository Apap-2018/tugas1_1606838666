package com.apap.tugas1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pegawai")
public class PegawaiModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @NotNull
    @Size(max = 255)
    @Column(name = "NIP", nullable = false, unique = true)
    private  String nip;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "tempat_lahir", nullable = false)
    private String tempatLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "tanggal_lahir", nullable = false)
    private Date tanggalLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "tahun_masuk", nullable = false)
    private String tahunMasuk;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_instansi", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private InstansiModel instansi;

    @OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY,  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<JabatanPegawaiModel> listJabatan;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(String tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public InstansiModel getInstansi() {
        return instansi;
    }

    public void setInstansi(InstansiModel instansi) {
        this.instansi = instansi;
    }

    public List<JabatanPegawaiModel> getListJabatan() {
        return listJabatan;
    }

    public void setListJabatan(List<JabatanPegawaiModel> listJabatan) {
        this.listJabatan = listJabatan;
    }

    public int getGaji() {
        double persentaseTunjangan = this.getInstansi().getProvinsi().getPresentaseTunjangan();
        double gajiPokokTertinggi = 0;
        for (JabatanPegawaiModel jabatanGateway: this.getListJabatan()) {
            double gajiEvaluasi = jabatanGateway.getJabatan().getGajiPokok();
            if ( gajiEvaluasi > gajiPokokTertinggi) {
                gajiPokokTertinggi = gajiEvaluasi;
            }
        }
        Double gaji = gajiPokokTertinggi + ((persentaseTunjangan / 100) * gajiPokokTertinggi);

        return gaji.intValue();
    }
}
