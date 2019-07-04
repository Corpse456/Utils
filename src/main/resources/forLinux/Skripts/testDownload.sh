#!/bin/bash
ssh ubuntu@18.223.176.254 "rm /opt/dumps/test_wtn.sql"
ssh ubuntu@18.223.176.254 "mysqldump -uwtn -pwtnpassword test_wtn > /opt/dumps/test_wtn.sql"
scp ubuntu@18.223.176.254:/opt/dumps/test_wtn.sql /opt/dumps/
cd /opt/workspace/wtn-server/
mvn liquibase:dropAll
mysql -uwtn -pwtnpassword test_wtn < /opt/dumps/test_wtn.sql
