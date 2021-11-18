package com.IndieAn.GoFundIndie;

import com.IndieAn.GoFundIndie.Domain.DTO.HealthDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoFundIndieApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("Main - Server Run")
	void contextLoads() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		HealthDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), HealthDTO.class);
		Assertions.assertEquals(result.getServer(), "running");
		Assertions.assertEquals(result.getHealth(), "OK");
	}
}
