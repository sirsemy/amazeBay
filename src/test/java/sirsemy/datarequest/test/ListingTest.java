package sirsemy.datarequest.test;

import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sirsemy.datarequestapi.Listing;
import sirsemy.datarequestapi.ListingDAOException;

/**
 *
 * @author Sirsemy
 */
public class ListingTest {
    private Listing listing;
    private static final double DELTA = 1e-15;
    
    public ListingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        listing = new Listing();
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void construktorTest() throws ListingDAOException {
        Listing consTest = new Listing("20f6b49c-80a6-475b-9c35-23c2765d5f35",
                "Trumpet Evening", "Oenothera jamesii",
                "bfcd9dc2-3ead-470b-9846-5a3f6d8f32a1", 561.71, "EUR", 41, 3, 2,
                "12/5/2017", "policiesATmac.com");
        assertEquals("Not appropriate id getter", "20f6b49c-80a6-475b-9c35-23c2765d5f35"
                , consTest.getId());
        assertEquals("Not appropriate title getter", "Trumpet Evening",
                consTest.getTitle());
        assertEquals("Not appropriate description getter", "Oenothera jamesii",
                consTest.getDescription());
        assertEquals("Not appropriate locationId getter", "bfcd9dc2-3ead-470b-9846-5a3f6d8f32a1"
                , consTest.getLocationId());
        assertEquals("Not appropriate listingPrice getter", 561.71, 
                consTest.getListingPrice(), DELTA);
        assertEquals("Not appropriate currency getter", "EUR", consTest.getCurrency());
        assertEquals("Not appropriate quantity getter", (Integer)41, consTest.getQuantity());
        assertEquals("Not appropriate listingStatus getter", (Integer)3
                , consTest.getListingStatus());
        assertEquals("Not appropriate marketplace getter", (Integer)2, consTest.getMarketplace());
        assertEquals("Not appropriate uploadTime getter", LocalDate.of(2017, 12, 5)
                , consTest.getUploadTime());
        assertEquals("Not appropriate emailAddress getter", "policiesATmac.com"
                , consTest.getOwnerEmailAddress());
    }
    
    @Test
    public void setterTest() throws ListingDAOException {
        listing.setId("20f6b49c-80a6-475b-9c35-23c2765d5f35");
        listing.setTitle("Trumpet Evening");
        listing.setDescription("Oenothera jamesii");
        listing.setLocationId("bfcd9dc2-3ead-470b-9846-5a3f6d8f32a1");
        listing.setListingPrice(561.71);
        listing.setCurrency("EUR");
        listing.setQuantity(41);
        listing.setListingStatus(3);
        listing.setMarketplace(2);
        listing.setUploadTime("8/26/2018");
        listing.setOwnerEmailAddress("policiesATmac.com");
        assertEquals("Not appropriate id setter", "20f6b49c-80a6-475b-9c35-23c2765d5f35"
                , listing.getId());
        assertEquals("Not appropriate title setter", "Trumpet Evening",
                listing.getTitle());
        assertEquals("Not appropriate description setter", "Oenothera jamesii",
                listing.getDescription());
        assertEquals("Not appropriate locationId setter", "bfcd9dc2-3ead-470b-9846-5a3f6d8f32a1"
                , listing.getLocationId());
        assertEquals("Not appropriate listingPrice setter", 561.71, 
                listing.getListingPrice(), DELTA);
        assertEquals("Not appropriate currency setter", "EUR", listing.getCurrency());
        assertEquals("Not appropriate quantity setter", (Integer)41, listing.getQuantity());
        assertEquals("Not appropriate listingStatus getter", (Integer)3
                , listing.getListingStatus());
        assertEquals("Not appropriate marketplace setter", (Integer)2, listing.getMarketplace());
        assertEquals("Not appropriate uploadTime setter", LocalDate.of(2018, 8, 26)
                , listing.getUploadTime());
        assertEquals("Not appropriate emailAddress setter", "policiesATmac.com"
                , listing.getOwnerEmailAddress());
    }

    @Test
    public void nullCheckerTest() throws ListingDAOException {
        Listing nullTest = new Listing(null, null, null, null, null, null, null,
                null, null, null, null);
        assertNull("Az id nem kezeli a null-t", nullTest.getId());
        assertNull("A title nem kezeli a null-t", nullTest.getTitle());
        assertNull("A description nem kezeli a null-t", nullTest.getDescription());
        assertNull("A locationId nem kezeli a null-t", nullTest.getLocationId());
        assertNull("A listingPrice nem kezeli a null-t", nullTest.getListingPrice());
        assertNull("A currency nem kezeli a null-t", nullTest.getCurrency());
        assertNull("A quantity nem kezeli a null-t", nullTest.getQuantity());
        assertNull("A listingStatus nem kezeli a null-t", nullTest.getListingStatus());
        assertNull("A marketplace nem kezeli a null-t", nullTest.getMarketplace());
        assertNull("Az uploadTime nem kezeli a null-t", nullTest.getUploadTime());
        assertNull("Az emailAddress nem kezeli a null-t", nullTest.getOwnerEmailAddress());
    }
    
    @Test
    public void dateConverterGoodTest() throws ListingDAOException{
        Listing testClass = new Listing();
        assertEquals("It should be good date format.", LocalDate.of(2015, 12, 11), testClass
                .dateConverter("12/11/2015"));
    }
    
    @Test(expected = NullPointerException.class)
    public void dateConverterWrongTest() throws ListingDAOException{
        Listing testClass = new Listing();
        testClass.dateConverter("12-11-2015");
    }
}