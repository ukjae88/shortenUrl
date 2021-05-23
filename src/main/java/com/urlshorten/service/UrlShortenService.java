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
	 * Original URL -> Shorten URL ����
	 */
	public UrlVO shortenUrl(String url) {
		
		// http ���ڿ� ����
		url = url.replace("http://", "").replace("https://", "");
		
		// ���ڿ��� ����ִ� ��� ����
		if("".equals(url))
			return null;
		
		// URL -> ID ��ȸ
		Long id = urlShortenRepository.getIdByUrl(url);
		if(id == null) {
			// URL �� ���� ���, URL ����
			id = urlShortenRepository.saveUrl(url);
		}
		
		// ID -> Encoding -> Shorten URL ����
		String shortenUrl = Base62Converter.encoding(id);
		
		UrlVO vo = new UrlVO();
		vo.setShortenUrl(shortenUrl);
		vo.setRequestCnt(getRequestCnt(id));
		
		return vo;
	}
	
	/*
	 * Shorten URL -> Original URL ��ȸ
	 */
	public String getUrl(String shortenUrl) {
		// Shorten URL -> Decoding -> ID ����
		Long id = Base62Converter.decoding(shortenUrl);
		
		// Request Count ����
		addRequestCnt(id);
		
		// ID -> URL ��ȸ
		return urlShortenRepository.getUrlById(id);
	}
	
	/*
	 * ID -> Request Count ����
	 */
	public void addRequestCnt(Long id) {
		urlShortenRepository.setRequestCnt(id, urlShortenRepository.getRequestCnt(id)+1);
	}
	
	/*
	 * ID -> Request Count ��ȸ
	 */
	public Long getRequestCnt(Long id) {
		return urlShortenRepository.getRequestCnt(id);
	}
}
