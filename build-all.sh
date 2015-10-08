#!/usr/bin/env bash

cd infrastructure/discovery-service;      ./gradlew clean build bootRun; cd -
cd infrastructure/load-balance-service;   ./gradlew clean build bootRun; cd -
cd infrastructure/authentication-service; ./gradlew clean build bootRun; cd -

cd business/project-service;              ./gradlew clean publishToMavenLocal bootRun; cd -
cd business/room-service;                 ./gradlew clean publishToMavenLocal bootRun; cd -
cd business/user-service;                 ./gradlew clean publishToMavenLocal bootRun; cd -

cd business/project-composite-service;    ./gradlew clean build bootRun; cd -
