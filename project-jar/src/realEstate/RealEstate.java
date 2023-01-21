package realEstate;

import java.io.Serializable;

public class RealEstate implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	
	private RealEstateType type;
	
	private double roomsNumber;
	
	private double size;
	
	private String address;
	
	private String ownerNumber;
	
	private String name;
	
	private int floor;
	
	private int price;
	
	private String location;
	
	private boolean elevator;
	
	public RealEstate() {
		this.id = IdGenerator.getNextId();
	}

	public RealEstate(RealEstateType type, double roomsNumber, double size, String address, String ownerNumber,
			String name, int floor, int price, String location, boolean elevator) {
		super();
		this.id = IdGenerator.getNextId();
		this.type = type;
		this.roomsNumber = roomsNumber;
		this.size = size;
		this.address = address;
		this.ownerNumber = ownerNumber;
		this.name = name;
		this.floor = floor;
		this.price = price;
		this.location = location;
		this.elevator = elevator;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RealEstateType getType() {
		return type;
	}

	public void setType(RealEstateType type) {
		this.type = type;
	}

	public double getRoomsNumber() {
		return roomsNumber;
	}

	public void setRoomsNumber(double roomsNumber) {
		this.roomsNumber = roomsNumber;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOwnerNumber() {
		return ownerNumber;
	}

	public void setOwnerNumber(String ownerNumber) {
		this.ownerNumber = ownerNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isElevator() {
		return elevator;
	}

	public void setElevator(boolean elevator) {
		this.elevator = elevator;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
