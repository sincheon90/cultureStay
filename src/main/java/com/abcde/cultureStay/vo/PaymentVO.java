package com.abcde.cultureStay.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentVO {
	
	int pay_code;
	int odr_code;
	String pay_method;
	Date pay_date;
	int pay_tot_price;
	int pay_rest_price;
	String pay_nobank_user;
	String pay_nobank;

}
