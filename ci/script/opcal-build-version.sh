#!/bin/bash

## this script depends on xmlstarlet
set -e

SCRIPT=$(readlink -f "${BASH_SOURCE:-$0}")
SCRIPT_DIR_PATH=$(dirname ${SCRIPT})
CI_DIR_PATH=$(dirname ${SCRIPT_DIR_PATH})
ROOT_PATH=$(dirname ${CI_DIR_PATH})

VERSION=$(grep "opcal-commons-build.version" "${ROOT_PATH}"/dependencies.properties|cut -d'=' -f2)

echo "opcal build version is [${VERSION}]"
# update xyz.opcal.build version
xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v "${VERSION}" "${ROOT_PATH}"/pom.xml
xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v "${VERSION}" "${ROOT_PATH}"/opcal-cloud-commons-dependencies/pom.xml

"${ROOT_PATH}"/mvnw -U clean compile >> /dev/null 2>&1

# get spring boot version from opcal build project
SPRING_BOOT_VERSION=$("${ROOT_PATH}"/mvnw help:evaluate -Dexpression=spring-boot.version | grep "^[^\\[]" |grep -v Download |grep -v Progress)

echo "spring boot version is [${SPRING_BOOT_VERSION}]"

# spring boot parent version
xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v "${SPRING_BOOT_VERSION}" "${ROOT_PATH}"/opcal-cloud-starter-parent/pom.xml
echo "parent module version update is done!"