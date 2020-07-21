package it.polimi.tiw.projects.beans;

public class Option {

	private String code;
	private String name;
	private String productCode;
	private boolean onSale;
	//private Image image;
	
	public Option() {}
	
	public Option(String code, String name, String productCode) {
		this.code = code;
		this.name = name;
		this.productCode = productCode;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
	
	
}
