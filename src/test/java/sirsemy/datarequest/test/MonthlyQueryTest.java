package sirsemy.datarequest.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sirsemy.datarequestapi.MonthlyQuery;

/**
 *
 * @author Sirsemy
 */
public class MonthlyQueryTest {
    private MonthlyQuery monthlyQ;

    public MonthlyQueryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        monthlyQ = new MonthlyQuery();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void consTeszt(){
        MonthlyQuery consTest = new MonthlyQuery("2017 january", "20",
            "2547.54284", "251.45879", "42", "128412.54897541",
            "181.457896");
        assertEquals("Not appropriate month getter", "2017 january"
                , consTest.getY_month());
        assertEquals("Not appropriate eBay_listing_count getter", "20",
                consTest.geteBay_listing_count());
        assertEquals("Not appropriate total_eBay_price getter", "2547.54284",
                consTest.getTotal_eBay_price());
        assertEquals("Not appropriate avarage_eBay_price getter", "251.45879"
                , consTest.getAvarage_eBay_price());
        assertEquals("Not appropriate mazon_listing_count getter", "42",
                consTest.getAmazon_listing_count());
        assertEquals("Not appropriate total_amazon_price getter", "128412.54897541", 
                consTest.getTotal_amazon_price());
        assertEquals("Not appropriate avarage_amazon_price getter", "181.457896",
                consTest.getAvarage_amazon_price());
    }
    
    @Test
    public void setterTest() {
        monthlyQ.setY_month("2017 january");
        monthlyQ.seteBay_listing_count("20");
        monthlyQ.setTotal_eBay_price("2547.54284");
        monthlyQ.setAvarage_eBay_price("251.45879");
        monthlyQ.setAmazon_listing_count("42");
        monthlyQ.setTotal_amazon_price("128412.54897541");
        monthlyQ.setAvarage_amazon_price("181.457896");
        monthlyQ.setBest_lister("mhanoh@verizon.net");
        assertEquals("Not appropriate month getter", "2017 january"
                , monthlyQ.getY_month());
        assertEquals("Not appropriate eBay_listing_count getter", "20",
                monthlyQ.geteBay_listing_count());
        assertEquals("Not appropriate total_eBay_price getter", "2547.54284",
                monthlyQ.getTotal_eBay_price());
        assertEquals("Not appropriate avarage_eBay_price getter", "251.45879"
                , monthlyQ.getAvarage_eBay_price());
        assertEquals("Not appropriate mazon_listing_count getter", "42",
                monthlyQ.getAmazon_listing_count());
        assertEquals("Not appropriate total_amazon_price getter", "128412.54897541", 
                monthlyQ.getTotal_amazon_price());
        assertEquals("Not appropriate avarage_amazon_price getter", "181.457896",
                monthlyQ.getAvarage_amazon_price());
        assertEquals("Not appropriate listingStatus getter", "mhanoh@verizon.net"
                , monthlyQ.getBest_lister());
    }
}