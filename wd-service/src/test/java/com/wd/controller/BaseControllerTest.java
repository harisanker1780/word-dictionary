package com.wd.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private final String controllerUrl;
	
	private String token;
	
	public BaseControllerTest(String controllerUrl) {
		this.controllerUrl = controllerUrl;
	}
	
	/**
	 * Create a HTTP Get request.
	 * 
	 * @param <T>
	 * @param url
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected <T> T get(String url, Class<T> type) throws Exception {
		
		url = this.controllerUrl + ((url != null) ? url : "");
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
			      .get(url)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andReturn();
		String response = result.getResponse().getContentAsString();
		return objectMapper.readValue(response, type);
	}
	
	/**
	 * Create a HTTP Post request.
	 * 
	 * @param <T>
	 * @param url
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected <T> T post(String url, Object data, Class<T> type) throws Exception {
		
		url = this.controllerUrl + ((url != null) ? "" + url : "");
		String content = (data != null) ? objectMapper.writeValueAsString(data) : "";
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
			      .post(url)
			      .content(content)
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andReturn();
		String response = result.getResponse().getContentAsString();
		return objectMapper.readValue(response, type);
	}
	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param file
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected <T> T postMultipartData(String url, MockMultipartFile  file, Class<T> type) throws Exception {
		
		url = this.controllerUrl + ((url != null) ? url : "");
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
			.multipart(url)
			.file(file)
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
	
		String response = result.getResponse().getContentAsString();
		return objectMapper.readValue(response, type);
	}

}
