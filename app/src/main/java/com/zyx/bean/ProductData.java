package com.zyx.bean;

/**
 * Created by zyx on 2016/2/18.
 */
public class ProductData {

    private String ProductNumber;
    private String ProductName;
    private String PrductDescription;
    private double QuotoPrice;
    private double RetailPrice;
    private String ImageUrls;
    private int QuantityOnHand;
    private int CategoryId;
    private int Mprice;

    public int getMprice() {
        return Mprice;
    }

    public void setMprice(int mprice) {
        Mprice = mprice;
    }

    public String getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(String productNumber) {
        ProductNumber = productNumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrductDescription() {
        return PrductDescription;
    }

    public void setPrductDescription(String prductDescription) {
        PrductDescription = prductDescription;
    }

    public double getQuotoPrice() {
        return QuotoPrice;
    }

    public void setQuotoPrice(double quotoPrice) {
        QuotoPrice = quotoPrice;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        RetailPrice = retailPrice;
    }

    public String getImageUrls() {
        return ImageUrls;
    }

    public void setImageUrls(String imageUrls) {
        ImageUrls = imageUrls;
    }

    public int getQuantityOnHand() {
        return QuantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        QuantityOnHand = quantityOnHand;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }
}
