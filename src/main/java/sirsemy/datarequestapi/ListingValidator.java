package sirsemy.datarequestapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/** @author Sirsemy
 */
public class ListingValidator {
    private Map<Listing, String> listToLogFile = new HashMap<>();
    private List<Listing> assortedList = new ArrayList<>();
    private List<String> wholeLocationTable = new ArrayList<>();
    
    public ListingValidator(List<Listing> wholeListingTable,
            List<String> wholeLocationTable) throws ListingDAOException {
        this.wholeLocationTable = wholeLocationTable;
        try {
            for (Listing listing : wholeListingTable) {
                if (listing.getId() == null ||
                        !Pattern.compile("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}")
                        .matcher(listing.getId()).find() || listing.getId().equals("null")) {
                    listToLogFile.put(listing, "id");
                } else if (listing.getTitle() == null
                        || listing.getTitle().equals("null")) {
                    listToLogFile.put(listing, "title");
                } else if (listing.getDescription() == null
                        || listing.getDescription().equals("null")) {
                    listToLogFile.put(listing, "description");
                } else if (listing.getLocationId() == null ||
                        !Pattern.compile("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}")
                        .matcher(listing.getLocationId()).find() ||
                        locationIdChecker(listing.getLocationId())
                        || listing.getLocationId().equals("null")) {
                    listToLogFile.put(listing, "location_id");
                } else if (listing.getListingPrice() == null || listing.getListingPrice() < 0
                        || !Pattern.compile("\\d+.\\d{2}").matcher(listing
                                .getListingPrice().toString()).matches()) {
                    listToLogFile.put(listing, "listing_price");
                } else if (listing.getCurrency() == null || listing.getCurrency()
                        .length() != 3) {
                    listToLogFile.put(listing, "currency");
                } else if (listing.getQuantity() == null || listing.getQuantity() < 0) {
                    listToLogFile.put(listing, "quantity");
                } else if (listing.getListingStatus() == null || listing.getListingStatus() < 1
                        || listing.getListingStatus() > 3) {
                    listToLogFile.put(listing, "listing_status");
                } else if (listing.getOwnerEmailAddress() == null ||
                        !Pattern.matches("[a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]+@"
                                + "[a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]+.[a-zA-Z]+",
                                listing.getOwnerEmailAddress())
                        || listing.getOwnerEmailAddress().equals("null")) {
                    listToLogFile.put(listing, "owner_email_address");
                } else if (listing.getMarketplace() == null || listing.getMarketplace() < 1
                        || listing.getMarketplace() > 2) {
                    listToLogFile.put(listing, "marketplace");
                } else
                    assortedList.add(listing);
            }
        } catch (NullPointerException ex) {
            throw new ListingDAOException(ex);
        }
        
        
    }

    public boolean locationIdChecker(String locationId){
        boolean matches = false;
        for (String actualId : wholeLocationTable) {
            matches = !locationId.equals(actualId);
        }
        return matches;
    }
    
    public Map<Listing, String> getListToLogFile() {
        return listToLogFile;
    }

    public List<Listing> getAssortedList() {
        return assortedList;
    }

    public List<String> getWholeLocationTable() {
        return wholeLocationTable;
    }

    public void setWholeLocationTable(List<String> wholeLocationTable) {
        this.wholeLocationTable = wholeLocationTable;
    }
    
}
