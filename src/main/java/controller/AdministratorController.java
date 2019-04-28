package controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import objects.SoldVehicle;
import objects.Vehicle;
import repository.AdministratorRepository;

@RestController
@EnableAutoConfiguration
public class AdministratorController {

	@Autowired
	AdministratorRepository repository;
	
	@RequestMapping("/users")
	public List<Map<String, Object>> allUsers() {
		return repository.getAllUsers();
	}

	@RequestMapping("/addVehicle")
	public int addVehicle(@RequestParam("vehicleMake") String vehicleMake, String vehicleModel, int vehicleYear,
			int vehicleMilage, int vehiclePrice, String vehicleVin, String vehicleDetails, String vehicleDrivetrain,
			String vehicleTransmission, String vehicleColor, String vehicleType,
			@RequestParam("vehicleImage") JSONObject vehicleImage) throws NoSuchFieldException, SecurityException {

		String imageBlob = "";

		if (!vehicleImage.isEmpty()) {
			imageBlob = (String) vehicleImage.get("mainImage");
		}

		return repository.addVehicle(vehicleMake, vehicleModel, vehicleYear, vehicleMilage, vehiclePrice, vehicleVin,
				vehicleDetails, vehicleDrivetrain, vehicleTransmission, vehicleColor, vehicleType, imageBlob);
	}
	
	@RequestMapping("/searchVhclUpdate")
	public List<Vehicle> searchVhclUpdate(@RequestParam("vinValue") String vinValue) {
		List<Vehicle> vhcl = repository.getVehicleForUpdate(vinValue);
		return vhcl;
	}

	@RequestMapping("/updateVehicle")
	public int updateVehicle(@RequestParam("vehicleMake") String vehicleMake, String vehicleModel, int vehicleYear,
			int vehicleMilage, int vehiclePrice, String vehicleVin, String vehicleDetails, String vehicleDrivetrain,
			String vehicleTransmission, String vehicleColor, String vehicleType,
			@RequestParam("vehicleImage") JSONObject vehicleImage) throws NoSuchFieldException, SecurityException {

		String imageBlob = "";

		if (!vehicleImage.isEmpty()) {
			imageBlob = (String) vehicleImage.get("mainImage");
		}

		return repository.updateVehicle(vehicleMake, vehicleModel, vehicleYear, vehicleMilage, vehiclePrice, vehicleVin,
				vehicleDetails, vehicleDrivetrain, vehicleTransmission, vehicleColor, vehicleType, imageBlob);
	}

	@RequestMapping("/report")
	public List<SoldVehicle> getAllReport() {
		return repository.getAllReport();
	}

	@RequestMapping("/vehicleDeleteSearch")
	public List<Vehicle> vehicleDeleteSearch(@RequestParam("vehicleVin") String vehicleVin, String vehicleMake,
			String vehicleModel, int vehicleYear) {
		List<Vehicle> vhcl = repository.getVehicleDeleteSearch(vehicleVin, vehicleMake, vehicleModel, vehicleYear);
		return vhcl;
	}

	@RequestMapping("/vehicleDelete")
	public int vehicleDelete(@RequestParam("vehicleVin") String vehicleVin) {
		int result = repository.getVehicleDelete(vehicleVin);
		return result;
	}
}
