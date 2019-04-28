package objects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import objects.SoldVehicle;
import objects.Vehicle;



public class VehicleObjectTest {
	
	@Test
    public void vehicleObjectDataTest() throws Exception {
		
    	Vehicle vehicle = new Vehicle();
    	vehicle.setMake("Ford");
    	vehicle.setModel("Focus");
    	
    	assertEquals("Ford", vehicle.getMake());
    	System.out.println("This test is working: " + vehicle.getMake());
    	
    }
	
	@Test
    public void soldVehicleObjectDataTest() throws Exception {
		
    	SoldVehicle vehicle = new SoldVehicle();
    	vehicle.setMake("Ford");
    	vehicle.setModel("Focus");
    	
    	assertEquals("Ford", vehicle.getMake());
    	System.out.println("This test is working: " + vehicle.getMake());
    	
    }
}
