#!/bin/bash

set -e

# Allow the container to be started with `--user`
if [[ "$1" = 'java' && "$(id -u)" = '0' ]]; then
    chown -R opcal "/app"
    exec gosu opcal "$0" "$@"
fi

exec "$@"