package dataManager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.RealEstate;
import model.RealEstateType;

@Startup
@LocalBean
@Singleton
public class RealEstateDataBean {

	private List<RealEstate> realEstateList ;
	
	public RealEstateDataBean() {
		this.realEstateList = new ArrayList<RealEstate>();
		this.realEstateList.add(new RealEstate(RealEstateType.apartment, 1.5, 33.0, "Vojvodjanska 3", "+38165645387", "Garsonjera na Grbavici, pogodna za studenta", 4, 200, "Grbavica"));;
		this.realEstateList.add(new RealEstate(RealEstateType.apartment, 2, 40, "Kolo srpskih sestara 12", "+381649872645", "Dvosoban stan na Grbavici, u mirnom kraju, pogodan za par ili dvoje studenata", 5, 240, "Grbavica"));
		this.realEstateList.add(new RealEstate(RealEstateType.house, 5, 89, "Veternik", "+38765940058", "Porodicna kuca na Veterniku,u blizini glavnih linija autobusa", 1, 220, "Veternik"));
		this.realEstateList.add(new RealEstate(RealEstateType.house, 7, 112, "Telep", "+381648739487", "Porodicna kuca na Telepu", 1, 250, "Telep"));
		this.realEstateList.add(new RealEstate(RealEstateType.garage, 1, 12, "Danila Kisa 9", "+38765166303", "Izdaje se garaza u Danila Kisa, vise informacija telefon", 1, 50, "Grbavica"));
		this.realEstateList.add(new RealEstate(RealEstateType.apartment, 1.5, 29, "Lasla Gala 10", "+381640039847", "Garsonjera na Grbavici, ne izdaje se studentima", 3, 180, "Grbavica"));
		this.realEstateList.add(new RealEstate(RealEstateType.apartment, 4, 76, "Dr Ribara 7", "+38766342805", "Stan na Limanu u blizini fakulteta, pogodan za vise studenata", 8, 450, "Liman"));
		this.realEstateList.add(new RealEstate(RealEstateType.apartment, 2, 34, "Janka Cmelika 12", "+381657463782", "Dvosoban stan u blizini linije 8", 5, 220, "Detelinara"));
		this.realEstateList.add(new RealEstate(RealEstateType.apartment, 3, 52, "Jevrejska 23", "+381648274673", "Trosoban stan u Jevrejskoj", 2, 300, "Centar"));
		this.realEstateList.add(new RealEstate(RealEstateType.apartment, 5, 67, "Ilije Bircanina 3", "+381648763927", "Prostran stan u Ilije Bircanina, zgrada ima lift", 6, 300, "Detelinara"));
		this.realEstateList.add(new RealEstate(RealEstateType.apartment, 3, 44, "Pavla Papa 23", "+381648736271", "Udoban stan u centru grada, zgrada ima lift", 3, 260, "Centar"));
	}

	public List<RealEstate> getRealEstateList() {
		return realEstateList;
	}

	public void setRealEstateList(List<RealEstate> realEstateList) {
		this.realEstateList = realEstateList;
	}
	
	
	
}
