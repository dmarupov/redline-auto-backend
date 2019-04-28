package objects;

import java.sql.Blob;

public class Vehicle {
	
	int id;
	String vin;
	Blob imageBlob;
	String imageBlobStr;
	String make;
	String model;
	String year;
	String price;
	String milage;
	String driveTrain;
	String transmission;
	String color;
	String vehicleDescription;
	String type;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Blob getImageBlob() {
		return imageBlob;
	}
	public void setImageBlob(Blob imageBlob) {
		this.imageBlob = imageBlob;
	}
	public String getImageBlobStr() {
		return imageBlobStr;
	}
	public void setImageBlobStr(String imageBlobStr) {
		this.imageBlobStr = imageBlobStr;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMilage() {
		return milage;
	}
	public void setMilage(String milage) {
		this.milage = milage;
	}
	public String getDriveTrain() {
		return driveTrain;
	}
	public void setDriveTrain(String driveTrain) {
		this.driveTrain = driveTrain;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getVehicleDescription() {
		return vehicleDescription;
	}
	public void setVehicleDescription(String vehicleDescription) {
		this.vehicleDescription = vehicleDescription;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
