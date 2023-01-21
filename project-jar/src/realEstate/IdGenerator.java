package realEstate;

public class IdGenerator {

	private static long id = 0;
	
	public void idGenerator() {
		
	}
	
	static public long getNextId() {
		return ++id;
	}
	
}
