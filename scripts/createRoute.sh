#!/bin/bash
# $1: environment name
# $2: port for the environment

   env_name=$1
   port=$2
   subdomain=""$1"".lab.cyneuro.org
   echo $env_name
   echo $port
#check to see if command has succeeded or not
function checkErr() {
    echo -e "{\"status\":\"fail\"} at $1 ">&2; exit 1;
}

output=$(sudo curl -H "Content-Type: application/json" -X POST -d '{"source":"'"$subdomain"'","target":"http://129.114.17.39:'"$port"'"}' http://127.0.0.1:5555/api/routes/ 2>&1) && grep Failed <<< "$output"
echo $output

