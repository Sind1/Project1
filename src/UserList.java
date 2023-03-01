
public class UserList {
	
	private int id, comID;
	private String name, surname, username, password;
	

	public UserList (int pid, String pname, String psurname, String pusername, String ppassword, int pcomID) {
	
		this.id = pid;
		this.name = pname;
		this.surname = psurname;
		this.username= pusername;
		this.password = ppassword;
		this.comID = pcomID;
	}
	public int getID() {
		return id;
	
	}
	public String getName() {
		return name;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
		
	}public String getSurname() {
		return surname;
	}
	public int getcomID() {
		return comID;
	}


}
