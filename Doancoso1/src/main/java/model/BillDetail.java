package model;

public class BillDetail {
    private int billDetailID;
    private int invoiceID;
    private int cosmeticID;
    private int quantity;
    private float price;
	public BillDetail(int billDetailID, int invoiceID, int cosmeticID, int quantity, float price) {
		super();
		this.billDetailID = billDetailID;
		this.invoiceID = invoiceID;
		this.cosmeticID = cosmeticID;
		this.quantity = quantity;
		this.price = price;
	}
	public BillDetail( int cosmeticID, int quantity, float price) {
		super();
		this.cosmeticID = cosmeticID;
		this.quantity = quantity;
		this.price = price;
	}
	public BillDetail() {
		super();
	}
	public int getBillDetailID() {
		return billDetailID;
	}
	public void setBillDetailID(int billDetailID) {
		this.billDetailID = billDetailID;
	}
	public int getInvoiceID() {
		return invoiceID;
	}
	public void setInvoiceID(int invoiceID) {
		this.invoiceID = invoiceID;
	}
	public int getCosmeticID() {
		return cosmeticID;
	}
	public void setCosmeticID(int cosmeticID) {
		this.cosmeticID = cosmeticID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	

    
}
