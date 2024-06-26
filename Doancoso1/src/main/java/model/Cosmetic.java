package model;

import java.util.Date;

public class Cosmetic {
    private int cosmeticID;
    private String cosmeticName;
    private int categoryID;
    private String description;
    private Date manufactureDate;
    private Date expiryDate;
    private float price;
    private int quantity;
    private String image;
	public Cosmetic(String cosmeticName, int categoryID, String description, Date manufactureDate, Date expiryDate,
			float price, int quantity, String image) {
		super();
		this.cosmeticName = cosmeticName;
		this.categoryID = categoryID;
		this.description = description;
		this.manufactureDate = manufactureDate;
		this.expiryDate = expiryDate;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}
	public Cosmetic(int cosmeticID, String cosmeticName, int categoryID, String description, Date manufactureDate,
			Date expiryDate, float price, int quantity, String image) {
		super();
		this.cosmeticID = cosmeticID;
		this.cosmeticName = cosmeticName;
		this.categoryID = categoryID;
		this.description = description;
		this.manufactureDate = manufactureDate;
		this.expiryDate = expiryDate;
		this.price = price;
		this.quantity = quantity;
		this.image = image;
	}
	public Cosmetic() {
		super();
	}
	public int getCosmeticID() {
		return cosmeticID;
	}
	public void setCosmeticID(int cosmeticID) {
		this.cosmeticID = cosmeticID;
	}
	public String getCosmeticName() {
		return cosmeticName;
	}
	public void setCosmeticName(String cosmeticName) {
		this.cosmeticName = cosmeticName;
	}
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
    
    
}