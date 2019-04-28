package controller;

import java.util.ArrayList;
//import java.util.Base64;
import java.util.List;
import java.util.Map;

//import org.json.JSONException;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import objects.Vehicle;
import repository.VehicleSearchRepository;

@RestController
@EnableAutoConfiguration
public class VehicleSearchController {

	@Autowired
	VehicleSearchRepository repository;

	@RequestMapping("/slider-imgs")
	public List<Vehicle> getSliderImages(@RequestParam("vehicleMake") ArrayList<String> vehicleMake,
			String vehicleModel, int vehicleYear) {
		List<Vehicle> vhcl = repository.getSliderImages(vehicleMake, vehicleModel, vehicleYear);
		return vhcl;
	}

	@RequestMapping("/monthly-special")
	public List<Vehicle> getMonthlySpecial() {
		List<Vehicle> vhcl = repository.getMonthlySpecial();
		return vhcl;
	}

	@RequestMapping("/vehicleSearch")
	public List<Vehicle> vehicleSearch(@RequestParam("vehicleMake") String vehicleMake, String vehicleModel, int vehicleYear, int priceLow, int priceHigh) {
		List<Vehicle> vhcl = repository.getVehicles(vehicleMake, vehicleModel, vehicleYear, priceLow, priceHigh);
		return vhcl;
	}

	@RequestMapping("/advVehicleSearch")
	public List<Vehicle> advVehicleSearch(@RequestParam("vehicleMake") String vehicleMake, String vehicleModel, int yearLow, int yearHigh,
			int priceLow, int priceHigh, boolean sedan, boolean hatchback, boolean convertible, boolean coupe,
			boolean suv, boolean pickup) {
		List<Vehicle> vhcl = repository.getVehiclesAdv(vehicleMake, vehicleModel, yearLow, yearHigh, priceLow, priceHigh, sedan,
				hatchback, convertible, coupe, suv, pickup);
		return vhcl;
	}

	@RequestMapping("/vehicles")
	public List<Map<String, Object>> getAllVehicles() {
		return repository.getAllVehicles();
	}

}
