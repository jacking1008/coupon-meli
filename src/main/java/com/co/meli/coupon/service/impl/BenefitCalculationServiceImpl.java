package com.co.meli.coupon.service.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
	List<ItemMeli> itemsTemp = List.of(
			new ItemMeli("MLA1",100.0),
			new ItemMeli("MLA2",210.0),
			new ItemMeli("MLA3",260.0),
			new ItemMeli("MLA4",80.0),
			new ItemMeli("MLA5",90.0));
	
	@Override
	public ResBenefit calculate(List<String> products, Double amount) throws MeliException {
		List<ItemMeli> itemsPrices = getObject(products);
		if(!itemsPrices.isEmpty()) {
			DecisionTree rta = this.getItems(itemsPrices, amount);
			if(rta.getItems() != null || !rta.getItems().isEmpty()) {
				return new ResBenefit(rta.getItems().stream()
						.sorted(Comparator.comparing(ItemMeli::getId))
						.map(e -> e.getId())
						.collect(Collectors.toList()),rta.getSuma());
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
	
	public DecisionTree getItems(List<ItemMeli> items, Double top) {
		items = items.stream().filter(e -> e.getPrice() <= top).collect(Collectors.toList());
		DecisionTree decision = new DecisionTree(null,0.0);
		items = items.stream().sorted((s1,s2)-> s2.getPrice().compareTo(s1.getPrice())).collect(Collectors.toList());
		for(int i = 0; i < items.size(); i++ ) {
			DecisionTree tree = new DecisionTree();
			tree.addToTree(items.get(i));
			for(int j = items.size() - 1; j > i; j-- ) {
				if( tree.getSuma() + items.get(j).getPrice() <= top ) {
					tree.addToTree(items.get(j));
				}
			}
			if(decision.getSuma() < tree.getSuma()) {
				decision = tree;
			}
		}
		System.out.print(decision.toString());
        return decision;
	}
	
}
