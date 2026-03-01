package com.velzon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Maps {

	@GetMapping("/maps-google")
	public String maps_google() {
		return "maps/google";
	}
	
	@GetMapping("/maps-vector")
	public String maps_vector() {
		return "maps/vector";
	}
	
	@GetMapping("/maps-leaflet")
	public String maps_leaflet() {
		return "maps/leaflet";
	}
}
