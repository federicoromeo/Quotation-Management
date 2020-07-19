package it.polimi.tiw.projects.beans;

public class Option {

	private String code;
	private String name;
	private float price;
	private boolean normal;
	private boolean onSale;
	//private Image image;
	
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isNormal() {
		return normal;
	}

	public void setNormal(boolean normal) {
		this.normal = normal;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
	
	
}
