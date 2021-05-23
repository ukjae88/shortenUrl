package com.urlshorten.repository;

public interface UrlShortenRepository {
	
	Long saveUrl(String url);
	Long getIdByUrl(String url);
	String getUrlById(Long id);
	Long setRequestCnt(Long id, Long cnt);
	Long getRequestCnt(Long id);
	
}
