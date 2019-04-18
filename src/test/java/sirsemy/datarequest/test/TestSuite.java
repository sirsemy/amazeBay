package sirsemy.datarequest.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Sirsemy
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({sirsemy.datarequest.test.AmazeBayReaderWriterTest.class,
        sirsemy.datarequest.test.ListingTest.class,
        sirsemy.datarequest.test.MonthlyQueryTest.class,
        sirsemy.datarequest.test.ListingValidatorTest.class})
public class TestSuite {

}