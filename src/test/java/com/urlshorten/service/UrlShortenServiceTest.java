package com.urlshorten.service;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.urlshorten.converter.Base62Converter;
import com.urlshorten.repository.MapUrlShortenRepository;
import com.urlshorten.repository.UrlShortenRepository;

class UrlShortenServiceTest {
	
	UrlShortenRepository urlShortenRepository = new MapUrlShortenRepository();
	
	@Test
	void shortenUrl() {
		
		// [given]
		String url = "http://www.google.com";
		url = url.replace("http://", "").replace("https://", "");
		
		// [when]
		// Get URL -> ID
		Long id = urlShortenRepository.getIdByUrl(url);
		if(id == null) {
			// Save URL
			id = urlShortenRepository.saveUrl(url);
		}
		
		// ID -> Encoding -> Shorten URL
		String shortenUrl = Base62Converter.encoding(id);
		
		// [then]
		assertEquals("B", shortenUrl);
	}
	
	@Test
	void getUrl() {
		
		// [given]
		urlShortenRepository.saveUrl("www.google.com");
		String shortenUrl = "B";
		
		// [when]
		// Shorten URL -> Decoding -> ID
		Long id = Base62Converter.decoding(shortenUrl);
		
		// Request Count++
		urlShortenRepository.setRequestCnt(id, urlShortenRepository.getRequestCnt(id)+1);
		
		// Get ID -> URL
		String url = urlShortenRepository.getUrlById(id);
		
		// [then]
		assertEquals("www.google.com", url);
	}
	
}
