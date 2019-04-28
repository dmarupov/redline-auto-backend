package com.capstone.repository;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

import objects.Vehicle;
import repository.DropDownRepository;

import org.springframework.test.context.junit4.SpringRunner;

@RestController
@EnableAutoConfiguration
public class DropDownRepositoryTest {

	@Mock
	DropDownRepository repository;

	@Before
	public void AtStart() {
		repository = Mockito.mock(DropDownRepository.class);
	}

	@Test
	public void testRepositoryNotNull() {
		assertTrue(repository != null);
	}

	@Test
	public void testMakeDropDownList() throws ClientProtocolException, IOException {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		Map<String, Object> mockData = new HashMap<String, Object>();	
		mockData.put("1", "Acura");
		res.add(mockData);
		Mockito.when(repository.getMakeDropDownList()).thenReturn(res);
		List<Map<String, Object>> test = repository.getMakeDropDownList();
		System.out.println(test.get(0).get("1"));
		assertEquals(test, res);
	}

	@Test
	public void testModelDropDownList() throws ClientProtocolException, IOException {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		Map<String, Object> mockData = new HashMap<String, Object>();	
		mockData.put("1", "TSX");
		res.add(mockData);
		Mockito.when(repository.getModelDropDownList(1)).thenReturn(res);
		List<Map<String, Object>> test = repository.getModelDropDownList(1);
		System.out.println(test.get(0).get("1"));
		assertEquals(test, res);
	}
	
	@Test
	public void testGetYearDropDownList() throws ClientProtocolException, IOException {
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		Map<String, Object> mockData = new HashMap<String, Object>();	
		mockData.put("1", "2016");
		res.add(mockData);
		Mockito.when(repository.getYearDropDownList()).thenReturn(res);
		List<Map<String, Object>> test = repository.getYearDropDownList();
		System.out.println(test.get(0).get("1"));
		assertEquals(test, res);
	}
}
