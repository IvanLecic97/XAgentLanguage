package realEstate;

public class RealEstateDTO {
	
	private String type;
	
	//double roomsNumber;
	
	double minSize;
	
	double maxSize;
	
	//String address;
	
	//String ownerNumber;
	
	//String name;
	
	//int floor;
	
	int minPrice;
	
	int maxPrice;
	
	String location;
	
	//boolean elevator;
	
	String username;
	
	
	public RealEstateDTO() {
		
	}


	public RealEstateDTO(String type, double roomsNumber, double minSize, double maxSize, String address,
			String ownerNumber, String name, int floor, int minPrice, int maxPrice, String location, boolean elevator) {
		super();
		this.type = type;
		//this.roomsNumber = roomsNumber;
		this.minSize = minSize;
		this.maxSize = maxSize;
		//this.address = address;
		//this.ownerNumber = ownerNumber;
		//this.name = name;
		//this.floor = floor;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.location = location;
		//this.elevator = elevator;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
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


	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
