-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: amazebay
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `listing`
--

DROP TABLE IF EXISTS `listing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `listing` (
  `id` binary(16) NOT NULL,
  `title` varchar(45) CHARACTER SET utf8 NOT NULL,
  `description` varchar(170) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `location_id` binary(16) NOT NULL,
  `listing_price` float unsigned NOT NULL,
  `currency` varchar(3) CHARACTER SET utf8 NOT NULL,
  `quantity` tinyint(70) unsigned NOT NULL,
  `listing_status` enum('ACTIVE','SCHEDULED','CANCELLED') CHARACTER SET utf8 NOT NULL,
  `marketplace` enum('EBAY','AMAZON') CHARACTER SET utf8 NOT NULL,
  `upload_time` date DEFAULT NULL,
  `owner_email_address` varchar(30) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing`
--

LOCK TABLES `listing` WRITE;
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT INTO `listing` VALUES (_binary '7\"rI\èž\Ñv\ÉO·\á','Light Yellow Lupine','Lupinus lutescens C.P. Sm.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',988.52,'EUR',11,'CANCELLED','AMAZON','2017-09-10','josem@aol.com'),(_binary '\ï\Ù\Z›HI?ò\Ù?I€','Winecup Clarkia','Clarkia purpurea (W. Curtis) A. Nelson & J.F. Macbr. ssp. viminea (Douglas ex Hook.) F.H. Lewis & M.E. Lewis',_binary 'RIó<ú\ßDÙ«pGòœ ¦',116.42,'EUR',36,'ACTIVE','AMAZON',NULL,'mallanmba@mac.com'),(_binary '©\áAe\ZBB£ø_j|šd\Ó','Caloncoba','Caloncoba Gilg',_binary 'RIó<ú\ßDÙ«pGòœ ¦',289.92,'EUR',28,'SCHEDULED','EBAY',NULL,'jfmulder@me.com'),(_binary 'I\Ï\Ñ\ØuB\á€\Ýî·¸¥¨¥','Slender Cottongrass','Eriophorum gracile W.D.J. Koch var. gracile',_binary 'RIó<ú\ßDÙ«pGòœ ¦',494.44,'EUR',42,'ACTIVE','AMAZON','2017-01-25','wkrebs@comcast.net'),(_binary '\Zq¤\ÍOAH±Ÿ¦Lw\î','Kauai Fern','Vandenboschia tubiflora F.S. Wagner',_binary 'RIó<ú\ßDÙ«pGòœ ¦',900.95,'JPY',15,'CANCELLED','AMAZON','2017-12-09','mhanoh@verizon.net'),(_binary '\Z§\ÒšE°±E–)üE%K','Texas Willkommia','Willkommia texana Hitchc.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',606.05,'USD',46,'ACTIVE','AMAZON',NULL,'dburrows@live.com'),(_binary '%*\ìzAC •§û¢_','Copeland\'s Speedwell','Veronica copelandii Eastw.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',235.78,'HUF',24,'CANCELLED','AMAZON','2018-06-09','bhtower@sbcglobal.net'),(_binary '%@ød[A©\ì§÷Á•','Mudbabies','Echinodorus tenellus (Mart. ex Schult. f.) Buchenau',_binary 'RIó<ú\ßDÙ«pGòœ ¦',681.73,'AUD',20,'ACTIVE','AMAZON','2017-10-08','dburrows@live.com'),(_binary '\'\Õ\æ\ê[JN®\ë	7|Uˆ','Slimleaf Prairie Clover','Dalea tenuifolia (A. Gray) Shinners',_binary 'RIó<ú\ßDÙ«pGòœ ¦',512.46,'EUR',25,'ACTIVE','AMAZON','2017-10-11','wkrebs@comcast.net'),(_binary '-\Z\Ë_N¸BX½×‘bP4Ö“','Lily Of The Incas','Alstroemeria L.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',530.82,'AUD',18,'ACTIVE','EBAY','2017-10-09','cderoove@aolcom'),(_binary '1½Z/—PG\â­xª\ì\ã™\Ó','Japanese Lovegrass','Eragrostis amabilis (L.) Wight & Arn. ex Nees',_binary 'RIó<ú\ßDÙ«pGòœ ¦',766.61,'AUD',33,'CANCELLED','EBAY','2017-01-13','krueger@yahoo.com'),(_binary '2ƒ˜Á\äBL±uA|Ÿh¥\×','Finestem Needlegrass','Nassella tenuissima (Trin.) Barkworth',_binary 'RIó<ú\ßDÙ«pGòœ ¦',877.01,'EUR',2,'SCHEDULED','EBAY','2018-11-24','jgwang@aol.com'),(_binary ':öòx\ÔMÝŒ\í\ÚbÖ‡','Canadian Rim Lichen','Lecanora canadensis Lynge & H. Magn.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',209.82,'EUR',31,'ACTIVE','AMAZON',NULL,'cderoove@aolcom'),(_binary 'JKªœ`G‰¹\Í]\Ük=J\Ç','Chee Reedgrass','Calamagrostis epigeios (L.) Roth ssp. macrolepis (Litv.) Tzvelev [excluded]',_binary 'RIó<ú\ßDÙ«pGòœ ¦',850.11,'GBP',9,'CANCELLED','EBAY','2017-02-15','jipsen@hotmail.com'),(_binary 'Tt\àDv=Að˜Bk{m','Xylographa Lichen','Xylographa opegraphella Nyl. ex Rothr.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',16.27,'EUR',24,'CANCELLED','AMAZON','2017-05-06','krueger@yahoo.com'),(_binary 'T\Ç\Ê\ÐYôDpŠc\Ü.3B\ß','Navassa Nakedstem Carpetweed','Mollugo nudicaulis Lam. var. navassensis Ekman',_binary 'RIó<ú\ßDÙ«pGòœ ¦',213.11,'HUF',14,'ACTIVE','AMAZON','2017-07-10','overbom@yahoo.com'),(_binary 'Zr¼•`F/—ž„\Ñ\Ä%‘n','Lindheimer\'s Milkvetch','Astragalus lindheimeri Engelm. ex A. Gray',_binary 'RIó<ú\ßDÙ«pGòœ ¦',494.47,'USD',39,'CANCELLED','AMAZON','2018-06-27','overbom@yahoo.com'),(_binary '`Z3­þA~—\ÍA\\‹\ËX','Eastern Poison Ivy','Toxicodendron radicans (L.) Kuntze ssp. divaricatum (Greene) Gillis',_binary 'RIó<ú\ßDÙ«pGòœ ¦',322.39,'EUR',36,'CANCELLED','EBAY','2017-08-18','bhtower@sbcglobal.net'),(_binary 'b\Þ{~#SB\ä“%i¾T=','Cranichis-like Ladies\'-tresses','Cyclopogon cranichoides (Griseb.) Schltr.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',369.25,'HUF',38,'CANCELLED','AMAZON','2017-06-24','cderoove@aolcom'),(_binary 'f>º‰`\åLò¶\Ê#½ð@A','Tamaulipas Amaranth','Amaranthus tamaulipensis Henrickson',_binary 'RIó<ú\ßDÙ«pGòœ ¦',633.11,'HUF',9,'ACTIVE','EBAY','2018-06-09','stefano@live.com'),(_binary 't!ùi•J\n¥˜\á~+Ž#U','Gumhead','Gymnosperma glutinosum (Spreng.) Less.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',448.38,'JPY',4,'SCHEDULED','EBAY',NULL,'stefano@live.com'),(_binary 'yx\Ô\'NLF½úm.\ÒM\Ú','Cunila','Cunila D. Royen ex L.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',183.04,'GBP',39,'CANCELLED','EBAY','2018-03-12','josem@aol.com'),(_binary 'CI–K‘³;:\ëG2','Calla Lily','Zantedeschia Spreng.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',328.33,'GBP',23,'CANCELLED','AMAZON','2017-05-31','jfmulder@me.com'),(_binary 'Š\É\Ë²\éDeº\\·F£','Tall Blazing Star','Liatris aspera Michx. var. salutans (Lunell) Shinners',_binary 'RIó<ú\ßDÙ«pGòœ ¦',41.01,'AUD',28,'SCHEDULED','EBAY',NULL,'bhtower@sbcglobal.net'),(_binary '¦øx‹O ¥¦\æü\ÜE','Silverweed','Potentilla elegans Cham. & Schltdl.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',718.35,'AUD',7,'CANCELLED','AMAZON',NULL,'overbom@yahoo.com'),(_binary '’~ŒeC@·Ö‚ D\ä&','Cottonflower','Gossypianthus Hook.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',99.44,'USD',39,'SCHEDULED','AMAZON','2017-05-20','jipsen@hotmail.com'),(_binary '—&cŸD\âŒ\ïK>™\"‹\n','Drummond\'s Campion','Silene drummondii Hook.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',166.47,'EUR',30,'CANCELLED','EBAY','2017-08-11','mallanmba@mac.com'),(_binary 'ž=c=mB\êƒ+F\âŒ\ËD','Trans-pecos Desert Goldenrod','Xylothamia triantha (S.F. Blake) G.L. Nesom',_binary 'RIó<ú\ßDÙ«pGòœ ¦',29.93,'USD',37,'CANCELLED','AMAZON','2018-08-31','mallanmba@mac.com'),(_binary ' õB\ÃÁBï¢½}Q<½\ê\Ý','Orange Wall Lichen','Xanthoria parietina (L.) Th. Fr.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',611.57,'EUR',12,'CANCELLED','AMAZON','2018-03-20','mfburgo@yahoo.com'),(_binary '¤c\æû\àO}©R\ÇHU¬&','Variable Panicgrass','Dichanthelium commutatum (Schult.) Gould',_binary 'RIó<ú\ßDÙ«pGòœ ¦',538.93,'AUD',43,'SCHEDULED','EBAY','2017-04-11','krueger@yahoo.com'),(_binary '¬%•·+\çM¥˜‰«‚šj\Ê','Peruvian Spikesedge','Kyllinga tibialis Ledeb.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',545.82,'GBP',35,'ACTIVE','EBAY','2018-12-21','josem@aol.com'),(_binary '®LEU\ÏGÀ±\ÙÙ\ïVJ\ë','Chestnut Rush','Juncus castaneus Sm.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',79.88,'GBP',46,'ACTIVE','AMAZON',NULL,'bhtower@sbcglobal.net'),(_binary '¯b\àL\ßqBÆ±\0Dmf\\X','Western Trailing Ticktrefoil','Desmodium procumbens (Mill.) Hitchc. var. procumbens',_binary 'RIó<ú\ßDÙ«pGòœ ¦',107.53,'JPY',42,'SCHEDULED','EBAY','2018-01-30','wkrebs@comcast.net'),(_binary '±?\çj\é\ïJŠŸwÆeò~-','Threeleaf Goldback Fern','Pityrogramma trifoliata (L.) R. Tryon',_binary 'RIó<ú\ßDÙ«pGòœ ¦',896.33,'HUF',48,'CANCELLED','EBAY',NULL,'jfmulder@me.com'),(_binary 'º\çu+Bÿª^\ÎQsq\Ì\é','Running Strawberry Bush','Euonymus obovatus Nutt.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',860.77,'HUF',34,'ACTIVE','EBAY','2017-03-18','dburrows@live.com'),(_binary '»¶1UŸGK¢Š\ã‚\Å+ö','Ricegrass','Ã—Achnella Barkworth',_binary 'RIó<ú\ßDÙ«pGòœ ¦',581.48,'HUF',40,'CANCELLED','AMAZON','2017-11-20','bhtower@sbcglobal.net'),(_binary '\Ë<\è76\àHSŸ†ú\ä´','Hairy Sedge','Carex lacustris Willd.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',416.26,'EUR',28,'SCHEDULED','EBAY','2017-12-06','jfmulder@me.com'),(_binary '\Ò8÷T·8FÍ›bö„1\"I','Riverbank Grape','Vitis riparia Michx.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',493.51,'JPY',14,'SCHEDULED','AMAZON','2017-11-07','mallanmba@mac.com'),(_binary '\Óâœ©vžJo»ƒF*¡œa','Fall Tansyaster','Machaeranthera asteroides (Torr.) Greene',_binary 'RIó<ú\ßDÙ«pGòœ ¦',655.87,'GBP',8,'SCHEDULED','EBAY','2018-04-18','jgwang@aol.com'),(_binary '\Õ!¸g<K›º‚Óƒl­','Caribbean Crabgrass','Digitaria dolichophylla Henr.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',959.87,'JPY',50,'ACTIVE','AMAZON','2018-03-15','bhtower@sbcglobal.net'),(_binary '\Öý`”ö\ÝH\î±4uÑ’”\Æ','Klamath Mountain Buckwheat','Eriogonum hirtellum J.T. Howell & Bacig.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',235.34,'EUR',8,'ACTIVE','AMAZON','2018-09-06','jipsen@hotmail.com'),(_binary '\æ{|¨&IµA		U.s','Australian Saltbush','Atriplex semibaccata R. Br.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',686.01,'EUR',48,'ACTIVE','AMAZON',NULL,'krueger@yahoo.com'),(_binary 'öÆ¥k\èDŽ§OÒ´{\ÛFE','Cnicus','Cnicus L.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',223.82,'HUF',45,'ACTIVE','AMAZON','2018-04-04','dburrows@live.com'),(_binary '÷ÁþNL\ç—W;¨¸˜\Ä\ç','Branching Phacelia','Phacelia ramosissima Douglas ex Lehm. var. montereyensis Munz',_binary 'RIó<ú\ßDÙ«pGòœ ¦',329.07,'EUR',41,'ACTIVE','AMAZON','2017-07-19','mhanoh@verizon.net'),(_binary 'ÿ“z’D#·†G\áûedö','Swamp Pricklegrass','Crypsis schoenoides (L.) Lam.',_binary 'RIó<ú\ßDÙ«pGòœ ¦',94.71,'GBP',43,'CANCELLED','EBAY','2017-07-15','josem@aol.com');
/*!40000 ALTER TABLE `listing` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-11 16:11:47
