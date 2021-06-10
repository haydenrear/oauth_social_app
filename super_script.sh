#!/bin/bash

mvn clean install
cd backendforfrontend
chmod 777 npm
chmod 777 ng
./npm install
./ng build