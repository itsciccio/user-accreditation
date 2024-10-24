#!/bin/bash

mvn clean package -DskipTests

java -jar target/user_accreditation-1.0.0.jar --server.port=9999