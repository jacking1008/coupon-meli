package com.co.meli.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.meli.coupon.dto.ReqBenefit;
import com.co.meli.coupon.dto.ResGeneric;
import com.co.meli.coupon.exception.MeliException;
import com.co.meli.coupon.service.BenefitCalcutationService;

@RestController
@RequestMapping("/api/meli/coupon")
public class BenefitCouponController {

	@Autowired BenefitCalcutationService service;
	
	@PostMapping
	public ResponseEntity<ResGeneric> calculate(@RequestBody(required = true) ReqBenefit data) {
		try {
			ResGeneric rta = new ResGeneric(200,service.calculate(data.getItem_ids(), data.getAmount()));
			return new ResponseEntity<ResGeneric>(rta,HttpStatus.OK);
		} catch( MeliException e) {
			ResGeneric rta = new ResGeneric(404,e.getMessage());
			return new ResponseEntity<ResGeneric>(rta,HttpStatus.NOT_FOUND);
		}
		
	}
	
	
}
