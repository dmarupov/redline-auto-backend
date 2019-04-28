package repository;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.text.DecimalFormat;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import objects.Vehicle;

import org.springframework.jdbc.core.RowMapper;


@Repository
public class VehicleSearchRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SEARCH_VHCL_QUERY = " select * from RLA_VHCL where (VHCL_MAKE = ? ";
	private static final String GET_MONTHLY_SPECIAL_QUERY = " select * from RLA_VHCL where VHCL_PICS is not null ";

	public List<Vehicle> getSliderImages(ArrayList<String> vehicleMakeLst, String vehicleModel, int vehicleYear) {

		String vehicleMake = "";

		String query = SEARCH_VHCL_QUERY + "or VHCL_MAKE = 'Jaguar')";
		
		if (vehicleMakeLst.size() > 1) {
			vehicleMake = vehicleMakeLst.get(0);
		}
		if (!vehicleModel.isEmpty()) {
			query = query + " and (VHCL_MODL = ? or VHCL_MODL = 'XE 35t')";
		}
		if (vehicleYear != 0) {
			query = query + " and VHCL_YEAR = ?";
		}
		
		final String make = vehicleMake;

		return jdbcTemplate.query(query, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, make);
				ps.setString(2, vehicleModel);
				ps.setInt(3, vehicleYear);
			}
		}, new VehicleImageRowMapper());
	}

	public List<Vehicle> getMonthlySpecial() {
		return jdbcTemplate.query(GET_MONTHLY_SPECIAL_QUERY, new VehicleImageRowMapper());
	}

	public List<Vehicle> getVehicles(String vehicleMake, String vehicleModel, int vehicleYear, int priceLower,
			int priceHigher) {
		
		String SEARCH_VHCL_QUERY = "select * from RLA_VHCL where VHCL_MAKE = ?";

		if (!vehicleModel.isEmpty()) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and VHCL_MODL = '" + vehicleModel + "'";
		}
		if (vehicleYear != 0) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and VHCL_YEAR = " + vehicleYear;
		}
		if (priceLower > 0 && priceHigher > 0) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and VHCL_PRICE BETWEEN " + priceLower + " and " + priceHigher;
		}

		SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " order by VHCL_MAKE";
		
		return jdbcTemplate.query(SEARCH_VHCL_QUERY, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, vehicleMake);
			}
		}, new VehicleImageRowMapper());
	}

	public List<Vehicle> getVehiclesAdv(String vehicleMake, String vehicleModel, int yearLow, int yearHigh,
			int priceLower, int priceHigher, boolean sedan, boolean hatchback, boolean convertible, boolean coupe,
			boolean suv, boolean pickup) {
		
		String SEARCH_VHCL_QUERY = getQueryForAdvanceSearch(vehicleMake, vehicleModel, yearLow, yearHigh, priceLower,
				priceHigher, sedan, hatchback, convertible, coupe, suv, pickup);
		
		return jdbcTemplate.query(SEARCH_VHCL_QUERY, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				if (!vehicleMake.isEmpty()) {
					ps.setString(1, vehicleMake);
				}
			}
		}, new VehicleImageRowMapper());
	}


	public List<Map<String, Object>> getAllVehicles() {
		return jdbcTemplate.queryForList("SELECT * FROM RLA_VHCL");

	}

	private String getQueryForAdvanceSearch(String vehicleMake, String vehicleModel, int yearLow, int yearHigh,
			int priceLower, int priceHigher, boolean sedan, boolean hatchback, boolean convertible, boolean coupe,
			boolean suv, boolean pickup) {
		
		String SEARCH_VHCL_QUERY = "select * from RLA_VHCL where 1=1";

		if (!vehicleMake.isEmpty()) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + "  and VHCL_MAKE = ?";
		}
		if (!vehicleModel.isEmpty()) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and VHCL_MODL = '" + vehicleModel + "'";
		}
		if (yearLow > 0 && yearHigh > 0) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and VHCL_YEAR BETWEEN " + yearLow + " and " + yearHigh;
		}
		if (priceLower > 0 && priceHigher > 0) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and VHCL_PRICE BETWEEN " + priceLower + " and " + priceHigher;
		}

		ArrayList<String> types = new ArrayList<String>();
		
		types = getVehicleTypesArray(sedan, hatchback, convertible, coupe, suv, pickup, types);
		
		for (int i = 0; i < types.size(); i++) {
			if (i == 0) {
				SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " and (VHCL_TYPE = '" + types.get(i) + "'";
			} else {
				SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " or VHCL_TYPE = '" + types.get(i) + "'";
			}
		}
		
		if (types.size() > 0) {
			SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + ")";
		}

		SEARCH_VHCL_QUERY = SEARCH_VHCL_QUERY + " order by VHCL_MAKE";
		return SEARCH_VHCL_QUERY;
	}
	
	private ArrayList<String> getVehicleTypesArray(boolean sedan, boolean hatchback, boolean convertible, boolean coupe, boolean suv,
			boolean pickup, ArrayList<String> types) {
		if (sedan) {
			types.add("sedan");
		}
		if (hatchback) {
			types.add("hatchback");
		}
		if (convertible) {
			types.add("convertible");
		}
		if (coupe) {
			types.add("coupe");
		}
		if (suv) {
			types.add("suv");
		}
		if (pickup) {
			types.add("pickup");
		}
		return types;
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
}
