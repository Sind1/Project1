public class ManagerList {
	
	private int id;
	private String name, surname, username, password;
	

	public ManagerList (int pid, String pname, String psurname, String pusername, String ppassword) {
	
		this.id = pid;
		this.name = pname;
		this.surname = psurname;
		this.username= pusername;
		this.password = ppassword;

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
}