#!/bin/bash

INSTALL_DIR=$(dirname $0)
PATH_SEPARATOR=:
[[ $(uname -s) = CYGWIN* ]] && PATH_SEPARATOR=\;
[[ $(uname -s) = MINGW*  ]] && PATH_SEPARATOR=\;
java -cp  "$INSTALL_DIR/bin${PATH_SEPARATOR}$INSTALL_DIR/bd-map.jar" bdmap.MakeMap $*
