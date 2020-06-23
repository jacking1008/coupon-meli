package com.co.meli.coupon;

import java.util.List;

import com.co.meli.coupon.dto.ItemMeli;
import com.co.meli.coupon.service.impl.BenefitCalculationServiceImpl;

public class MainClass {

	public static void main(String[] args) {
		BenefitCalculationServiceImpl impl = new BenefitCalculationServiceImpl();
		List<ItemMeli> items = List.of(
				new ItemMeli("MLA1",100.0),
				new ItemMeli("MLA2",210.0),
				new ItemMeli("MLA3",260.0),
				new ItemMeli("MLA4",80.0),
				new ItemMeli("MLA5",90.0));
		impl.getItems(items, 500.0);
	}

}
