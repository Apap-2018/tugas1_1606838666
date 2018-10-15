package com.apap.tugas1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author faisalridwan
 * PegawaiController
 */
@Controller
public class PegawaiController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		model.addAttribute("pageTitle", "Home Page");
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	public String lihatPegawai(@RequestParam String nip, Model model) {
		
		model.addAttribute("pageTitle", "Detail Pegawai");
		return "home";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String tambahPegawai(Model model) {
		
		model.addAttribute("endpoint", "tambah");
		return "formPegawai";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String submitPegawai(Model model) {
		
		model.addAttribute("pesan", "ditambahkan");
		model.addAttribute("nip", "1234567890");
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
