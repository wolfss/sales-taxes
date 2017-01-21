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
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (imported ? 1 : 0);  
		result = 31 * result + (baseTaxFree ? 1 : 0);  
		result = 31 * result + (description==null ? 0 : description.hashCode());
		return result;
	}
	
	/**
	 * Overridden equals method in order to identify the goods already present into the basket.
	 */
	@Override
	public boolean equals(Object obj) {
		
		if ( this == obj ) {
			return true;
		}
		
		if ( !(obj instanceof Good) ) {
			return false;
		}

		Good good = (Good) obj;
		
		return
				this.isBaseTaxFree() == good.isBaseTaxFree() &&
				this.isImported() == good.isImported() &&
				this.getDescription().equalsIgnoreCase(good.getDescription());
	}
}
