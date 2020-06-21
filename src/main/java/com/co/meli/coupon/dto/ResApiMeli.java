package com.co.meli.coupon.dto;

import java.util.LinkedHashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResApiMeli {
	
	private Integer code;
	private LinkedHashMap<String, Object> body;

}
