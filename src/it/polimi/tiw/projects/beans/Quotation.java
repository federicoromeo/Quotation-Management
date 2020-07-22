package it.polimi.tiw.projects.beans;

public class Quotation {
	
	private int code;
	private int clientCode;
	private int employeeCode;
	private int productCode;
	private float price;
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

	public int getClientCode() {
		return clientCode;
	}

	public void setClientCode(int clientCode) {
		this.clientCode = clientCode;
	}

	public int getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(int employeeCode) {
		this.employeeCode = employeeCode;
	}

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
