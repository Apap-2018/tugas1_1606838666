package com.apap.tugas1.controller;

import com.apap.tugas1.model.*;
import com.apap.tugas1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author faisalridwan
 * PegawaiController
 */
@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;

	@Autowired
	private ProvinsiService provinsiService;

	@Autowired
	private JabatanService jabatanService;

	@Autowired
	private InstansiService instansiService;

	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();

		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("isHome", true);
		model.addAttribute("pageTitle", "Home Page");
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	public String lihatPegawai(@RequestParam String nip, Model model) {
		Optional<PegawaiModel> pegawaiDicari = pegawaiService.findPegawaiByNIP(nip);
		PegawaiModel pegawai = pegawaiDicari.get();

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("pageTitle", "Detail Pegawai");
		return "pegawaiDetail";
	}

	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String lihatPegawai(Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();

		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("pageTitle", "Detail Pegawai");
		return "cariPegawai";
	}

	@RequestMapping(value = "/pegawai/cari/data", method = RequestMethod.POST)
	public ResponseEntity<Object> getInstansi(@RequestBody Map<String, Object> data) {
		AjaxResponseBody result = new AjaxResponseBody();
		String provinsi = data.get("provinsi").toString();
		String instansi = data.get("instansi").toString();
		String jabatan = data.get("jabatan").toString();
		List<PegawaiModel> listPegawai = pegawaiService.getAll();


		List<PegawaiModel> listA = new ArrayList<>();
		listA = pegawaiService.findAllByInstansi(instansiService.findByNama(instansi));
		listPegawai.retainAll(listA);

		List<PegawaiModel> listB = new ArrayList<>();
		ProvinsiModel provinsiModel = provinsiService.getProvinsiByNama(provinsi).get();
		List<InstansiModel> listInstansi = instansiService.findAllByProvinsi(provinsiModel);
		for (InstansiModel instansiModel: listInstansi) {
			listB = pegawaiService.findAllByInstansi(instansiModel);
			listPegawai.retainAll(listB);
		}

		List<PegawaiModel> listC = new ArrayList<>();
		JabatanModel jabatanModel= jabatanService.findByNama(jabatan);
		List<JabatanPegawaiModel> listJabatanPegawaiModel = jabatanPegawaiService.findAllByJabatan(jabatanModel);
		for (JabatanPegawaiModel jabatanPegawaiModel: listJabatanPegawaiModel) {
			listC.add(jabatanPegawaiModel.getPegawai());
		}
		listPegawai.retainAll(listC);

		result.setListOfPegawaiModel(listPegawai);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	public String lihatPegawaiTermudaTertua(@RequestParam String id_instansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(new BigInteger(id_instansi));
		List<PegawaiModel> pegawaiInstansi = pegawaiService.findAllByInstansiOrderByTanggalLahir(instansi);
		PegawaiModel pegawaiTertua = pegawaiInstansi.get(0);
		PegawaiModel pegawaiTermuda = pegawaiInstansi.get(pegawaiInstansi.size()-1);

		model.addAttribute("pageTitle", "Detail Pegawai");
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		return "pegawaiTermudaTertua";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String tambahPegawai(Model model) {

		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		return "addPegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public ResponseEntity<Object> submitPegawai(
			Model model,
			@RequestBody Map<String, Object> data
			) {
		PegawaiModel newPegawai = new PegawaiModel();
		ProvinsiModel provinsi = provinsiService.getProvinsiByNama(data.get("provinsi").toString()).get();
		InstansiModel instansiObject = null;

		String kodeInstansi = "";
		for (InstansiModel instansi: provinsi.getListInstansi()) {
			if (instansi.getNama().equals(data.get("namaInstansi").toString())) {
				kodeInstansi = instansi.getId().toString().substring(2);
				instansiObject = instansi;
			}
		}
		String tanggalLahir = data.get("tanggalLahir").toString();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		try {
			myDate = formatter.parse(tanggalLahir);
			//The code you are trying to exception handle
		}
		catch (Exception e) {
			//The handling for the code
		}
		String[] kodeTanggal = data.get("tanggalLahir").toString().split("-");
		List<PegawaiModel> pegawaiMirip = pegawaiService.findAllByTanggalLahirAndTahunMasuk(myDate, data.get("tahunMasuk").toString());
		String urutan = (pegawaiMirip.size() < 10) ? "0" + Integer.toString(pegawaiMirip.size() + 1) : Integer.toString(pegawaiMirip.size() + 1);
		String nip = provinsi.getId().toString() + kodeInstansi + kodeTanggal[2] + kodeTanggal[1] + kodeTanggal[0].substring(2) + data.get("tahunMasuk").toString() + urutan;

		String a = data.get("listJabatan").toString();
		a = a.replace("[", "");
		a = a.replace("]", "");
		a = a.trim();
		String[] listNamaJabatan = a.split(",");
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<>();
		for (String namaJabatan: listNamaJabatan) {
			JabatanModel jabatanDitambah = jabatanService.findByNama(namaJabatan);
			JabatanPegawaiModel jabatanPegawaiModelDitambah = new JabatanPegawaiModel();
			jabatanPegawaiModelDitambah.setJabatan(jabatanDitambah);
			jabatanPegawaiModelDitambah.setPegawai(newPegawai);
			listJabatanPegawai.add(jabatanPegawaiModelDitambah);
		}

		newPegawai.setNip(nip);
		newPegawai.setNama(data.get("namaPegawai").toString());
		newPegawai.setTempatLahir(data.get("tempatLahir").toString());
		newPegawai.setTanggalLahir(myDate);
		newPegawai.setTahunMasuk(data.get("tahunMasuk").toString());
		newPegawai.setInstansi(instansiObject);
//		newPegawai.setListJabatan(listJabatanPegawai);

		pegawaiService.addPegawai(newPegawai);

		model.addAttribute("pesan", "ditambahkan");
		model.addAttribute("nip", nip);

		AjaxResponseBody result = new AjaxResponseBody();
		result.setMessage(nip);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/pegawai/berhasil/tambah", method = RequestMethod.GET)
	public String pegawaiDitambah(@RequestParam String nip, Model model) {
		model.addAttribute("nip", nip);
		model.addAttribute("pesan", "ditambah");
		model.addAttribute("pageTitle", "Detail Pegawai");
		return "submitPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	public String updatePegawai(@RequestParam String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.findPegawaiByNIP(nip).get();

		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("pegawai", pegawai);
		return "ubahPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	public String submitUpdatePegawai(Model model) {
		
		model.addAttribute("pesan", "diubah");
		model.addAttribute("nip", "1234567890");
		return "submitPegawai";
	}

}
