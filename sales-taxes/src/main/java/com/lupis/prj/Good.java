package com.lupis.prj;

import java.math.BigDecimal;

public class Good {

	private boolean baseTaxFree;
	private boolean imported;
	private BigDecimal price;
	private String description;
	private int quantity;
	
	public boolean isBaseTaxFree() {
		return baseTaxFree;
	}

	public void setBaseTaxFree(boolean baseTaxFree) {
		this.baseTaxFree = baseTaxFree;
	}

	public boolean isImported() {
		return imported;
	}

	public void setImported(boolean imported) {
		this.imported = imported;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Good(String description, BigDecimal price, int quantity, boolean imported, boolean baseTaxFree) {
		super();
		this.description = description;
		this.setQuantity(quantity);
		this.imported = imported;
		this.baseTaxFree = baseTaxFree;
		this.price = price;
	}

}
