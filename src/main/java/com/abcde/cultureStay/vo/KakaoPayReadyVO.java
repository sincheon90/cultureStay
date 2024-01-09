package com.abcde.cultureStay.vo;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
public class KakaoPayReadyVO {
	
	 //response
    private String tid, next_redirect_pc_url;
    private Date created_at;

}
