package com.co.meli.coupon.service;

import java.util.List;

import com.co.meli.coupon.dto.ResBenefit;

public interface BenefitCalcutationService {

	ResBenefit calculate(List<String> products, Double amount);
	
}
