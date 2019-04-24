package sirsemy.datarequestapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/** @author Sirsemy
 */
public class Listing {
    private String id, title, description, locationId;
    private Double listingPrice;
    private String currency;
    private Integer quantity, listingStatus, marketplace;

    @JsonProperty("uploadTime")
    @JsonDeserialize(converter = dateConverter())
    private LocalDate uploadTime;
    private String ownerEmailAddress;

    public Listing(){
    }
    
    public Listing(String id, String title, String description, String location_id
            , Double listingPrice, String currency, Integer quantity
            , Integer listingStatus, Integer marketplace, String uploadTime
            , String ownerEmailAddress) throws ListingDAOException {
        this.id = id;
        this.title = title;
        this.description = description;
        this.locationId = location_id;
        this.listingPrice = listingPrice;
        this.currency = currency;
        this.quantity = quantity;
        this.listingStatus = listingStatus;
        this.marketplace = marketplace;
        this.uploadTime = dateConverter(uploadTime);
        this.ownerEmailAddress = ownerEmailAddress;
    }
            
    public LocalDate dateConverter(String datum) throws ListingDAOException {
        if (datum != null && !datum.equals("null")) {
            List<String> dateForms = new ArrayList<>();
                dateForms.add("M/d/yyyy");
                dateForms.add("MM/d/yyyy");
                dateForms.add("M/dd/yyyy");
                dateForms.add("MM/dd/yyyy");
            String goodPattern = null;
            for (String dateForm : dateForms) {
                if (dateForm.length() == datum.length() 
                        && dateForm.indexOf("/") == datum.indexOf("/"))
                    goodPattern = dateForm;
            }
            LocalDate convertedDate = null;
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(goodPattern);
                convertedDate = LocalDate.parse(datum, dtf);
            } catch (DateTimeParseException ex) {
                throw new ListingDAOException("Not proper form of the date field.");
            } catch (NullPointerException ex) {
                throw ex;
            }
            return convertedDate;
        } else
            return null;
    }     

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Double getListingPrice() {
        return listingPrice;
    }

    public void setListingPrice(Double listingPrice) {
        this.listingPrice = listingPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(Integer listingStatus) {
        this.listingStatus = listingStatus;
    }

    public Integer getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Integer marketplace) {
        this.marketplace = marketplace;
    }

    public LocalDate getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) throws ListingDAOException {
        this.uploadTime = dateConverter(uploadTime);
    }

    public String getOwnerEmailAddress() {
        return ownerEmailAddress;
    }

    public void setOwnerEmailAddress(String ownerEmailAddress) {
        this.ownerEmailAddress = ownerEmailAddress;
    }
    
    

}
