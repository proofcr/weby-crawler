# Weby Crawler

This crawler collects news periodically from multiples sources.

 Configuration features:
* You can configure sites to crawl
* You can configure scrap rules by site's categories.
* You can assign labels to site's categories

Collected news and its labels are stored inside database.

## Profiles

* application.yml  this profile is include in the other profiles
* application-h2.yml h2 database configuration
* application-mysql.yml mysql database configuration
* application-dev.yml dev environment profile, includes h2 profile
* application-prod.yml  production environment profile, includes mysql profile


## Local mode
- Run `com.crevainera.weby.WebyCrawlerApplication` from your IDE
- Required VM options:
    -  `-Dspring.profiles.active=dev,prod`
    
##  Package mode
- Run `mvn package` from project root

## Server mode
- Upload the jar file to the server
- Run `java -jar ./target/weby-crawlerNoticiasLasFlores-[version].jar`

