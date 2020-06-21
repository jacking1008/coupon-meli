package com.co.meli.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.meli.coupon.dto.ReqBenefit;
import com.co.meli.coupon.dto.ResBenefit;
import com.co.meli.coupon.service.BenefitCalcutationService;

@RestController
@RequestMapping("/api/meli/coupon")
public class BenefitCouponController {

	@Autowired BenefitCalcutationService service;
	
	@PostMapping
	public ResponseEntity<ResBenefit> calculate(@RequestBody(required = true) ReqBenefit data) {
		ResBenefit rta = service.calculate(data.getItem_ids(), data.getAmount());
		if(rta != null) {
			return new ResponseEntity<ResBenefit>(rta,HttpStatus.OK);
		} else {
			return new ResponseEntity<ResBenefit>(rta,HttpStatus.NOT_FOUND);
		}
		
	}
	
	
}
