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
	 * Save URL
	 */
	public Long saveUrl(String url) {
		Long seq = (long)mapUrl.size()+1;
		mapUrl.put(seq, url);
		return seq;
	}
	
	/*
	 * Get URL -> ID
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
	 * Get ID -> URL
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
