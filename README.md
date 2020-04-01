# Weby Crawler

This crawler was made to collect news periodically from different sources .

## Configuration features:
* You can configure sites to crawl
* You can configure scrap rules by site's categories.
* You can assign labels to site's categories

Collected news and its labels are stored inside database.

### Profiles

* application.yml  this profile is include in the other profiles
* application-h2.yml h2 database configuration
* application-mysql.yml mysql database configuration
* application-dev.yml dev environment profile, includes h2 profile
* application-prod.yml  production environment profile, includes mysql profile

``` In order to run a profile, you can use: -Dspring.profiles.active=<profile_name>
Profile activation examples:
-Dspring.profiles.active=dev
-Dspring.profiles.active=prod