#!/bin/bash

NEXT_DIRECTION=$1

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
DIR_PATH=`dirname ${SCRIPT}`

CURRENT_VERSION=$(${DIR_PATH}/mvnw help:evaluate -Dexpression=project.version | grep "^[^\\[]" |grep -v Download)
VERSION_NUMBER=${CURRENT_VERSION/-SNAPSHOT/}

echo "current version is [${CURRENT_VERSION}]"

NUMS=($(echo "${VERSION_NUMBER}" | tr '.' '\n'))

NEXT_VERSION=CURRENT_VERSION

if [ "${NEXT_DIRECTION}" = "-s" ];then
  s_version=$(echo ${NUMS[3]} + 1 | bc)
  NEXT_VERSION="${NUMS[0]}.${NUMS[1]}.${NUMS[2]}.${s_version}-SNAPSHOT"
elif [ "${NEXT_DIRECTION}" = "-n" ];then
  n_version=$(echo ${NUMS[2]} + 1 | bc)
  NEXT_VERSION="${NUMS[0]}.${NUMS[1]}.${n_version}.0-SNAPSHOT"
else
  echo "no version next direction"
  exit 1
fi

echo "next version is [${NEXT_VERSION}]"

${DIR_PATH}/versions-set.sh ${NEXT_VERSION}