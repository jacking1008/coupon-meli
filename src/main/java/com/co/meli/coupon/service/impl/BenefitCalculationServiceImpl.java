package com.co.meli.coupon.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.co.meli.coupon.dto.ItemMeli;
import com.co.meli.coupon.dto.ResApiMeli;
import com.co.meli.coupon.dto.ResBenefit;
import com.co.meli.coupon.exception.ErrorMeli;
import com.co.meli.coupon.exception.MeliException;
import com.co.meli.coupon.items.DecisionTree;
import com.co.meli.coupon.service.BenefitCalcutationService;
import com.google.gson.Gson;

@Service("BenefitCalculation")
public class BenefitCalculationServiceImpl implements BenefitCalcutationService {

	@Value("${meli.api.base}") public String urlBaseApiMeli;
	public static final String urlItem = "items";
	RestTemplate restClient = new RestTemplate();
	Gson gson = new Gson();
	
	@Override
	public ResBenefit calculate(List<String> products, Double amount) throws MeliException {
		List<ItemMeli> itemsPrices = getObject(products);
		if(!itemsPrices.isEmpty()) {
			//VALIDATION IF THE PRODUCT HAS A HIGHER VALUE
			itemsPrices = itemsPrices.stream().filter(e -> e.getPrice() <= amount).collect(Collectors.toList());
			if(!itemsPrices.isEmpty()) {
				List<String> items = itemsPrices.stream().map(e -> e.getId()).collect(Collectors.toList());
				Double total = itemsPrices.stream().map(e -> e.getPrice()).reduce(0.0,Double::sum);
				return new ResBenefit(items,total);
			} else {
				throw ErrorMeli.NO_RESPUESTA.throwError();
			}
		} else {
			throw ErrorMeli.NO_RESPUESTA.throwError();
		}
	}
	
	private List<ItemMeli> getObject(List<String> id_products) throws MeliException {
		StringBuilder urlFinal = new StringBuilder();
		urlFinal.append(urlBaseApiMeli).append(urlItem).append("?ids=").append(id_products.stream().collect(Collectors.joining(",")));
		ResponseEntity<ResApiMeli[]> service = restClient.exchange(urlFinal.toString(), HttpMethod.GET, null, ResApiMeli[].class);
		if(service.getStatusCode().equals(HttpStatus.OK)) {
			return Arrays.stream(service.getBody())
					.filter(e -> e.getCode().equals(200))
					.map( e -> new ItemMeli((String)e.getBody().get("id"),Double.valueOf((int)e.getBody().get("price"))))
					.collect(Collectors.toList());
		} else {
			throw ErrorMeli.LISTA_ERRONEA.throwError();
		}
   
	}
	
	private void getItems(List<ItemMeli> items) {
		List<DecisionTree> decTree = new ArrayList<DecisionTree>();
		for(ItemMeli item: items) {
			DecisionTree tree = new DecisionTree();
			item.
		}
        
	}
	
}
