#!/bin/bash
ssh ubuntu@18.216.237.224 "rm /opt/dumps/prelife.sql"
ssh ubuntu@18.216.237.224 "mysqldump -uwtn -pwtnpassword test_wtn > /opt/dumps/prelife.sql"
scp ubuntu@18.216.237.224:/opt/dumps/prelife.sql /opt/dumps/
cd /opt/workspace/wtn-server/
mvn liquibase:dropAll
mysql -uwtn -pwtnpassword test_wtn < /opt/dumps/prelife.sql
