package it.polimi.tiw.projects.beans;

import java.util.*;

import it.polimi.tiw.projects.dao.ProductDAO;

public class Product {

	private int code;
	private String name;
	List<Option> optionsList = new ArrayList<>();
	//private Image image;
	
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
	
	public void addOption(Option o) {
		optionsList.add(o);
	}

	public List<Option> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(List<Option> optionsList) {
		this.optionsList = optionsList;
	}
	
	
	
}
