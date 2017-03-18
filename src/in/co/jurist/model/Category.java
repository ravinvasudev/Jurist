package in.co.jurist.model;

import java.io.Serializable;

public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String name;

	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}

}