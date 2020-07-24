package it.polimi.tiw.projects.beans;

import java.util.*;

public class Product {

	private int code;
	private String name;
	List<Option> optionsList = new ArrayList<>();
	private String image;
	
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
	
	public void setImage(String image) {
		this.image = image;
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

	public String getImage() {
		return image;
	}
	
	
	
}
