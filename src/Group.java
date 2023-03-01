
public class Group {
	private int id, sid, gid;
	private String name, Sname;
	private Float price;
	

	public Group (int pid, int ssid, String cname, String sname, int sgid, Float sprice) {
	
		this.id = pid;
		this.sid = ssid;
		this.name = cname;
		this.Sname = sname;
		this.gid = sgid;
		this.price= sprice;

	}
	public int getID() {
		return id;
	}
	public int getSid() {
		return sid;
	}
	public String getName() {
		return name;
	}
	public String getSname() {
		return Sname;
	}
	public int getGid() {
		return gid;
	}
	public Float getPrice() {
		return price;
		
	}
}
