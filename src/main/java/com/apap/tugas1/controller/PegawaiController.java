package com.apap.tugas1.controller;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
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
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String tambahPegawai(Model model) {

		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		return "addPegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String submitPegawai(
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

		String nip = provinsi.getId().toString() + kodeInstansi + kodeTanggal[2] + kodeTanggal[1] + kodeTanggal[0].substring(2) + data.get("tahunMasuk").toString() + Integer.toString(pegawaiMirip.size() + 1);

		newPegawai.setNip(nip);
		newPegawai.setNama(data.get("namaPegawai").toString());
		newPegawai.setTempatLahir(data.get("tempatLahir").toString());
		newPegawai.setTanggalLahir(myDate);
		newPegawai.setTahunMasuk(data.get("tahunMasuk").toString());
		newPegawai.setInstansi(instansiObject);

		pegawaiService.addPegawai(newPegawai);

		model.addAttribute("pesan", "ditambahkan");
		model.addAttribute("nip", nip);
		return "submitPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	public String updatePegawai(@RequestParam String nip, Model model) {
		
		model.addAttribute("endpoint", "ubah");
		return "formPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	public String submitUpdatePegawai(Model model) {
		
		model.addAttribute("pesan", "diubah");
		model.addAttribute("nip", "1234567890");
		return "submitPegawai";
	}

}
