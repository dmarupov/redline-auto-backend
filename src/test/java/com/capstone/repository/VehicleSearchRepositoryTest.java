package com.capstone.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import objects.Vehicle;
import repository.VehicleSearchRepository;

public class VehicleSearchRepositoryTest {

	@Mock
	VehicleSearchRepository respository;

	@Before
	public void AtStart() {
		respository = Mockito.mock(VehicleSearchRepository.class);
	}

	@Test
	public void testRepositoryNotNull() throws Exception {
		assertTrue(respository != null);
	}

	@Test
	public void testGetSliderImages() throws Exception {
		ArrayList<String> vehicleMakeLst = new ArrayList<>();
		vehicleMakeLst.add("Test");
		String second = "test";
		List<Vehicle> reponse = new ArrayList<>();
		Vehicle one = new Vehicle();
		one.setId(1);
		one.setMake("Ford");
		one.setModel("Fusion");
		one.setYear("2014");
		reponse.add(one);
		Mockito.when(respository.getSliderImages(vehicleMakeLst, second, 1)).thenReturn(reponse);

		assertEquals(respository.getSliderImages(vehicleMakeLst, second, 1).get(0).getModel(),
				reponse.get(0).getModel());
	}

	@Test
	public void testGetVehicles() throws Exception {
		ArrayList<String> vehicleMakeLst = new ArrayList<>();
		vehicleMakeLst.add("Test");
		String second = "test";
		String third = "test";

		List<Vehicle> reponse = new ArrayList<>();
		Vehicle one = new Vehicle();
		one.setId(1);
		one.setMake("Ford");
		one.setModel("Fusion");
		one.setYear("2014");
		reponse.add(one);

		Mockito.when(respository.getVehicles(second, third, 1, 2, 3)).thenReturn(reponse);

		assertEquals(respository.getVehicles(second, third, 1, 2, 3).get(0).getMake(), reponse.get(0).getMake());
	}

	@Test
	public void testGetVehiclesAdv() throws Exception {
		ArrayList<String> vehicleMakeLst = new ArrayList<>();
		vehicleMakeLst.add("Test");
		String second = "test";
		String third = "test";

		List<Vehicle> reponse = new ArrayList<>();
		Vehicle one = new Vehicle();
		one.setId(1);
		one.setMake("Ford");
		one.setModel("Fusion");
		one.setYear("2014");
		reponse.add(one);

		Mockito.when(respository.getVehiclesAdv(second, third, 1, 2, 3, 4, false, false, false, false, false, false))
				.thenReturn(reponse);

		assertEquals(respository.getVehiclesAdv(second, third, 1, 2, 3, 4, false, false, false, false, false, false)
				.get(0).getMake(), reponse.get(0).getMake());
	}
}
