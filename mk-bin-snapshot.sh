#!/bin/sh
cd main-site
npm run build
cd ..
mvn clean package

site_dir=main-site/build

bin_version=`cat pom.xml | grep \<version\> | head -1 | egrep -o "[0-9]+\.[0-9]+\.[0-9]+(-SNAPSHOT)?"`
alpn_version=`cat pom.xml | grep alpn\.version | egrep -o "[0-9]+\.[0-9]+\.[0-9]+\.v[0-9]+"`
echo $bin_version >bin.version
echo $alpn_version >alpn.version
echo "Assembling binaries version $bin_version with ALPN version $alpn_version"

cp ~/.m2/repository/org/mortbay/jetty/alpn/alpn-boot/$alpn_version/alpn-boot-$alpn_version.jar ./misc/

zip -9r bundle-$bin_version.zip usebilbo-node/target/usebilbo*-fat.jar ./misc/* $site_dir/* *.sh *.version
