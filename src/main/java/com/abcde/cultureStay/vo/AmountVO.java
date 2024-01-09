package com.abcde.cultureStay.vo;

import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
public class AmountVO {
	
	 private Integer total, tax_free, vat, point, discount;

}
