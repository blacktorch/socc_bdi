#!/usr/bin/env bash
echo "Building monitor..."
cd rcssmonitor && ./configure && make
cd ../rcssserver
echo "Building server..."
./configure && make
echo "Building agent..."
cd ..
mvn clean compile assembly:single