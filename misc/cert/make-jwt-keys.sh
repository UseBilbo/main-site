#!/bin/sh

keytool -genseckey -keystore keystore.jceks -storetype jceks -storepass secret -keyalg HMacSHA256 -keysize 2048 -alias HS256 -keypass secret
keytool -genseckey -keystore keystore.jceks -storetype jceks -storepass secret -keyalg HMacSHA384 -keysize 2048 -alias HS384 -keypass secret
keytool -genseckey -keystore keystore.jceks -storetype jceks -storepass secret -keyalg HMacSHA512 -keysize 2048 -alias HS512 -keypass secret
keytool -genkey -keystore keystore.jceks -storetype jceks -storepass secret -keyalg RSA -keysize 2048 -alias RS256 -keypass secret -sigalg SHA256withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 366
keytool -genkey -keystore keystore.jceks -storetype jceks -storepass secret -keyalg RSA -keysize 2048 -alias RS384 -keypass secret -sigalg SHA384withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 366
keytool -genkey -keystore keystore.jceks -storetype jceks -storepass secret -keyalg RSA -keysize 2048 -alias RS512 -keypass secret -sigalg SHA512withRSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 366
keytool -genkeypair -keystore keystore.jceks -storetype jceks -storepass secret -keyalg EC -keysize 256 -alias ES256 -keypass secret -sigalg SHA256withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 366
keytool -genkeypair -keystore keystore.jceks -storetype jceks -storepass secret -keyalg EC -keysize 256 -alias ES384 -keypass secret -sigalg SHA384withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 366
keytool -genkeypair -keystore keystore.jceks -storetype jceks -storepass secret -keyalg EC -keysize 256 -alias ES512 -keypass secret -sigalg SHA512withECDSA -dname "CN=,OU=,O=,L=,ST=,C=" -validity 366