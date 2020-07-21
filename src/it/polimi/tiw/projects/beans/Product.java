package it.polimi.tiw.projects.beans;

import it.polimi.tiw.projects.dao.ProductDAO;

public class Product {

	private String code;
	private String name;
	//private Image image;
	
	
	
	
	public Product(String name) {
		this.name = name;
		ProductDAO pd = new ProductDAO(name);
		String code = pd.insertProductInDB();
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
	
}
