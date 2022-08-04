#!/bin/bash

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`

DIR_PATH=`dirname ${SCRIPT}`

VERSION=$1

echo ${VERSION}

xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v ${VERSION} ${DIR_PATH}/opcal-cloud-starter-parent/pom.xml