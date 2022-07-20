#!/bin/bash

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
DIR_PATH=`dirname ${SCRIPT}`

find ${DIR_PATH}/ -type f -iname '.flattened-pom.xml' | xargs -I {} rm -f {};
find ${DIR_PATH}/ -type f -iname 'pom.xml.versionsBackup' | xargs -I {} rm -f {};