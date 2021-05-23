package com.urlshorten.service;

import org.springframework.stereotype.Service;

import com.urlshorten.converter.Base62Converter;
import com.urlshorten.repository.UrlShortenRepository;
import com.urlshorten.vo.UrlVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlShortenService {
	
	private final UrlShortenRepository urlShortenRepository;
	
	/*
	 * Original URL -> Shorten URL
	 */
	public UrlVO shortenUrl(String url) {
		
		// Remove http String
		url = url.replace("http://", "").replace("https://", "");
		
		// If null, return
		if("".equals(url))
			return null;
		
		// Get URL -> ID
		Long id = urlShortenRepository.getIdByUrl(url);
		if(id == null) {
			// Save URL
			id = urlShortenRepository.saveUrl(url);
		}
		
		// ID -> Encoding -> Shorten URL
		String shortenUrl = Base62Converter.encoding(id);
		
		UrlVO vo = new UrlVO();
		vo.setShortenUrl(shortenUrl);
		vo.setRequestCnt(getRequestCnt(id));
		
		return vo;
	}
	
	/*
	 * Get Shorten URL -> Original URL
	 */
	public String getUrl(String shortenUrl) {
		// Shorten URL -> Decoding -> ID
		Long id = Base62Converter.decoding(shortenUrl);
		
		// Request Count++
		addRequestCnt(id);
		
		// ID -> URL 조회
		return urlShortenRepository.getUrlById(id);
	}
	
	/*
	 * ID -> Request Count++
	 */
	public void addRequestCnt(Long id) {
		urlShortenRepository.setRequestCnt(id, urlShortenRepository.getRequestCnt(id)+1);
	}
	
	/*
	 * Get ID -> Request Count
	 */
	public Long getRequestCnt(Long id) {
		return urlShortenRepository.getRequestCnt(id);
	}
}
