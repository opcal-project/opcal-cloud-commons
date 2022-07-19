#!/bin/bash

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`

DIR_PATH=`dirname ${SCRIPT}`

VERSION=$1

echo ${VERSION}

${DIR_PATH}/mvnw versions:set -DnewVersion=${VERSION}
${DIR_PATH}/mvnw versions:set -DnewVersion=${VERSION} -pl opcal-cloud-commons-dependencies
${DIR_PATH}/mvnw versions:set -DnewVersion=${VERSION} -pl opcal-cloud-starter-parent