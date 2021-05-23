package com.urlshorten.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MapUrlShortenRepository implements UrlShortenRepository{
	
	private Map<Long, String> mapUrl = new HashMap<>();
	private Map<Long, Long> mapRequest = new HashMap<>();
	
	/*
	 * URL 저장
	 */
	public Long saveUrl(String url) {
		Long seq = (long)mapUrl.size()+1;
		mapUrl.put(seq, url);
		return seq;
	}
	
	/*
	 * URL -> ID 조회
	 */
	public Long getIdByUrl(String url) {
		Long result = null;
		for (Long id : mapUrl.keySet()) {
			if(url.equals(mapUrl.get(id))) {
				result = id;
				break;
			}
		}
		return result;
	}
	
	/*
	 * ID -> URL 조회
	 */
	public String getUrlById(Long id) {
		return mapUrl.get(id);
	}
	
	/*
	 * ID -> Set Request Count
	 */
	public Long setRequestCnt(Long id, Long cnt) {
		if(mapUrl.containsKey(id)) {
			mapRequest.put(id, cnt);
		}
		return cnt;
	}
	
	/*
	 * ID -> Get Request Count
	 */
	public Long getRequestCnt(Long id) {
		return mapRequest.getOrDefault(id, 0L);
	}
}
