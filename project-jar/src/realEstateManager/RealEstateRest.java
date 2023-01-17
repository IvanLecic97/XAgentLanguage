package realEstateManager;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import dataManager.RealEstateDataBean;
import model.RealEstate;

@Stateless
@LocalBean
@Path("/realEstate")
@Remote(RealEstateRestRemote.class)
public class RealEstateRest implements RealEstateRestRemote {
	
	@EJB
	private RealEstateDataBean dataBean;


	@Override
	public List<RealEstate> getRealEstates(String type, double minPrice, double maxPrice, double minSize,
			double maxSize, String location) {
		List<RealEstate> lista = dataBean.getRealEstateList();
		System.out.println(lista);
		if(!type.equals("empty")) {
			lista = lista.stream().filter(e -> e.getType().toString().equals(type)).collect(Collectors.toList());
		} 
		if(minPrice != 0) {
			lista = lista.stream().filter(e -> e.getPrice() >= minPrice).collect(Collectors.toList());
		}
		if(maxPrice != 0) {
			lista = lista.stream().filter(e -> e.getPrice() <= maxPrice).collect(Collectors.toList());
		}
		if(minSize != 0) {
			lista = lista.stream().filter(e -> e.getSize() >= minSize).collect(Collectors.toList());
		}
		if(maxSize != 0) {
			lista = lista.stream().filter(e -> e.getSize() <= maxSize).collect(Collectors.toList());
		}
		if(!location.equals("empty")) {
			lista = lista.stream().filter(e -> e.getLocation().equals(location)).collect(Collectors.toList());
		} 
		return lista;
	}

}
