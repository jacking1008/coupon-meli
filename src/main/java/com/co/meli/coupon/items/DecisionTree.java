package com.co.meli.coupon.items;

import java.util.ArrayList;
import java.util.List;

import com.co.meli.coupon.dto.ItemMeli;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DecisionTree {
	
	private List<ItemMeli> items;
	private Double suma;
	
	public DecisionTree() {
		items = new ArrayList<ItemMeli>();
		suma = 0.0;
	}
	
}
