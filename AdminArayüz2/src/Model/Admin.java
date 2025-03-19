package Model;

public class Admin {
	
	private String adminName;
	private char adminPassword;
	
	
	public Admin(String adminName, char adminPassword) {
		super();
		this.adminName = adminName;
		this.adminPassword = adminPassword;
	}


	public String getAdminName() {
		return adminName;
	}


	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}


	public char getAdminPassword() {
		return adminPassword;
	}


	public void setAdminPassword(char adminPassword) {
		this.adminPassword = adminPassword;
	}
		
}
