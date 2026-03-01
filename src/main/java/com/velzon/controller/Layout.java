package com.velzon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Layout {

	@GetMapping("/layouts-horizontal")
	public String index() {
		return "layouts/layout-horizontal";
	}
	
	@GetMapping("/layouts-detached")
	public String layouts_detached() {
		return "layouts/layout-detached";
	}
	
	@GetMapping("/layouts-two-column")
	public String layouts_two_column() {
		return "layouts/layout-two-column";
	}
	
	@GetMapping("/layouts-vertical-hovered")
	public String layouts_vertical_hovered() {
		return "layouts/layout-vertical-hovered";
	}
}
