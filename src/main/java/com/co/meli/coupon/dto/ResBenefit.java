package com.co.meli.coupon.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResBenefit {

	private List<String> item_ids;
	private Double total;
}
