package it.polimi.tiw.projects.beans;

public class Option {

	private int code;
	private String name;
	private int productCode;
	private boolean onSale;
	//private Image image;
	
	public Option() {}
	
	public Option(int code, String name, int productCode) {
		this.code = code;
		this.name = name;
		this.productCode = productCode;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getProductCode() {
		return productCode;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
	
	
}
