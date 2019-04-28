package com.capstone.repository;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import objects.Vehicle;
import repository.AdministratorRepository;

public class AdministratorRepositoryTest {

	@Mock
	AdministratorRepository repository;
	
	@Before
	public void AtStart() {
		repository = Mockito.mock(AdministratorRepository.class);
	}
	
	@Test
	public void testRepositoryNotNull() throws Exception {
		assertTrue(repository != null);
	}
	
	@Test
	public void testGetVehicleForUpdate() throws Exception {
		List<Vehicle> response = new ArrayList<Vehicle>();
		Vehicle car = new Vehicle();
		car.setMake("Volvo");
		response.add(car);
		String key = "volvo";
		Mockito.when(repository.getVehicleForUpdate(key)).thenReturn(response);
		assertEquals(repository.getVehicleForUpdate(key), response);
		assertNotEquals(repository.getVehicleForUpdate("Something else"), response);
	}
	
	@Test
	public void testAddVehicle() throws Exception {
		String a="test", b="test", c="test", d="test", e="test", f="test", g="test", h="test", i="test";
		int aa = 0, bb = 0, cc = 0;
		Mockito.when(repository.addVehicle("", "", 0, 0, 0, "", "", "", "", "", "", "")).thenReturn(2);
		Mockito.when(repository.addVehicle(a, b, aa, bb, cc, c, d, e, f, g, h, i)).thenReturn(1);
		assertEquals(repository.addVehicle("", "", 0, 0, 0, "", "", "", "", "", "", ""), 2);
		assertEquals(repository.addVehicle(a, b, aa, bb, cc, c, d, e, f, g, h, i), 1);
	}
	
	@Test
	public void testGetVehicleDelete() throws Exception {
		Mockito.when(repository.getVehicleDelete("test")).thenReturn(2);
		assertEquals(repository.getVehicleDelete("test"), 2);
	}
	
	@Test
	public void testGetVehicleDeleteSearch() throws Exception {
		List<Vehicle> response = new ArrayList<Vehicle>();
		Vehicle car = new Vehicle();
		car.setMake("Volvo");
		response.add(car);
		Mockito.when(repository.getVehicleDeleteSearch("test", "test", "test", 0)).thenReturn(response);
		assertEquals(repository.getVehicleDeleteSearch("test", "test", "test", 0), response);
		assertNotEquals(repository.getVehicleDeleteSearch("", "", "", 0), response);
	}
}
