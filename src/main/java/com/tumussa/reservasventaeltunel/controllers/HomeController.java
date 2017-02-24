package com.tumussa.reservasventaeltunel.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//v.0.0.1
@Controller
public class HomeController {

	
	@RequestMapping("/")
	public String reservas() {
		return "reservas";
	}
	
	@RequestMapping("/r")
	public ModelAndView show(
			@RequestParam ("nombre") String nombre,
			@RequestParam ("telefono") String telefono,
			@RequestParam ("fecha") String fecha,
			@RequestParam ("comensales") String comensales,
			@RequestParam ("hora") String hora){
		ModelAndView mav = new ModelAndView();
		System.out.println(nombre);
		System.out.println(telefono);
		System.out.println(fecha);
		System.out.println(comensales);
		System.out.println(hora);
		
		
		mav.setViewName("reservaExito");
		return mav;
	}
	
}
