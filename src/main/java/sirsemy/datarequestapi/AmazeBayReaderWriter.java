package sirsemy.datarequestapi;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.*;
import java.util.*;

/** @author Sirsemy
 */
public class AmazeBayReaderWriter {
    private HttpURLConnection httpConn;
    private URL listingUrl;
    private URL locationUrl;
    private Properties prop;
    private FTPClient client = new FTPClient();
    private FileInputStream fis = null;
    private List<Listing> wholeListingTable = new ArrayList<>();
    private List<String> wholeLocationTable = new ArrayList<>();
    private List<Listing> validListingTable = new ArrayList<>();
    private Map<Listing, String> invalidList = new HashMap<>();
    private List<String> countingResult = new ArrayList<>();
    private List<MonthlyQuery> monthlyQuery = new ArrayList<>();
    private String bestListerResult;
    

    public AmazeBayReaderWriter() {
    }
    
    public AmazeBayReaderWriter(String configFile) throws ListingDAOException {
        if (!(new File(configFile).isFile())) {
            throw new ListingDAOException("I can't open the " + configFile + " file.");
        }
        try {
            this.prop = new Properties();
            this.prop.load(new FileReader(configFile));
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the reading of config file");
        }
    }

    public void urlConnection() throws ListingDAOException{
        try {
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.connect();
        } catch (IOException e) {
            throw new ListingDAOException("Error during the API URL connection. Message: "
                    + e.getMessage());
        }
    }
    
    public void listingJsonDownloader() throws ListingDAOException {
        try {
            listingUrl = new URL(prop.getProperty("APItable.listing") + "?key=63304c70");
            httpConn = (HttpURLConnection) listingUrl.openConnection();
        } catch (IOException e) {
            throw new ListingDAOException("Error during the API URL connection. Message: "
                    + e.getMessage());
        }
        urlConnection();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(listingUrl);
            List<String> takeId = root.findValuesAsText("id");
            List<String> takeTitle = root.findValuesAsText("title");
            List<String> takeDescription = root.findValuesAsText("description");
            List<String> takeLocationId = root.findValuesAsText("location_id");
            List<String> takeListingPrice = root.findValuesAsText("listing_price");
            List<String> takeCurrency = root.findValuesAsText("currency");
            List<String> takeQuantity = root.findValuesAsText("quantity");
            List<String> takeListingStatus = root.findValuesAsText("listing_status");
            List<String> takeMarketplace = root.findValuesAsText("marketplace");
            List<String> takeUploadTime = root.findValuesAsText("upload_time");
            List<String> takeOwnerEmailAddress = root.findValuesAsText("owner_email_address");
            for (int i = 0; i < Integer.valueOf(prop
                    .getProperty("APItable.listing.size")); i++) {
                List<String> oneListing = new ArrayList<>();
                oneListing.add(takeId.get(i));
                oneListing.add(takeTitle.get(i));
                oneListing.add(takeDescription.get(i));
                oneListing.add(takeLocationId.get(i));
                oneListing.add(takeListingPrice.get(i));
                oneListing.add(takeCurrency.get(i));
                oneListing.add(takeQuantity.get(i));
                oneListing.add(takeListingStatus.get(i));
                oneListing.add(takeMarketplace.get(i));
                oneListing.add(takeUploadTime.get(i));
                oneListing.add(takeOwnerEmailAddress.get(i));
                Listing wholeListing = new Listing(oneListing.get(0),
                    oneListing.get(1), oneListing.get(2), oneListing.get(3),
                    Double.valueOf(oneListing.get(4)), oneListing.get(5),
                    Integer.valueOf(oneListing.get(6)), Integer.valueOf(oneListing.get(7)),
                    Integer.valueOf(oneListing.get(8)), oneListing.get(9),
                    oneListing.get(10));
                wholeListingTable.add(wholeListing);
                oneListing.removeAll(oneListing);
            }
            httpConn.disconnect();
        } catch (FileNotFoundException | JsonGenerationException ex) {
            throw new ListingDAOException(ex);
        } catch (IOException ex) {
            throw new ListingDAOException(ex);
        }
    }
    
    public void locationJsonDownloader() throws ListingDAOException {
        try {
            locationUrl = new URL(prop.getProperty("APItable.location") + "?key=63304c70");
            httpConn = (HttpURLConnection) locationUrl.openConnection();
        } catch (IOException e) {
            throw new ListingDAOException("Error during the API URL connection. Message: "
                    + e.getMessage());
        }
        urlConnection();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(locationUrl);
            wholeLocationTable = root.findValuesAsText("id");
            httpConn.disconnect();
        } catch (FileNotFoundException ex) {
            throw new ListingDAOException(ex);
        } catch (IOException ex) {
            throw new ListingDAOException(ex);
        }
    }
    
    public void lineReviser() throws ListingDAOException {
        ListingValidator validator = new ListingValidator(wholeListingTable, wholeLocationTable);
        validListingTable = validator.getAssortedList();
        invalidList = validator.getListToLogFile();
    }
    
    public void writeToImportLog() throws ListingDAOException {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter(prop
                .getProperty("Filename.log"), true))) {
            for (Map.Entry<Listing, String> par : invalidList.entrySet()) {
                logWriter.println(par.getKey().getId() + ";" 
                    + (par.getKey().getMarketplace() == 1 ? "EBAY" : "AMAZON")
                    + ";" + par.getValue());
            }
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the writing of 'importLog.csv' file");
        } 
    }
    
    public void uploadToDataBase() throws ListingDAOException {
        try (Connection dataBaseConn = DriverManager.getConnection("jdbc:mysql://" 
                + prop.getProperty("DBserver") + ":" + prop.getProperty("DBport") 
                + "/" + prop.getProperty("DBname") + prop.getProperty("DBtimezone"),
                prop.getProperty("DBusername"), prop.getProperty("DBpassword"));
            PreparedStatement pstmt = dataBaseConn.prepareStatement(
                "INSERT INTO listing (id, title, description,"
                + "location_id, listing_price, currency, quantity,"
                + " listing_status, marketplace, upload_time, owner_email_address)"
                + " VALUES (unhex(replace(?,'-','')), ?, ?,"
                + " unhex(replace(?,'-','')), ?, ?"
                + ", ?, ?, ?, ?, ?)")) {
            for (Listing writeThrough : validListingTable) {
                pstmt.setString(1, writeThrough.getId());
                pstmt.setString(2, writeThrough.getTitle());
                pstmt.setString(3, writeThrough.getDescription());
                pstmt.setString(4, writeThrough.getLocationId());
                pstmt.setDouble(5, writeThrough.getListingPrice());
                pstmt.setString(6, writeThrough.getCurrency());
                pstmt.setInt(7, writeThrough.getQuantity());
                pstmt.setInt(8, writeThrough.getListingStatus());
                pstmt.setInt(9, writeThrough.getMarketplace());
                if (writeThrough.getUploadTime() != null) {
                    pstmt.setDate(10, Date.valueOf(writeThrough.getUploadTime()));
                } else
                    pstmt.setDate(10, null);
                pstmt.setString(11, writeThrough.getOwnerEmailAddress());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ListingDAOException("Error in the database connection."
                    + " Message: " + e.getMessage());
        }
    }
    
    public void queries() throws ListingDAOException {
        try (Connection dataBaseConn = DriverManager.getConnection("jdbc:mysql://" 
                + prop.getProperty("DBserver") + ":" + prop.getProperty("DBport") 
                + "/" + prop.getProperty("DBname") + prop.getProperty("DBtimezone"),
                prop.getProperty("DBusername"), prop.getProperty("DBpassword"));
            Statement stmt = dataBaseConn.createStatement()) {
            ResultSet count = stmt.executeQuery("SELECT COUNT(id) AS 'total_listing_count'," +
                " SUM(IF(marketplace='EBAY', 1, 0)) AS 'eBay_listing_count'," +
                " SUM(IF(marketplace='EBAY', listing_price, 0)) AS 'total_eBay_price'," +
                " AVG(IF(marketplace='EBAY', listing_price, 0)) AS 'avarage_eBay_price'," +
                " SUM(IF(marketplace='AMAZON', 1, 0)) AS 'amazon_listing_count'," +
                " SUM(IF(marketplace='AMAZON', listing_price, 0)) AS 'total_amazon_price'," +
                " AVG(IF(marketplace='AMAZON', listing_price, 0)) AS 'avarage_amazon_price'" +
                " FROM amazebay.listing");
            count.next();
            countingResult.add(count.getString("total_listing_count"));
            countingResult.add(count.getString("eBay_listing_count"));
            countingResult.add(count.getString("total_eBay_price"));
            countingResult.add(count.getString("avarage_eBay_price"));
            countingResult.add(count.getString("amazon_listing_count"));
            countingResult.add(count.getString("total_amazon_price"));
            countingResult.add(count.getString("avarage_amazon_price"));
                
            ResultSet bestLister = stmt.executeQuery("SELECT best_lister"
                    + " FROM (SELECT owner_email_address AS 'best_lister',"
                    + " count(*) as NUM FROM amazebay.listing GROUP BY "
                    + "owner_email_address) AS temp ORDER BY num DESC limit 1");
            bestLister.next();
            bestListerResult = bestLister.getString("best_lister");
            
            ResultSet monthlyReport = stmt.executeQuery("SELECT "
                + "DATE_FORMAT(upload_time, '%Y %M') "
                + "AS 'y_month', SUM(IF(marketplace='EBAY', 1, 0)) AS "
                + "'eBay_listing_count', SUM(IF(marketplace='EBAY', listing_price, 0))"
                + " AS 'total_eBay_price', AVG(IF(marketplace='EBAY', listing_price, NULL))"
                + " AS 'avarage_eBay_price', SUM(IF(marketplace='AMAZON', 1, 0))"
                + " AS 'amazon_listing_count', SUM(IF(marketplace='AMAZON', listing_price, 0))"
                + " AS 'total_amazon_price', AVG(IF(marketplace='AMAZON'"
                + ", listing_price, NULL)) AS 'avarage_amazon_price' FROM"
                + " listing Group by year(upload_time), month(upload_time)"
                + ", values(marketplace) ORDER BY y_month");
            while (monthlyReport.next()) {
                MonthlyQuery lines = new MonthlyQuery(monthlyReport.getString("y_month"),
                    monthlyReport.getString("eBay_listing_count"),
                    monthlyReport.getString("total_eBay_price"),
                    monthlyReport.getString("avarage_eBay_price"),
                    monthlyReport.getString("amazon_listing_count"),
                    monthlyReport.getString("total_amazon_price"),
                    monthlyReport.getString("avarage_amazon_price"));
                monthlyQuery.add(lines);
            }
            ResultSet monthlyBest = stmt.executeQuery("SELECT y_month, best_lister FROM "
                    + "(SELECT DATE_FORMAT(upload_time, '%Y %M') AS y_month"
                    + ", owner_email_address AS best_lister FROM listing GROUP BY y_month"
                    + ", best_lister ORDER BY y_month, COUNT(best_lister) DESC) "
                    + "AS temp GROUP BY y_month");
            for (int i = 0; monthlyBest.next(); i++) {
                monthlyQuery.get(i)
                    .setBest_lister(monthlyBest.getString("best_lister"));
            }
        } catch (SQLException e) {
            throw new ListingDAOException("Error in the database connection."
                    + "Message: " + e.getMessage());
        }
    }
    
    public void jsonWriter() throws ListingDAOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(prop.getProperty("Filename.report"));

            ObjectNode commonReport = mapper.createObjectNode();
            commonReport.put("total_listing_count", countingResult.get(0));
            commonReport.put("eBay_listing_count", countingResult.get(1));
            commonReport.put("total_eBay_price", countingResult.get(2));
            commonReport.put("avarage_eBay_price", countingResult.get(3));
            commonReport.put("amazon_listing_count", countingResult.get(4));
            commonReport.put("total_amazon_price", countingResult.get(5));
            commonReport.put("avarage_amazon_price", countingResult.get(6));

            commonReport.put("best_lister", bestListerResult);

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, commonReport);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(file, true), monthlyQuery);
        } catch (JsonGenerationException | FileNotFoundException ex) {
            throw new ListingDAOException(ex);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the write of " 
                    + prop.getProperty("Filename.report") + " file. Message: "
                    + ex.getMessage());
        }
    }
    
    public void uploadToFtp(String fileToFTP) throws ListingDAOException {
        try {
            client.connect(prop.getProperty("FTPurl"));
            client.login(prop.getProperty("FTPusername"), prop.getProperty("FTPpassword"));
            fis = new FileInputStream(fileToFTP);
            client.storeFile(fileToFTP, fis);
            client.logout();
        } catch (IOException e) {
            throw new ListingDAOException("Error during the FTP connection.");
        }
    }
    
    public void streamCloser() throws ListingDAOException{
        try {
            if (fis != null)
                fis.close();
            client.disconnect();
            System.out.flush();
        } catch (IOException ex) {
            throw new ListingDAOException(ex);
        }
        
    }

    public Map<Listing, String> getInvalidList() {
        return invalidList;
    }
    
    public Properties getProp() {
        return prop;
    }

    public URL getListingUrl() {
        return listingUrl;
    }

    public FTPClient getClient() {
        return client;
    }

    public FileInputStream getFis() {
        return fis;
    }

    public HttpURLConnection getHttpConn() {
        return httpConn;
    }

    public void setHttpConn(HttpURLConnection httpConn) {
        this.httpConn = httpConn;
    }

    public void setListingUrl(URL listingUrl) {
        this.listingUrl = listingUrl;
    }

    public void setLocationUrl(URL locationUrl) {
        this.locationUrl = locationUrl;
    }

    public void setInvalidList(Map<Listing, String> invalidList) {
        this.invalidList = invalidList;
    }
    
}
