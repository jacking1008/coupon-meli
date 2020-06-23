package com.co.meli.coupon;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.co.meli.coupon.dto.ReqBenefit;
import com.co.meli.coupon.dto.ResBenefit;
import com.co.meli.coupon.exception.MeliException;
import com.co.meli.coupon.service.BenefitCalcutationService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CouponMeliServiceTests {

	@Autowired private BenefitCalcutationService service;
	private static ReqBenefit mock = new ReqBenefit(List.of("MLA811601010"),Double.valueOf(18000.0));
	
	@Test
	public void fromMethod() throws Exception {
		mock.setItem_ids(List.of("MLA811601010"));
		mock.setAmount(Double.valueOf(18000.0));
		ResBenefit rta = service.calculate(mock.getItem_ids(), mock.getAmount());
		assertNotNull(rta);
	}
	
	@ParameterizedTest
	@ValueSource(classes = { 
	  MeliException.class
	})
	public void methodWrapsException() {
		mock.setItem_ids(List.of("MLA1"));
		assertThrows(MeliException.class, () ->  service.calculate(mock.getItem_ids(), mock.getAmount()));
	}
	
	@ParameterizedTest
	@ValueSource(classes = { 
	  MeliException.class
	})
	public void highValues() {
		mock.setItem_ids(List.of("MLA811601010"));
		mock.setAmount(Double.valueOf(5000.0));
		assertThrows(MeliException.class, () ->  service.calculate(mock.getItem_ids(), mock.getAmount()));
	}
	
}
