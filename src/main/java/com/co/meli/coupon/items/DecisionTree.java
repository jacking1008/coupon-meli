package com.co.meli.coupon.items;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public void addToTree(ItemMeli e) {
		suma = suma + e.getPrice();
		items.add(e);
	}
	
	public String toString() {
		StringBuilder salida = new StringBuilder();
		salida.append(" OBJETOS = [ ").append(items.stream().map(e -> e.getId())
				.collect(Collectors.joining(","))).append(" ]").append(", VALOR = [")
				.append(suma.toString()).append(" ]");
		return salida.toString();
	}
	
}
