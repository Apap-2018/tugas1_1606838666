package com.apap.tugas1.controller;

import com.apap.tugas1.model.AjaxResponseBody;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@Controller
public class JabatanController {
    @Autowired
    private JabatanService jabatanService;

    @Autowired
    private JabatanPegawaiService jabatanPegawaiService;

    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
    public String tambahJabatan(Model model) {
        model.addAttribute("title", "tambah");
        model.addAttribute("jabatan", new JabatanModel());
        model.addAttribute("isTambahJabatan", true);
        return "formJabatan";
    }

    @RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
    public String submitJabatan(Model model, @ModelAttribute JabatanModel jabatan) {
        jabatanService.addJabatan(jabatan);

        model.addAttribute("jabatan", jabatan);
        model.addAttribute("action", "tambah");
        return "jabatanModified";
    }

    @RequestMapping(value = {"/jabatan/view", "/jabatan/view/{id_jabatan}"}, method = RequestMethod.GET)
    public String viewJabatan(
            Model model,
            @PathVariable Optional<String> id_jabatan,
            @RequestParam (value = "idJabatan", required = false) String idJabatan
            ) {
        JabatanModel jabatan = jabatanService.getJabatanById((id_jabatan.isPresent()) ? Long.valueOf(id_jabatan.get()) : Long.valueOf(idJabatan)).get();

        model.addAttribute("pageTitle", "Detail Jabatan");
        model.addAttribute("jabatan", jabatan);
        return "viewJabatan";
    }
    @RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
    public String viewAllJabatan(Model model) {
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();

        model.addAttribute("isLihatSemuaJabatan", true);
        model.addAttribute("pageTitle", "Detail Jabatan");
        model.addAttribute("listJabatan", listJabatan);
        return "allJabatan";
    }

    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
    public String ubahJabatan(Model model, @RequestParam String idJabatan) {
        JabatanModel jabatan = jabatanService.getJabatanById(Long.valueOf(idJabatan)).get();

        model.addAttribute("title", "ubah");
        model.addAttribute("jabatan", jabatan);
        return "formJabatan";
    }

    @RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
    public String submitPerubahanJabatan(Model model, @ModelAttribute JabatanModel jabatan) {
        jabatanService.editJabatan(jabatan);

        model.addAttribute("jabatan", jabatan);
        model.addAttribute("action", "ubah");
        return "jabatanModified";
    }

    @RequestMapping(value = "/jabatan/cek/hapus/{id_jabatan}", method = RequestMethod.GET)
    public ResponseEntity<Object> cekHapusJabatan(@PathVariable String id_jabatan) {
        ArrayList<JabatanPegawaiModel> listJabatanPegawai = jabatanPegawaiService.findAllByJabatan(jabatanService.getJabatanById(Long.valueOf(id_jabatan)).get());
        AjaxResponseBody result = new AjaxResponseBody();

        Boolean bisaDihapus = listJabatanPegawai.size() <= 0;
        result.setMessage(bisaDihapus.toString());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
    public String hapusJabatan(@RequestParam String id, @RequestParam String nama, Model model) {
        jabatanService.deleteJabatan(Long.valueOf(id));

        model.addAttribute("nama", nama);
        return "jabatanDeleted";
    }

    @RequestMapping(value = "/jabatan/data/pegawai", method = RequestMethod.GET)
    public String dataPegawai(Model model) {
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
        Map<String, Integer> dataPegawaiInstansi = new HashMap<>();

        for (JabatanModel jabatan: listJabatan) {
            int jumlahPegawai = jabatanPegawaiService.findAllByJabatan(jabatan).size();
            dataPegawaiInstansi.put(jabatan.getNama(), jumlahPegawai);
        }
        model.addAttribute("dataPegawaiInstansi", dataPegawaiInstansi);
        return "allPegawaiInstansi";
    }

    @RequestMapping(value = "/jabatan/get", method = RequestMethod.GET)
    public ResponseEntity<Object> getJabatan() {
        AjaxResponseBody result = new AjaxResponseBody();
        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
        List<String> listNamaJabatan = new ArrayList<>();

        for (JabatanModel jabatan: listJabatan) {
            listNamaJabatan.add(jabatan.getNama());
        }

        result.setListOfString(listNamaJabatan);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
