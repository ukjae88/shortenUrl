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
		// URL -> ID 조회
		Long id = urlShortenRepository.getIdByUrl(url);
		if(id == null) {
			// URL 이 없는 경우, URL 저장
			id = urlShortenRepository.saveUrl(url);
		}
		
		// ID -> Encoding -> Shorten URL 생성
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
		// Shorten URL -> Decoding -> ID 생성
		Long id = Base62Converter.decoding(shortenUrl);
		
		// Request Count 증가
		urlShortenRepository.setRequestCnt(id, urlShortenRepository.getRequestCnt(id)+1);
		
		// ID -> URL 조회
		String url = urlShortenRepository.getUrlById(id);
		
		// [then]
		assertEquals("www.google.com", url);
	}
	
}
