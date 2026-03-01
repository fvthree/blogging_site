package com.velzon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Charts {
	
	@GetMapping("/charts-apex-line")
	public String apex_line() {
		return "charts/apex-line";
	}
	
	@GetMapping("/charts-apex-area")
	public String apex_area() {
		return "charts/apex-area";
	}
	
	@GetMapping("/charts-apex-column")
	public String apex_column() {
		return "charts/apex-column";
	}
	
	@GetMapping("/charts-apex-bar")
	public String apex_bar() {
		return "charts/apex-bar";
	}
	
	@GetMapping("/charts-apex-mixed")
	public String apex_mixed() {
		return "charts/apex-mixed";
	}
	
	@GetMapping("/charts-apex-timeline")
	public String apex_timeline() {
		return "charts/apex-timeline";
	}
	
	@GetMapping("/charts-apex-candlestick")
	public String apex_candlestick() {
		return "charts/apex-candlestick";
	}
	
	@GetMapping("/charts-apex-boxplot")
	public String apex_boxplot() {
		return "charts/apex-boxplot";
	}
	
	@GetMapping("/charts-apex-bubble")
	public String apex_bubble() {
		return "charts/apex-bubble";
	}
	
	@GetMapping("/charts-apex-scatter")
	public String apex_scatter() {
		return "charts/apex-scatter";
	}
	
	@GetMapping("/charts-apex-heatmap")
	public String apex_heatmap() {
		return "charts/apex-heatmap";
	}
	
	@GetMapping("/charts-apex-treemap")
	public String apex_treemap() {
		return "charts/apex-treemap";
	}
	
	@GetMapping("/charts-apex-pie")
	public String apex_pie() {
		return "charts/apex-pie";
	}
	
	@GetMapping("/charts-apex-radialbar")
	public String apex_radialbar() {
		return "charts/apex-radialbar";
	}
	
	@GetMapping("/charts-apex-radar")
	public String apex_radar() {
		return "charts/apex-radar";
	}
	
	@GetMapping("/charts-apex-polar")
	public String apex_polar() {
		return "charts/apex-polar";
	}
	
	@GetMapping("/charts-chartjs")
	public String chartjs() {
		return "charts/chartjs";
	}
	
	@GetMapping("/charts-echarts")
	public String echarts() {
		return "charts/echarts";
	}
	
}
