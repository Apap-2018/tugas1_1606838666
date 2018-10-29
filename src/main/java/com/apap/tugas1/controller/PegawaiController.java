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


import javax.servlet.http.HttpServletRequest;
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
		try {
			Optional<PegawaiModel> pegawaiDicari = pegawaiService.findPegawaiByNIP(nip);
			PegawaiModel pegawai = pegawaiDicari.get();
			model.addAttribute("pegawai", pegawai);
			model.addAttribute("pageTitle", "Detail Pegawai");
			model.addAttribute("isHome", true);
			return "pegawaiDetail";
		} catch (Exception e) {
			model.addAttribute("errorMessage", "NIP yang anda masukkan salah");
			model.addAttribute("pageTitle", "Error Page");
			return "errorPage";
		}
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
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setListJabatan(new ArrayList<JabatanPegawaiModel>());
		pegawai.getListJabatan().add(new JabatanPegawaiModel());

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listAllJabatan", jabatanService.getAllJabatan());
		model.addAttribute("method", "tambah");
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
		model.addAttribute("method", "tambah");
		model.addAttribute("isTambahPegawai", true);

		return "formPegawai";
	}

	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST, params= {"removeRow"})
	public String removeRow(@ModelAttribute PegawaiModel pegawai,
							BindingResult bindingResult,
							final HttpServletRequest req,
							Model model) {
		pegawai.getListJabatan().remove(Integer.valueOf(req.getParameter("removeRow")).intValue());

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("pageTitle", "Tambah Pegawai");
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listAllJabatan", jabatanService.getAllJabatan());
		model.addAttribute("method", "tambah");
		model.addAttribute("isTambahPegawai", true);
		return "formPegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params = {"addPegawai"})
	public String submitPegawai(
			Model model,
			@ModelAttribute PegawaiModel pegawai,
			@RequestParam String id_instansi
			) {
		try {
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
			List<JabatanPegawaiModel> jabatanModelModified = new ArrayList<>();

			for (JabatanPegawaiModel jabatan: pegawaiAdded.getListJabatan()) {
				if (jabatan == null) {
					System.out.println("NULL!");
				} else {
					jabatan.setPegawai(pegawaiAdded);
					jabatanModelModified.add(jabatan);
				}
			}
			for (JabatanPegawaiModel jabatan: jabatanModelModified) {
				jabatanPegawaiService.addJabatanPegawai(jabatan);
			}

			model.addAttribute("method", "tambah");
			model.addAttribute("pesan", "ditambahkan");
			model.addAttribute("nip", nip);
			return "submitPegawai";
		} catch (Exception e) {
			model.addAttribute("errorFlag", "true");
			return "formPegawai";
		}
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	public String updatePegawai(@RequestParam String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.findPegawaiByNIP(nip).get();
		for (JabatanPegawaiModel jabatan: pegawai.getListJabatan()) {
			if (jabatan == null) {
				System.out.println("NULL!");
			} else {
				System.out.println(jabatan.getJabatan().getNama());
				System.out.println(jabatan.getId());
				System.out.println("=====");
			}
		}

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listAllJabatan", jabatanService.getAllJabatan());
		model.addAttribute("method", "ubah");
		return "formPegawai";
	}

	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.POST, params= {"addJabatanRow"})
	public String addUbahRow(@ModelAttribute PegawaiModel pegawai,
						 BindingResult bindingResult,
						 Model model) {
		PegawaiModel pegawaiFromDb = pegawaiService.findPegawaiByNIP(pegawai.getNip()).get();
		if (pegawai.getListJabatan() == null) {
			pegawai.setListJabatan(new ArrayList<JabatanPegawaiModel>());
		}
		pegawai.getListJabatan().add(new JabatanPegawaiModel());
		pegawai.setInstansi(pegawaiFromDb.getInstansi());

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("pageTitle", "Tambah Pegawai");
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listAllJabatan", jabatanService.getAllJabatan());
		model.addAttribute("method", "ubah");
		model.addAttribute("isTambahPegawai", true);

		return "formPegawai";
	}

	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.POST, params= {"removeRow"})
	public String removeUbahRow(@ModelAttribute PegawaiModel pegawai,
							BindingResult bindingResult,
							final HttpServletRequest req,
							Model model) {
		pegawai.getListJabatan().remove(Integer.valueOf(req.getParameter("removeRow")).intValue());

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("pageTitle", "Tambah Pegawai");
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listAllJabatan", jabatanService.getAllJabatan());
		model.addAttribute("method", "ubah");
		model.addAttribute("isTambahPegawai", true);
		return "formPegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params = {"addPegawai"})
	public String submitUpdatePegawai(
			@ModelAttribute PegawaiModel pegawai,
			@RequestParam(value = "id_instansi", required = false) String id_instansi,
			Model model
			) {
		try {
			PegawaiModel pegawaiFromDb = pegawaiService.findPegawaiByNIP(pegawai.getNip()).get();
			InstansiModel instansi = new InstansiModel();
			if (id_instansi != null) {
				instansi = instansiService.getInstansiById(Long.valueOf(id_instansi));
			} else {
				instansi = pegawaiFromDb.getInstansi();
			}
			pegawai.setInstansi(instansi);

			String tanggalLahir = pegawai.getTanggalLahir().toString();
			String[] kodeTanggal = tanggalLahir.split("-");

			List<PegawaiModel> pegawaiMirip = pegawaiService.findAllByTanggalLahirAndTahunMasuk(pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
			String urutan = "01";
			try {
				urutan = (pegawaiMirip.size() < 9) ? "0" + (Integer.toString(pegawaiMirip.size() + 1)) : Integer.toString(pegawaiMirip.size() + 1);
			} catch (Exception e) {

			}
			String nip = instansi.getId() + kodeTanggal[2] + kodeTanggal[1] + kodeTanggal[0].substring(2) + pegawai.getTahunMasuk() + urutan;

			String oldNip = pegawai.getNip();
			pegawai.setNip(nip);
			pegawaiService.editPegawai(pegawai, oldNip);

			PegawaiModel pegawaiAdded = pegawaiService.findPegawaiByNIP(nip).get();
			List<JabatanPegawaiModel> jabatanModelModified = new ArrayList<>();

			for (JabatanPegawaiModel jabatan: pegawaiAdded.getListJabatan()) {
				if (jabatan == null) {
					System.out.println("NULL!");
				} else {
					jabatan.setPegawai(pegawaiAdded);
					System.out.println(jabatan.getJabatan().getNama());
					System.out.println(jabatan.getPegawai().getNama());
					System.out.println(jabatan.getId());
					jabatanModelModified.add(jabatan);
				}
			}
			jabatanPegawaiService.deleteByPegawai(pegawaiFromDb);
			for (JabatanPegawaiModel jabatan: jabatanModelModified) {
				jabatanPegawaiService.addJabatanPegawai(jabatan);
			}
			model.addAttribute("method", "ubah");
			model.addAttribute("newNip", pegawai.getNip());
			model.addAttribute("pesan", "diubah, NIP baru adalah ");
			model.addAttribute("nip", oldNip);
			return "submitPegawai";
		} catch (Exception e) {
			model.addAttribute("errorFlag", "true");
			return "formPegawai";
		}
	}
}
