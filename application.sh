#!/bin/bash

##directory where jar file is located
dir=/opt/project/ClickBackendAssesment/target

##jar file name
jar_name=pucheta-1.0-shaded.jar

if [ $# -eq 4]; then
    arg1=$1
    arg2=$2
    arg3=$3
    java -jar $dir/$jar_name arg1 arg2 arg3
else
    arg1=$1
    arg2=$2
    java -jar $dir/$jar_name arg1 arg2
fi

