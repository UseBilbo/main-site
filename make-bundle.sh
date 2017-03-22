#!/bin/bash

cd main-site
npm run build
cd ..
mvn clean package

zip -9r bundle.zip main-site/build/* usebilbo-node/target/usebilbo*-fat.jar

