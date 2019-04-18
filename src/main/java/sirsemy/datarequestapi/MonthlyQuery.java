package sirsemy.datarequestapi;

import java.io.Serializable;

/** @author Sirsemy
 */
public class MonthlyQuery implements Serializable{
    private String y_month;
    private String eBay_listing_count;
    private String total_eBay_price;
    private String avarage_eBay_price;
    private String amazon_listing_count;
    private String total_amazon_price;
    private String avarage_amazon_price;
    private String best_lister;

    public MonthlyQuery() {
    }

    public MonthlyQuery(String y_month, String eBay_listing_count,
            String total_eBay_price, String avarage_eBay_price,
            String amazon_listing_count, String total_amazon_price,
            String avarage_amazon_price) {
        this.y_month = y_month;
        this.eBay_listing_count = eBay_listing_count;
        this.total_eBay_price = total_eBay_price;
        this.avarage_eBay_price = avarage_eBay_price;
        this.amazon_listing_count = amazon_listing_count;
        this.total_amazon_price = total_amazon_price;
        this.avarage_amazon_price = avarage_amazon_price;
    }

    public void setY_month(String y_month) {
        this.y_month = y_month;
    }

    public void seteBay_listing_count(String eBay_listing_count) {
        this.eBay_listing_count = eBay_listing_count;
    }

    public void setTotal_eBay_price(String total_eBay_price) {
        this.total_eBay_price = total_eBay_price;
    }

    public void setAvarage_eBay_price(String avarage_eBay_price) {
        this.avarage_eBay_price = avarage_eBay_price;
    }

    public void setAmazon_listing_count(String amazon_listing_count) {
        this.amazon_listing_count = amazon_listing_count;
    }

    public void setTotal_amazon_price(String total_amazon_price) {
        this.total_amazon_price = total_amazon_price;
    }

    public void setAvarage_amazon_price(String avarage_amazon_price) {
        this.avarage_amazon_price = avarage_amazon_price;
    }

    public void setBest_lister(String best_lister) {
        this.best_lister = best_lister;
    }

    public String getY_month() {
        return y_month;
    }

    public String geteBay_listing_count() {
        return eBay_listing_count;
    }

    public String getTotal_eBay_price() {
        return total_eBay_price;
    }

    public String getAvarage_eBay_price() {
        return avarage_eBay_price;
    }

    public String getAmazon_listing_count() {
        return amazon_listing_count;
    }

    public String getTotal_amazon_price() {
        return total_amazon_price;
    }

    public String getAvarage_amazon_price() {
        return avarage_amazon_price;
    }

    public String getBest_lister() {
        return best_lister;
    }
    
    
}
