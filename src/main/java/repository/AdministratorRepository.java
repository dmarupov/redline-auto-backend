package repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import objects.SoldVehicle;
import objects.Vehicle;

import org.springframework.jdbc.core.RowMapper;



@Repository
public class AdministratorRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static String UPDATE_VHCL = "UPDATE RLA_VHCL ";
	private static String ADD_VHCL = "INSERT INTO RLA_VHCL (VHCL_VIN, VHCL_MAKE, VHCL_MODL, VHCL_YEAR, VHCL_TRNS, "
			+ "VHCL_DRTN, VHCL_COLR, VHCL_DESC, VHCL_TYPE, VHCL_PRICE, VHCL_MILG, VHCL_PICS) VALUES(";

	public List<Map<String, Object>> getAllUsers() {
		return jdbcTemplate.queryForList("Select * from RLA_DPDN_USERS");
	}

	public int addVehicle(String vehicleMake, String vehicleModel, int vehicleYear, int vehicleMilage, int vehiclePrice,
			String vehicleVin, String vehicleDetails, String vehicleDrivetrain, String vehicleTransmission,
			String vehicleColor, String vehicleType, String imageBlob) {

		String ADD_VHCL_QUERY = getQueryToAddVehicle(vehicleMake, vehicleModel, vehicleYear, vehicleMilage,
				vehiclePrice, vehicleVin, vehicleDetails, vehicleDrivetrain, vehicleTransmission, vehicleColor,
				vehicleType, imageBlob);

		return jdbcTemplate.update(ADD_VHCL_QUERY);
	}

	public List<Vehicle> getVehicleForUpdate(String vinValue) {
		String SEARCH_VHCL_QUERY = "select * from RLA_VHCL where VHCL_VIN = ?";
		return jdbcTemplate.query(SEARCH_VHCL_QUERY, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, vinValue);
			}
		}, new VehicleImageRowMapper());
	}

	public int updateVehicle(String vehicleMake, String vehicleModel, int vehicleYear, int vehicleMilage,
			int vehiclePrice, String vehicleVin, String vehicleDetails, String vehicleDrivetrain,
			String vehicleTransmission, String vehicleColor, String vehicleType, String imageBlob) {

		String UPDATE_VHCL_QUERY = getQueryToUpdateVehicle(vehicleMake, vehicleModel, vehicleYear, vehicleMilage,
				vehiclePrice, vehicleVin, vehicleDetails, vehicleDrivetrain, vehicleTransmission, vehicleColor,
				vehicleType, imageBlob);

		return jdbcTemplate.update(UPDATE_VHCL_QUERY);
	}

	public List<SoldVehicle> getAllReport() {
		String ALL_SOLD_VHCLS = "SELECT * FROM RLA_VHCL_SOLD";
		List<SoldVehicle> soldVehicleList = new ArrayList<SoldVehicle>();
		soldVehicleList = jdbcTemplate.query(ALL_SOLD_VHCLS, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
			}
		}, new SoldVehicleRowMapper());

		return getSalesReport(soldVehicleList);
	}

	public List<Vehicle> getVehicleDeleteSearch(String vehicleVin, String vehicleMake, String vehicleModel,
			int vehicleYear) {

		String SEARCH_VHCL_QUERY = getQueryToDeleteVehicle(vehicleVin, vehicleMake, vehicleModel, vehicleYear);
		
		return jdbcTemplate.query(SEARCH_VHCL_QUERY, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
			}
		}, new VehicleImageRowMapper());
	}

	public int getVehicleDelete(String vehicleVin) {
		String DELETE_VHCL_QUERY = "delete from RLA_VHCL where VHCL_VIN = '" + vehicleVin + "'";
		return jdbcTemplate.update(DELETE_VHCL_QUERY);
	}

	private List<SoldVehicle> getSalesReport(List<SoldVehicle> soldVehicles) {
		
		Map<String, Integer> result = new HashMap<>();
		DecimalFormat df2 = new DecimalFormat("#.##");
		ArrayList<String> makes = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();
		HashMap<String, Integer> makesAndPrice = new HashMap<String, Integer>();
		String mostSoldMake = "";
		String leastSoldMake = "";
		String mostMake = "";
		String leastMake = "";
		double totalNumberOfVehiclesSold = 0;

		for (int i = 0; i < soldVehicles.size(); i++) {
			makes.add(soldVehicles.get(i).getMake());
			makesAndPrice.put(soldVehicles.get(i).getMake(), soldVehicles.get(i).getPrice());
		}

		for (String unique : new HashSet<>(makes)) {
			result.put(unique, Collections.frequency(makes, unique));
		}

		for (int numberOfVehicles : result.values()) {
			totalNumberOfVehiclesSold += numberOfVehicles;
		}

		double max = Collections.max(result.values());
		double min = Collections.min(result.values());

		for (Entry<String, Integer> entry : result.entrySet()) {
			if (entry.getValue() == max) {
				keys.add(entry.getKey());
				mostMake = entry.getKey();
			}
			if (entry.getValue() == min) {
				keys.add(entry.getKey());
				leastMake = entry.getKey();
			}
		}
		
		mostSoldMake = df2.format(((max / totalNumberOfVehiclesSold) * 100)) + "% of your vehicles sold are: "
				+ mostMake;
		leastSoldMake = df2.format(((min / totalNumberOfVehiclesSold) * 100)) + "% of your vehicles sold are: "
				+ leastMake;

		for (SoldVehicle sv : soldVehicles) {
			sv.setMostSoldVehicle(mostSoldMake);
			sv.setLeastSoldVehicle(leastSoldMake);
		}
		
		return soldVehicles;
	}

	private String getQueryToAddVehicle(String vehicleMake, String vehicleModel, int vehicleYear, int vehicleMilage,
			int vehiclePrice, String vehicleVin, String vehicleDetails, String vehicleDrivetrain,
			String vehicleTransmission, String vehicleColor, String vehicleType, String imageBlob) {

		String ADD_VHCL_QUERY = ADD_VHCL;

		if (!vehicleVin.isEmpty()) {
			ADD_VHCL_QUERY = ADD_VHCL_QUERY + "'" + vehicleVin + "'";
		}
		if (!vehicleMake.isEmpty()) {
			ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + vehicleMake + "'";
		}
		if (!vehicleModel.isEmpty()) {
			ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + vehicleModel + "'";
		}
		if (vehicleYear != 0) {
			ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", " + vehicleYear;
		}
		ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + vehicleTransmission + "'";
		ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + vehicleDrivetrain + "'";
		ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + vehicleColor + "'";
		ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + vehicleDetails + "'";
		ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + vehicleType + "'";
//		if (!vehicleColor.isEmpty()) {
//			ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + vehicleColor + "'";
//		}
		
		if (vehiclePrice != 0) {
			ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", " + vehiclePrice;
		}
		if (vehicleMilage != 0) {
			ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", " + vehicleMilage;
		}
		ADD_VHCL_QUERY = ADD_VHCL_QUERY + ", '" + imageBlob + "'";
		ADD_VHCL_QUERY = ADD_VHCL_QUERY + ")";

		return ADD_VHCL_QUERY;
	}

	private String getQueryToUpdateVehicle(String vehicleMake, String vehicleModel, int vehicleYear, int vehicleMilage,
			int vehiclePrice, String vehicleVin, String vehicleDetails, String vehicleDrivetrain,
			String vehicleTransmission, String vehicleColor, String vehicleType, String imageBlob) {

		String UPDATE_VHCL_QUERY = UPDATE_VHCL;

		if (!vehicleVin.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + "SET VHCL_VIN = '" + vehicleVin + "'";
		}
		if (!vehicleMake.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_MAKE = '" + vehicleMake + "'";
		}
		if (!vehicleModel.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_MODL = '" + vehicleModel + "'";
		}
		if (vehicleYear != 0) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_YEAR = " + vehicleYear;
		}
		if (vehicleMilage != 0) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_MILG = " + vehicleMilage;
		}
		if (vehiclePrice != 0) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_PRICE = " + vehiclePrice;
		}
		if (!vehicleDetails.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_DESC = '" + vehicleDetails + "'";
		}
		if (!vehicleDrivetrain.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_DRTN = '" + vehicleDrivetrain + "'";
		}
		if (!vehicleTransmission.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_TRNS = '" + vehicleTransmission + "'";
		}
		if (!vehicleColor.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_COLR = '" + vehicleColor + "'";
		}
		if (!vehicleType.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_TYPE = '" + vehicleType + "'";
		}
		if (!imageBlob.isEmpty()) {
			UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + ", VHCL_PICS = '" + imageBlob + "'";
		}

		UPDATE_VHCL_QUERY = UPDATE_VHCL_QUERY + " WHERE VHCL_VIN = '" + vehicleVin + "'";

		return UPDATE_VHCL_QUERY;
	}

	private String getQueryToDeleteVehicle(String vehicleVin, String vehicleMake, String vehicleModel,
			int vehicleYear) {

		String SEARCH_VHCL_QUERY = "select * from RLA_VHCL where 1=1";

		if (!vehicleVin.isEmpty()) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + "  and VHCL_VIN = '" + vehicleVin + "'";
		}
		if (!vehicleMake.isEmpty()) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + "  and VHCL_MAKE = '" + vehicleMake + "'";
		}
		if (!vehicleModel.isEmpty()) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and VHCL_MODL = '" + vehicleModel + "'";
		}
		if (vehicleYear != 0) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and VHCL_YEAR = " + vehicleYear;
		}

		return SEARCH_VHCL_QUERY;
	}

	private class VehicleImageRowMapper implements RowMapper<Vehicle> {
		@Override
		public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			Vehicle vehicle = new Vehicle();
			vehicle.setId(rs.getInt("VHCL_ID"));
			vehicle.setVin(rs.getString("VHCL_VIN"));
			vehicle.setImageBlobStr(rs.getString("VHCL_PICS"));
			vehicle.setMake(rs.getString("VHCL_MAKE"));
			vehicle.setModel(rs.getString("VHCL_MODL"));
			vehicle.setYear(rs.getString("VHCL_YEAR"));
			vehicle.setPrice(rs.getString("VHCL_PRICE"));
			vehicle.setMilage(rs.getString("VHCL_MILG"));
			vehicle.setDriveTrain(rs.getString("VHCL_DRTN"));
			vehicle.setTransmission(rs.getString("VHCL_TRNS"));
			vehicle.setVehicleDescription(rs.getString("VHCL_DESC"));
			vehicle.setColor(rs.getString("VHCL_COLR"));
			vehicle.setType(rs.getString("VHCL_TYPE"));
			return vehicle;
		}
	}

	private class SoldVehicleRowMapper implements RowMapper<SoldVehicle> {
		@Override
		public SoldVehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
			SoldVehicle soldVehicle = new SoldVehicle();
			soldVehicle.setId(rs.getInt("VHCL_ID"));
			soldVehicle.setVin(rs.getString("VHCL_VIN"));
			soldVehicle.setMake(rs.getString("VHCL_MAKE"));
			soldVehicle.setModel(rs.getString("VHCL_MODL"));
			soldVehicle.setYear(rs.getInt("VHCL_YEAR"));
			soldVehicle.setPrice(rs.getInt("VHCL_PRICE"));
			soldVehicle.setMilage(rs.getInt("VHCL_MILG"));
			soldVehicle.setDriveTrain(rs.getString("VHCL_DRTN"));
			soldVehicle.setTransmission(rs.getString("VHCL_TRNS"));
			soldVehicle.setVehicleDescription(rs.getString("VHCL_DESC"));
			soldVehicle.setColor(rs.getString("VHCL_COLR"));
			soldVehicle.setType(rs.getString("VHCL_TYPE"));
			soldVehicle.setSoldYear(rs.getInt("VHCL_SLDY"));
			soldVehicle.setSoldMonth(rs.getString("VHCL_SLDM"));
			soldVehicle.setSoldDate(rs.getInt("VHCL_SLDD"));
			return soldVehicle;
		}
	}
}
