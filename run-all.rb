#!/usr/bin/env ruby

require 'fileutils'
include FileUtils

FileUtils.cd('./infrastructure/discovery-service/') do
    system("./gradlew clean build")
    system("./gradlew bootRun &")
    system("cd -")
end

FileUtils.cd('./infrastructure/load-balance-service/') do
    system("./gradlew clean build")
    system("./gradlew bootRun &")
    system("cd -")
end

FileUtils.cd('./infrastructure/authentication-service/') do
    system("./gradlew clean build")
    system("./gradlew bootRun &")
    system("cd -")
end

FileUtils.cd('./business/project-service/') do
    system("./gradlew clean publishToMavenLocal build")
    system("./gradlew bootRun &")
    system("cd -")
end

FileUtils.cd('./business/room-service/') do
    system("./gradlew clean publishToMavenLocal build")
    system("./gradlew bootRun &")
    system("cd -")
end

FileUtils.cd('./business/user-service/') do
    system("./gradlew clean publishToMavenLocal build")
    system("./gradlew bootRun &")
    system("cd -")
end

FileUtils.cd('./business/project-composite-service/') do
    system("./gradlew clean build")
    system("./gradlew bootRun &")
    system("cd -")
end