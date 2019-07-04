#!/bin/bash
ssh gpuser@192.168.1.170 "rm /opt/dumps/pep_wtn.sql"
ssh gpuser@192.168.1.170 "mysqldump -uwtn -pwtnpassword test_wtn > /opt/dumps/pep_wtn.sql"
scp gpuser@192.168.1.170:/opt/dumps/pep_wtn.sql /opt/dumps/
cd /opt/workspace/wtn-server/
mvn liquibase:dropAll
mysql -uwtn -pwtnpassword test_wtn < /opt/dumps/pep_wtn.sql
