#!/bin/bash
cd /opt/workspace/wtn-server/
mvn liquibase:dropAll
mysql -uwtn -pwtnpassword test_wtn < /opt/dumps/live_wtn.sql
