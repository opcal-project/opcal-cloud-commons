#!/bin/bash

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
DIR_PATH=`dirname ${SCRIPT}`

rm -f ${DIR_PATH}/.flattened-pom.xml
rm -f ${DIR_PATH}/pom.xml.versionsBackup
rm -f ${DIR_PATH}/**/.flattened-pom.xml
rm -f ${DIR_PATH}/**/pom.xml.versionsBackup