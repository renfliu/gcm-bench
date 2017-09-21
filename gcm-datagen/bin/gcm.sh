#!/usr/bin/env bash

JAVA=`which java`

if [ -z "$JAVA" -a -n "$JAVA_HOME" ]; then
  JAVA=$JAVA_HOME/bin/java
fi

if [ -z "$JAVA" -a -n "$JDK_HOME" ]; then
  JAVA=$JDK_HOME/bin/java
fi

if [ -n "$JAVA" ]; then
  $JAVA -Xms256m -Xmx1g -jar lib/gcm-gen.jar $*
else
  echo "Can't find java path"
fi

