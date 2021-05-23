package com.urlshorten.controller;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.urlshorten.service.UrlShortenService;
import com.urlshorten.vo.UrlVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UrlShortenController {
	
	private final UrlShortenService urlShortenService;
	
	@Autowired
	Environment environment; 
	
	@GetMapping("/")
	public String input() {
		return "index";
	}
	
	@PostMapping("/")
	public String shorten(@RequestParam(defaultValue = "") String url, Model model) throws Exception {
		
		// Shorten URL
		UrlVO vo = urlShortenService.shortenUrl(url.trim());
		
		// If null, return
		if(vo == null)
			return "index";
		
		// Set Port, Host Address
		String port = environment.getProperty("local.server.port");	
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		
		// Set Attribute
		String shortenUrl = "http://" + hostAddress + ":" + port + "/" + vo.getShortenUrl();
		vo.setShortenUrl(shortenUrl);
		model.addAttribute("urlVO", vo);
		
		return "index";
	}
	
	@GetMapping("/{url}")
	public String redirect(@PathVariable("url") String shortenUrl) {
		
		// Get Original URL
		String url = urlShortenService.getUrl(shortenUrl);
		
		// Redirect
		if(url != null) {
			url = "redirect:"+"http://" + url;
		}else {
			url = "errorPage";
		}
		return url;
	}
}
