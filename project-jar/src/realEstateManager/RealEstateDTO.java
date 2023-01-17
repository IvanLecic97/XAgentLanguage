package realEstateManager;

import model.RealEstateType;

public class RealEstateDTO {

	private RealEstateType type;
	
	private double roomsNumber;
	
	private double minSize;
	
	private double maxSize;
	
	private String address;
	
	private String ownerNumber;
	
	private String name;
	
	private int floor;
	
	private int minPrice;
	
	private int maxPrice;
	
	private String location;

	public RealEstateDTO(RealEstateType type, double roomsNumber, double minSize, double maxSize, String address,
			String ownerNumber, String name, int floor, int minPrice, int maxPrice, String location) {
		super();
		this.type = type;
		this.roomsNumber = roomsNumber;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.address = address;
		this.ownerNumber = ownerNumber;
		this.name = name;
		this.floor = floor;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.location = location;
	}
	
	public RealEstateDTO() {
		
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

	public double getMinSize() {
		return minSize;
	}

	public void setMinSize(double minSize) {
		this.minSize = minSize;
	}

	public double getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(double maxSize) {
		this.maxSize = maxSize;
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

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	
}
