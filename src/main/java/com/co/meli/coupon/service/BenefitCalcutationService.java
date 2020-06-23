package com.co.meli.coupon.service;

import java.util.List;

import com.co.meli.coupon.dto.ResBenefit;
import com.co.meli.coupon.exception.MeliException;

public interface BenefitCalcutationService {

	ResBenefit calculate(List<String> products, Double amount) throws MeliException;
	
}
