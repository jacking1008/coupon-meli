package com.co.meli.coupon;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.co.meli.coupon.dto.ReqBenefit;
import com.co.meli.coupon.dto.ResBenefit;
import com.co.meli.coupon.service.BenefitCalcutationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CouponMeliApplicationTests {

	@Autowired private MockMvc mockMvc;
	@Autowired private BenefitCalcutationService service;
	
	private static ReqBenefit mock = new ReqBenefit(List.of("MLA811601010"),Double.valueOf(18000.0));
	public final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Test
	public void fromMethod() throws Exception {
		ResBenefit rta = service.calculate(mock.getItem_ids(), mock.getAmount());
		assertNotNull(rta);
	}
	
	@Test
	public void fromPostMethod() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(mock);
		mockMvc.perform(post("/api/meli/coupon")
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson)).andDo(print())
				.andExpect(status().isOk()).andReturn();
	}

}
