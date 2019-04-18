package sirsemy.datarequestapi;

/** @author Sirsemy
 */
public class ListingDAOException extends Exception {
    
    public ListingDAOException() {
    }

    public ListingDAOException(String string) {
        super(string);
    }

    public ListingDAOException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public ListingDAOException(Throwable thrwbl) {
        super(thrwbl);
    }
}
