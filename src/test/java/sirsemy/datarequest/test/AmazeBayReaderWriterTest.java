package sirsemy.datarequest.test;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sirsemy.datarequestapi.AmazeBayReaderWriter;
import sirsemy.datarequestapi.Listing;
import sirsemy.datarequestapi.ListingDAOException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Sirsemy
 */
public class AmazeBayReaderWriterTest {
    private static AmazeBayReaderWriter amazeBay;
    private static PrintWriter logWriter, jsonWriter;
    private static BufferedReader jsonReader;
    private static Properties prop;
    private static Connection dataBaseConn;
    private static FTPClient client = new FTPClient();
    private static FileInputStream fis = null;
    private static final String FIRSTID = "813f23e4-a029-4ea7-b95c-0020ea60aa1c";
    private static final String SECONDID = "0fe479bb-fe39-4265-b59f-bb4ac5a060d4";
    private static final String THIRDID = "37c6000b-d199-4ea0-949b-2faa5773309a";
    private PreparedStatement insertData;

    public AmazeBayReaderWriterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws ListingDAOException {
        try {
            prop = new Properties();
            prop.load(new FileReader("properties.ini"));
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the reading of 'properties.ini' file");
        }
        
        try {
            logWriter = new PrintWriter(new FileWriter(prop.getProperty("Filename.testLog"), true));
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the writing of 'testLog.csv' file");
        } 
        
        try {
            jsonWriter = new PrintWriter(new FileWriter(prop.getProperty("Filename.testReport"), true));
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the writing of 'testReport.json' file");
        }
        try {
            jsonReader = new BufferedReader(new FileReader(prop.getProperty("Filename.testReport")));
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the opening of 'testReport.json' file");
        }
        
        try {
            dataBaseConn = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("DBserver")
                    + ":" + prop.getProperty("DBport") + "/" + prop.getProperty("DBname") +
                    prop.getProperty("DBtimezone"), prop.getProperty("DBusername")
                    , prop.getProperty("DBpassword"));
        } catch (SQLException e) {
            throw new ListingDAOException("Error in the Database connection. Message: " 
                    + e.getMessage());
        }
        
        try {
            client.connect(prop.getProperty("FTPurl"));
            client.login(prop.getProperty("FTPusername"), prop.getProperty("FTPpassword"));
        } catch (IOException e) {
            throw new ListingDAOException("Error during the FTP connection. Message: "
                    + e.getMessage());
        }
    }

    @AfterClass
    public static void tearDownClass() throws ListingDAOException {
        if (logWriter != null)
            logWriter.close();
        if (jsonWriter != null)
            jsonWriter.close();
        if (jsonReader != null) {
            try {
                jsonReader.close();
            } catch (IOException e) {
                throw new ListingDAOException("Error during the close of 'testReport.json' file.");
            }
        }
        if (dataBaseConn != null) {
            try {
                dataBaseConn.close();
            } catch (SQLException e) {
                throw new ListingDAOException("Error in the Database connection. Message: " 
                        + e.getMessage());
            }
        }
        try {
            if (fis != null) {
                fis.close();
            }
            client.logout();
            client.disconnect();
        } catch (IOException e) {
            throw new ListingDAOException("Error during the close of FTP connection. Message: "
                    + e.getMessage());
        }
    }

    @Before
    public void setUp() throws ListingDAOException {
        amazeBay = new AmazeBayReaderWriter();
        try (RandomAccessFile raf = new RandomAccessFile(prop.getProperty("Filename.testReport"), "rw")) {
            raf.setLength(0);
        } catch (Exception e) {
            throw new ListingDAOException("Error during the delete of 'testReport.json' file's content.");
        }
        try (RandomAccessFile raf = new RandomAccessFile(prop.getProperty("Filename.testLog"), "rw")) {
            raf.setLength(0);
        } catch (Exception e) {
            throw new ListingDAOException("Error during the delete of 'testLog.csv' file's content.");
        }
        try (RandomAccessFile raf = new RandomAccessFile(prop.getProperty("Filename.report"), "rw")) {
            raf.setLength(0);
        } catch (Exception e) {
            throw new ListingDAOException("Error during the delete of 'report.json' file's content.");
        }
        try (RandomAccessFile raf = new RandomAccessFile(prop.getProperty("Filename.log"), "rw")) {
            raf.setLength(0);
        } catch (Exception e) {
            throw new ListingDAOException("Error during the delete of 'importLog.csv' file's content.");
        }
        try (Statement stmt = dataBaseConn.createStatement()) {
            stmt.executeUpdate("DELETE FROM amazebay.listing WHERE id ="
                + " unhex(replace('" + FIRSTID + "','-',''))");
            stmt.executeUpdate("DELETE FROM amazebay.listing WHERE id ="
                + " unhex(replace('" + SECONDID + "','-',''))");
            stmt.executeUpdate("DELETE FROM amazebay.listing WHERE id ="
                + " unhex(replace('" + THIRDID + "','-',''))");
        } catch (Exception e) {
            throw new ListingDAOException("Error in the database connection.");
        }
    }
    
    public void uploadToDataBase() throws ListingDAOException {
        try {
            insertData = dataBaseConn.prepareStatement(
                "INSERT INTO listing (id, title, description,"
                + "location_id, listing_price, currency, quantity,"
                + " listing_status, marketplace, upload_time, owner_email_address)"
                + " VALUES (unhex(replace(?,'-','')), ?, ?, unhex(replace(?,'-','')),"
                + " ?, ?, ?, ?, ?, ?, ?)");
            insertData.setString(1, FIRSTID);
            insertData.setString(2, "Slender Wild");
            insertData.setString(3, "Caulanthus major");
            insertData.setString(4, FIRSTID);
            insertData.setDouble(5, 526.45);
            insertData.setString(6, "USD");
            insertData.setInt(7, 24);
            insertData.setInt(8, 2);
            insertData.setInt(9, 1);
            insertData.setDate(10, Date.valueOf("2017-11-05"));
            insertData.setString(11, "wkrebs@comcast.net");
            insertData.executeUpdate();

            insertData.setString(1, SECONDID);
            insertData.setString(2, "Slender Wild");
            insertData.setString(3, "Caulanthus major");
            insertData.setString(4, SECONDID);
            insertData.setDouble(5, 526.45);
            insertData.setString(6, "EUR");
            insertData.setInt(7, 24);
            insertData.setInt(8, 2);
            insertData.setInt(9, 1);
            insertData.setDate(10, Date.valueOf("2017-11-05"));
            insertData.setString(11, "wkrebs@comcast.net");
            insertData.executeUpdate();

            insertData.setString(1, THIRDID);
            insertData.setString(2, "Slender Wild");
            insertData.setString(3, "Caulanthus major");
            insertData.setString(4, THIRDID);
            insertData.setDouble(5, 526.45);
            insertData.setString(6, "HUF");
            insertData.setInt(7, 24);
            insertData.setInt(8, 2);
            insertData.setInt(9, 1);
            insertData.setDate(10, Date.valueOf("2017-11-05"));
            insertData.setString(11, "wkrebs@comcast.net");
            insertData.executeUpdate();
        } catch (SQLException e) {
            throw new ListingDAOException("Error in the database connection.");
        }
    }
    
    @Test
    public void constTest() throws ListingDAOException{
        AmazeBayReaderWriter amazeConstr = new AmazeBayReaderWriter("properties.ini");
        assertEquals("The constructor not working", "1234", amazeConstr.getProp()
                .getProperty("DBpassword"));
    }
    
    @Test(expected = ListingDAOException.class)
    public void constWrongFileTest() throws ListingDAOException{
        AmazeBayReaderWriter amazeConstr = new AmazeBayReaderWriter("set.ini");
    }
    
    @Test
    public void apiUrlTest() throws ListingDAOException {
        AmazeBayReaderWriter amazeTest = new AmazeBayReaderWriter("properties.ini");
        int responsecode;
        try {
            amazeTest.setListingUrl(new URL(prop.getProperty("APItable.listing") 
                    + "?key=63304c70"));
            amazeTest.setHttpConn( (HttpURLConnection) amazeTest.getListingUrl()
                    .openConnection());
            amazeTest.urlConnection();
            responsecode = amazeTest.getHttpConn().getResponseCode();
        } catch (IOException ex) {
            throw new ListingDAOException("No connection with the URL");
        }
        assertEquals("The URL connection is wrong.", 200, responsecode);
    }
    
    @Test
    public void getJsonFromApiTest() throws IOException {
        String proba;
        try (Scanner sc = new Scanner(new URL(prop.getProperty("APItable.location")
                + "?key=63304c70").openStream())) {
            proba = null;
            while(sc.hasNext()) {
                proba += sc.nextLine();
            }
        }
        assertNotNull("Erron during the download of json data.", proba);
    }
    
    @Test
    public void writeIntoLogFileTest() throws ListingDAOException {
        logWriter.println("#ListingId;MarketplaceName;InvalidField");
        logWriter.println("813f23e4-a029-4ea7-b95c-0020ea60aa1c;"
                + "eBay;quantity");
        logWriter.println("813f23e4-a119-4ea7-b95c-0020ea60aa1c;"
                + "Amazon;currency");
        logWriter.flush();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void readWriteLogFileSeparatorTest() throws ListingDAOException {
        logWriter.println("#ListingId;MarketplaceName;InvalidField");
        logWriter.println("813f23e4-a029-4ea7-b95c-0020ea60aa1c;"
                + "eBay;quantity");
        logWriter.flush();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line;
            while ((line = logReader.readLine()) != null){
                if (line.startsWith("#", 0))
                    continue;
                assertEquals("There is no ';' separator.", true, line.contains(";"));
            }
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void writeIntoImportLogTest() throws ListingDAOException {
        AmazeBayReaderWriter amazeTest = new AmazeBayReaderWriter("properties.ini");
        Map<Listing, String> invalidList = new HashMap<>();
        invalidList.put(new Listing("1346243f-34d4-42e2-b728-0ac3e72a59f0",
                "Apetalous Catchfly", "Silene uralensis",
                "1d551b07-fd16-4760-88a3-4aa4fda13a2b", 512.14, "GBP", 45, 1, 1,
                "9/17/2017", "jgwang@aol.com"), "id");
        invalidList.put(new Listing("b0e87ab4-2c54-4544-8ca4-d60a3d500a65",
                "Pseudobryum Moss", "Pseudobryum",
                "5249f33c-fadf-44d9-ab70-471df29c20a6", 589.53, "HUF", 4, 3, 2,
                "11/21/2018", "crobles@yahoo.com"), "title");
        amazeTest.setInvalidList(invalidList);
        amazeTest.writeToImportLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.log")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
//    @Test
//    public void readWriteLogFileColumnsTest() throws ListingDAOException {
//        logWriter.println("#ListingId;MarketplaceName;InvalidField");
//        logWriter.println("813f23e4-a029-4ea7-b95c-0020ea60aa1c;"
//                + "eBay;quantity");
//        logWriter.flush();
//        try {
//            String line;
//            List<String> lineStock = new ArrayList<>();
//            while ((line = logReader.readLine()) != null){
//                if (line.startsWith("#", 0))
//                    continue;
//                StringTokenizer separator = new StringTokenizer(line, ";");
//                while (separator.hasMoreTokens()) {
//                    lineStock.add(separator.nextToken());
//                }
//            }
//            assertEquals("There isn't 3 columns", true, lineStock.size() == 3);
//        } catch (IOException ex) {
//            throw new ListingDAOException("Error during the file opening.");
//        }
//    }
    
    @Test
    public void writeIntoReportFileTest() throws ListingDAOException {
        jsonWriter.println("\"ListingId\" : \"813f23e4-a029-4ea7-b95c-0020ea60aa1c;\"");
        jsonWriter.println("\"MarketplaceName\" : \"eBay\"");
        jsonWriter.flush();
        try {
            String result = jsonReader.readLine();
            assertNotNull("The testReport file is empty.", result);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void writeIntoDataBaseTest() throws ListingDAOException {
        uploadToDataBase();
        try {
            ResultSet rs = insertData.executeQuery("SELECT * FROM amazebay.listing");
            assertNotNull("Wrong database data upload.", rs);
        } catch (SQLException ex) {
            throw new ListingDAOException("Error in the database connection.");
        }
    }
    
//    @Test
//    public void readFromDataBaseTest() throws ListingDAOException {
//        try (Statement stmt = dataBaseConn.createStatement()) {
//            stmt.executeUpdate("INSERT INTO amazebay.listing (id, title, description,"
//                + "location_id, listing_price, currency, quantity,"
//                + " listing_status, marketplace, upload_time, owner_email_address)"
//                + " VALUES (unhex(replace('" + FIRSTID + "','-','')), 'Slender Wild', 'Caulanthus major',"
//                + " unhex(replace('" + FIRSTID + "','-','')), 526.45, 'USD', 24, 2, 1, '2017-11-05',"
//                + " 'wkrebs@comcast.net')");
//            ResultSet rs = stmt.executeQuery("SELECT id FROM amazebay.listing");
//            while (rs.next()) {
//                System.out.println(rs.getString("szemely")+" "+rs.getString("email"));
//            }
//            rs.close();
//            assertNotNull("Wrong database data upload.", stmt.
//                    executeUpdate("SELECT * FROM amazebay.listing"));
//        } catch (SQLException e) {
//            throw new ListingDAOException("Error in the database connection.");
//        }
//    }
    
    @Test
    public void uploadFileToFtpTest() throws ListingDAOException {
        AmazeBayReaderWriter testRW = new AmazeBayReaderWriter("properties.ini");
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(prop.getProperty("Filename.testReport")))) {
            bfw.write("It needs some content.");
            testRW.uploadToFtp(prop.getProperty("Filename.testReport"));
            client.connect(prop.getProperty("FTPurl"));
            client.login(prop.getProperty("FTPusername"), prop.getProperty("FTPpassword"));
            assertThat(client.deleteFile());
//            assertEquals("The file isn't on the FTP.", "213-STAT\n213 End.\n", client
//                    .getStatus(prop.getProperty("FTPurl") + "/testReport.json"));
        } catch (IOException e) {
            throw new ListingDAOException("");
        }

    }
    
    
}