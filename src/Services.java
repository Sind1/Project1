
public class Services {
	
	private int id;
	private String name;
	private float price;
	

	public Services (int pid, String pname, float pprice) {
	
		this.id = pid;
		this.name = pname;
		this.price = pprice;
	}
	public int getID() {
		return id;
	
	}
	public String getName() {
		return name;
	}
	public float getPrice() {
		return price;
	}

}
