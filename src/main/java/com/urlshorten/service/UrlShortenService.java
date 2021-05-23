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
	 * Original URL -> Shorten URL 단축
	 */
	public UrlVO shortenUrl(String url) {
		
		// http 문자열 제거
		url = url.replace("http://", "").replace("https://", "");
		
		// 문자열이 비어있는 경우 리턴
		if("".equals(url))
			return null;
		
		// URL -> ID 조회
		Long id = urlShortenRepository.getIdByUrl(url);
		if(id == null) {
			// URL 이 없는 경우, URL 저장
			id = urlShortenRepository.saveUrl(url);
		}
		
		// ID -> Encoding -> Shorten URL 생성
		String shortenUrl = Base62Converter.encoding(id);
		
		UrlVO vo = new UrlVO();
		vo.setShortenUrl(shortenUrl);
		vo.setRequestCnt(getRequestCnt(id));
		
		return vo;
	}
	
	/*
	 * Shorten URL -> Original URL 조회
	 */
	public String getUrl(String shortenUrl) {
		// Shorten URL -> Decoding -> ID 생성
		Long id = Base62Converter.decoding(shortenUrl);
		
		// Request Count 증가
		addRequestCnt(id);
		
		// ID -> URL 조회
		return urlShortenRepository.getUrlById(id);
	}
	
	/*
	 * ID -> Request Count 증가
	 */
	public void addRequestCnt(Long id) {
		urlShortenRepository.setRequestCnt(id, urlShortenRepository.getRequestCnt(id)+1);
	}
	
	/*
	 * ID -> Request Count 조회
	 */
	public Long getRequestCnt(Long id) {
		return urlShortenRepository.getRequestCnt(id);
	}
}
