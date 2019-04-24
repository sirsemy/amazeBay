package sirsemy.datarequestapi;

/** @author Sirsemy
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            AmazeBayReaderWriter dataBase = new AmazeBayReaderWriter("properties.ini");
            dataBase.locationJsonDownloader();
            dataBase.listingJsonDownloader();
            dataBase.lineReviser();
            dataBase.writeToImportLog();
            dataBase.uploadToDataBase();
            dataBase.queries();
            dataBase.jsonWriter();
            dataBase.uploadToFtp("report.json");
            dataBase.streamCloser();
        } catch (ListingDAOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
