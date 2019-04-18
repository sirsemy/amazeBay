package sirsemy.datarequest.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import sirsemy.datarequestapi.Listing;
import sirsemy.datarequestapi.ListingDAOException;
import sirsemy.datarequestapi.ListingValidator;

/**
 *
 * @author Sirsemy
 */
public class ListingValidatorTest {
    private List<Listing> testList = new ArrayList<>();
    private List<String> testLocation = new ArrayList<>();
    private Map<Listing, String> invalidList = new HashMap<>();
    private static Properties prop;
    private final String title = "Trumpet Evening";
    private final String description = "Oenothera jamesii";
    private final Double listingPrice = 561.71;
    private final String currency = "EUR", uploadTime = "12/5/2017";
    private final String emailAddress = "policies@mac.com";
    private static final String FIRSTID = "813f23e4-a029-4ea7-b95c-0020ea60aa1c";
    private static final String SECONDID = "0fe479bb-fe39-4265-b59f-bb4ac5a060d4";
    private static final String THIRDID = "37c6000b-d199-4ea0-949b-2faa5773309a";

    public ListingValidatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws ListingDAOException {
        try {
            prop = new Properties();
            prop.load(new FileReader("properties.ini"));
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the reading of 'properties.ini' file");
        }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws ListingDAOException {
//        testList.add(new Listing(FIRSTID,  title, description, SECONDID,
//                listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
//        testList.add(new Listing("1346243f-34d4-42e2-b728-0ac3e72a59f0",
//                "Apetalous Catchfly", "Silene uralensis",
//                "1d551b07-fd16-4760-88a3-4aa4fda13a2b", 512.14, "GBP", 45, 1, 1,
//                "9/17/2017", "jgwang@aol.com"));
//        testList.add(new Listing("b0e87ab4-2c54-4544-8ca4-d60a3d500a65",
//                "Pseudobryum Moss", "Pseudobryum",
//                "5249f33c-fadf-44d9-ab70-471df29c20a6", 589.53, "HUF", 4, 3, 2,
//                "11/21/2018", "crobles@yahoo.com"));
    }

    @After
    public void tearDown() throws ListingDAOException {
        testList.removeAll(testList);
        testLocation.removeAll(testLocation);
        System.out.flush();
//        try (PrintWriter logWriter = new PrintWriter(new FileWriter(prop
//                .getProperty("Filename.testLog")))) {
//            logWriter.print("");
//        } catch (IOException ex) {
//            throw new ListingDAOException("Error during the writing of " + 
//                    prop.getProperty("Filename.testLog") + " file");
//        }
    }

    public void writeToTestLog() throws ListingDAOException {
        try (PrintWriter logWriter = new PrintWriter(new FileWriter(prop
                .getProperty("Filename.testLog")))) {
            for (Map.Entry<Listing, String> par : invalidList.entrySet()) {
                logWriter.println(par.getKey().getId() + ";" 
                    + (par.getKey().getMarketplace() == 1 ? "EBAY" : "AMAZON")
                    + ";" + par.getValue());
            }
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the writing of 'testLog.csv' file");
        } 
    }
    
    @Test
    public void constTest() throws ListingDAOException {
        testLocation.add(THIRDID);
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        assertNotNull("The 'wholeLocationTable' variable is null", listVal.getWholeLocationTable());
    }
    
    @Test
    public void setterTest() throws ListingDAOException {
        testLocation.add(THIRDID);
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        listVal.setWholeLocationTable(testLocation);
        assertNotNull("The 'wholeLocationTable' variable is null", listVal.getWholeLocationTable());
    }
    
    @Test
    public void locationIdCheckerMatchTest() throws ListingDAOException {
        testLocation.add(SECONDID);
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        assertEquals("The locationChecker not match", false, listVal.locationIdChecker(SECONDID));
    }
    
    @Test
    public void locationIdCheckerNotMatchTest() throws ListingDAOException {
        testLocation.add(THIRDID);
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        assertEquals("The locationChecker match", true, listVal.locationIdChecker(SECONDID));
    }
    
    @Test
    public void validAllTest() throws ListingDAOException {
        testLocation.add(SECONDID);
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidIdUuidTest() throws ListingDAOException {
        testList.add(new Listing("15",  title, description,
                SECONDID, listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidIdNullTest() throws ListingDAOException {
        testList.add(new Listing(null,  title, description,
                SECONDID, listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidTitleTest() throws ListingDAOException, InterruptedException {
        testList.add(new Listing(FIRSTID, "null", description,
                SECONDID, listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
//        ExecutorService es = Executors.newCachedThreadPool();
//        es.execute(() -> {
//            try {
//                writeToTestLog();
//            } catch (ListingDAOException ex) {
//                Logger.getLogger(ListingValidatorTest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//        es.shutdown();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
//            es.awaitTermination(10, TimeUnit.SECONDS);
//            latch.countDown();
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
//        } catch (InterruptedException ex) {
//            throw new ListingDAOException("The current thread have been interrupted.");
        }
    }
    
    @Test
    public void invalidDescriptionTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, null,
                SECONDID, listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidLocationIdNullTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                null, listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidLocationIdWrongPatternTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description, "12kju-154djfh",
                listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidLocationIdReferenceTest() throws ListingDAOException {
        testLocation.add(THIRDID);
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            assertNotNull("The log file is empty.", logReader.readLine());
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidListingPriceOneDecimalTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, 561.7, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidListingPriceThreeDecimalTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, 561.7254, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidListingPriceNullTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, null, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidListingPriceMinusTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, -15.12, currency, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidCurrencyNullTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, null, 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidCurrencyTwoCharsTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, "FG", 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidCurrencyFourCharsTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, "FGFG", 41, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidQuantityNullTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description, SECONDID,
                listingPrice, currency, null, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidQuantityMinusTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description, SECONDID,
                listingPrice, currency, -12, 3, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidListingStatusNullTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description, SECONDID,
                listingPrice, currency, 12, null, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidListingStatusReference1Test() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, currency, 12, 0, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidListingStatusReference2Test() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, currency, 12, 4, 2, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
//    @Test
//    public void invalidMarketplaceNullTest() throws ListingDAOException {
//        testList.add(new Listing(FIRSTID, title, description, SECONDID,
//                listingPrice, currency, 12, 2, null, uploadTime, emailAddress));
//        ListingValidator listVal = new ListingValidator(testList, testLocation);
//        invalidList = listVal.getListToLogFile();
//        writeToTestLog();
//        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
//                .getProperty("Filename.testLog")))) {
//            String line = logReader.readLine();
//            assertNotNull("The log file is empty.", line);
//        } catch (IOException ex) {
//            throw new ListingDAOException("Error during the file opening.");
//        }
//    }
    
    @Test
    public void invalidMarketplaceReference1Test() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, currency, 12, 2, 0, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidMarketplaceReference2Test() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, currency, 12, 2, 3, uploadTime, emailAddress));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidEmailAddressNullTest() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description,
                SECONDID, listingPrice, currency, 12, 2, 3, uploadTime, null));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidEmailAddressFormat1Test() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description, SECONDID,
                listingPrice, currency, 12, 2, 3, uploadTime, "policiesATmac.com"));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidEmailAddressFormat2Test() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description, SECONDID,
                listingPrice, currency, 12, 2, 3, uploadTime, "policies@maccom"));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    @Test
    public void invalidEmailAddressFormat3Test() throws ListingDAOException {
        testList.add(new Listing(FIRSTID, title, description, SECONDID,
                listingPrice, currency, 12, 2, 3, uploadTime, "policiesmaccom"));
        ListingValidator listVal = new ListingValidator(testList, testLocation);
        invalidList = listVal.getListToLogFile();
        writeToTestLog();
        try (BufferedReader logReader = new BufferedReader(new FileReader(prop
                .getProperty("Filename.testLog")))) {
            String line = logReader.readLine();
            assertNotNull("The log file is empty.", line);
        } catch (IOException ex) {
            throw new ListingDAOException("Error during the file opening.");
        }
    }
    
    
}