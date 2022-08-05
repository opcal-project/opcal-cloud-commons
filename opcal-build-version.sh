#!/bin/bash

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`

DIR_PATH=`dirname ${SCRIPT}`

VERSION=$1

echo "opcal build version is [${VERSION}]"
# update xyz.opcal.build version
xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v ${VERSION} ${DIR_PATH}/pom.xml
xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v ${VERSION} ${DIR_PATH}/opcal-cloud-commons-dependencies/pom.xml

# get spring boot version from opcal build project
SPRING_BOOT_VERSION=$(${DIR_PATH}/mvnw help:evaluate -Dexpression=spring-boot.version | grep "^[^\\[]" |grep -v Download)

echo "spring boot version is [${SPRING_BOOT_VERSION}]"

# spring boot parent version
xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v ${SPRING_BOOT_VERSION} ${DIR_PATH}/opcal-cloud-starter-parent/pom.xml