package com.algaworks.boot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.boot.model.Cerveja;
import com.algaworks.boot.model.Origem;
import com.algaworks.boot.model.Sabor;
import com.algaworks.boot.repository.Estilos;
import com.algaworks.boot.service.CadastroCervejaService;

//5.9
//quando inserir valores ver o vídeo 9.2
// parou no vídeo 10.2
// parou em 11.2



@Controller
public class CervejaController {
	
	@Autowired
	private Estilos estilos;
	
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;

	@RequestMapping("/cervejas/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		return mv;
	}
	
	@RequestMapping(value = "/cervejas/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(cerveja);
		}
		
		// Salvar no banco de dados...
		cadastroCervejaService.salvar(cerveja);
		attributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso!");
		return new ModelAndView("redirect:/cervejas/novo");
	}
	

}
