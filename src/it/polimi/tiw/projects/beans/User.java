package it.polimi.tiw.projects.beans;

public class User {
	private int code;
	private String role;
	private String name;

	public int getCode() {
		return code;
	}

	public String getRole() {
		return role;
	}

	public String getName() {
		return name;
	}
	
	public void setCode(int i) {
		code = i;
	}

	public void setRole(String r) {
		role = r;
	}

	public void setName(String n) {
		name = n;
	}

}
