#!/bin/sh
alpn_version=`cat alpn.version`
bin_version=`cat bin.version`
echo "Starting node 0 version $bin_version with ALPN version $alpn_version"
cmd_line="java -Xbootclasspath/p:./misc/alpn-boot-$alpn_version.jar -jar usebilbo-node/target/usebilbo-node-$bin_version-fat.jar"
echo $cmd_line "$@"
$cmd_line "$@"