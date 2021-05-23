package com.urlshorten.vo;

import lombok.Data;

@Data
public class UrlVO {
	private String shortenUrl;
	private Long requestCnt;
}

