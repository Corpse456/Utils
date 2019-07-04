#!/bin/bash
ssh ubuntu@52.15.147.9 "rm /opt/dumps/live_wtn.sql"
ssh ubuntu@52.15.147.9 "mysqldump -uwtn -pwtnpassword live_wtn > /opt/dumps/live_wtn.sql"
scp ubuntu@52.15.147.9:/opt/dumps/live_wtn.sql /opt/dumps/
cd /opt/workspace/wtn-server/
mvn liquibase:dropAll
mysql -uwtn -pwtnpassword test_wtn < /opt/dumps/live_wtn.sql
