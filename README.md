# amazeBay
A multi-stream handle program what's download from JSON REST API, check data-fields, write to log file,
create MySQL database, query database, write to report file and upload to FTP.

This is a database handle demo with random data on Amazon and eBay.

First the program get data from a database table in JSON format. It's in a JSON REST API, here:
https://my.api.mockaroo.com/listing?key=63304c70
The data change dynamically, and the API works with three sidetable:

https://my.api.mockaroo.com/location?key=63304c70
https://my.api.mockaroo.com/listingStatus?key=63304c70
https://my.api.mockaroo.com/marketplace?key=63304c70
These tables are static.

I planned the database and I used enums for two joining rows in 'listing' table. That's why I dropped out the 'listingStatus' and
'marketplace' tables. The result is in properDate.sql in the project root.
