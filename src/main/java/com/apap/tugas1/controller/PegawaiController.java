package com.apap.tugas1.controller;

import com.apap.tugas1.model.*;
import com.apap.tugas1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


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

		model.addAttribute("isCariPegawai", true);
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
		InstansiModel instansi = instansiService.getInstansiById(Long.valueOf(id_instansi));
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
//		PegawaiModel pegawai = new PegawaiModel();
//		List<JabatanPegawaiModel> dummyList = new ArrayList<>();
//		JabatanPegawaiModel dummyJabatanPegawai = new JabatanPegawaiModel();
//		dummyJabatanPegawai.setPegawai(pegawai);
//		dummyList.add(dummyJabatanPegawai);
//		pegawai.setListJabatan(dummyList);
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setListJabatan(new ArrayList<JabatanPegawaiModel>());
		pegawai.getListJabatan().add(new JabatanPegawaiModel());

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listAllJabatan", jabatanService.getAllJabatan());
		model.addAttribute("isTambahPegawai", true);
		return "formPegawai";
	}

	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST, params= {"addJabatanRow"})
	public String addRow(@ModelAttribute PegawaiModel pegawai,
						 BindingResult bindingResult,
						 Model model) {
		if (pegawai.getListJabatan() == null) {
			pegawai.setListJabatan(new ArrayList<JabatanPegawaiModel>());
		}
		pegawai.getListJabatan().add(new JabatanPegawaiModel());

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("pageTitle", "Tambah Pegawai");
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listAllJabatan", jabatanService.getAllJabatan());
		model.addAttribute("isTambahPegawai", true);

		return "formPegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String submitPegawai(
			Model model,
			@ModelAttribute PegawaiModel pegawai,
			@RequestParam String id_instansi
			) {
		InstansiModel instansi = instansiService.getInstansiById(Long.valueOf(id_instansi));
		pegawai.setInstansi(instansi);

		String tanggalLahir = pegawai.getTanggalLahir().toString();
		String[] kodeTanggal = tanggalLahir.split("-");

		List<PegawaiModel> pegawaiMirip = pegawaiService.findAllByTanggalLahirAndTahunMasuk(pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
		String urutan = "01";
		try {
			urutan = (pegawaiMirip.size() < 9) ? "0" + (Integer.toString(pegawaiMirip.size() + 1)) : Integer.toString(pegawaiMirip.size() + 1);
		} catch (Exception e) {

		}
		String nip = id_instansi + kodeTanggal[2] + kodeTanggal[1] + kodeTanggal[0].substring(2) + pegawai.getTahunMasuk() + urutan;

		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);

		PegawaiModel pegawaiAdded = pegawaiService.findPegawaiByNIP(nip).get();
		System.out.println(pegawaiAdded.getListJabatan());

		for (JabatanPegawaiModel jabatan: pegawaiAdded.getListJabatan()) {
			jabatan.setPegawai(pegawaiAdded);
			System.out.println(jabatan.getJabatan().getNama());
			System.out.println(jabatan.getPegawai().getNama());
			jabatanPegawaiService.addJabatanPegawai(jabatan);
			System.out.println(jabatan.getId());
		}


		model.addAttribute("pesan", "ditambahkan");
		model.addAttribute("nip", nip);

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
	public ResponseEntity<Object> submitUpdatePegawai(Model model, @RequestBody Map<String, Object> data) {
		PegawaiModel pegawai = new PegawaiModel();
		ProvinsiModel provinsi = provinsiService.getProvinsiByNama(data.get("provinsi").toString()).get();
		InstansiModel instansiObject = null;

		String kodeInstansi = "";
		for (InstansiModel instansi: provinsi.getListInstansi()) {
			if (instansi.getNama().equals(data.get("namaInstansi").toString())) {
				kodeInstansi = Long.toString(instansi.getId()).substring(2);
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
		String nip = Long.toString(provinsi.getId()) + kodeInstansi + kodeTanggal[2] + kodeTanggal[1] + kodeTanggal[0].substring(2) + data.get("tahunMasuk").toString() + urutan;

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
			jabatanPegawaiModelDitambah.setPegawai(pegawai);
			listJabatanPegawai.add(jabatanPegawaiModelDitambah);
		}

		pegawai.setNip(nip);
		pegawai.setNama(data.get("namaPegawai").toString());
		pegawai.setTempatLahir(data.get("tempatLahir").toString());
//		pegawai.setTanggalLahir(myDate);
		pegawai.setTahunMasuk(data.get("tahunMasuk").toString());
		pegawai.setInstansi(instansiObject);

		pegawaiService.editPegawai(pegawai);

		AjaxResponseBody result = new AjaxResponseBody();
		result.setMessage(nip);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/pegawai/berhasil/ubah", method = RequestMethod.GET)
	public String pegawaiDiubah(@RequestParam String nip, Model model) {
		model.addAttribute("nip", nip);
		model.addAttribute("pesan", "diubah");
		model.addAttribute("pageTitle", "Detail Pegawai");
		return "submitPegawai";
	}

}
