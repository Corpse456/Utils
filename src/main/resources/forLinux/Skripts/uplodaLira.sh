#!/bin/bash
mysqldump -uwtn -pwtnpassword test_wtn > /opt/dumps/test_wtn.sql
scp /opt/dumps/test_wtn.sql gpuser@192.168.1.170:/opt/dumps/
ssh gpuser@192.168.1.170 'cd /opt/wtn-server/; mvn liquibase:dropAll'
ssh gpuser@192.168.1.170 'mysql -uwtn -pwtnpassword test_wtn < /opt/dumps/test_wtn.sql'
