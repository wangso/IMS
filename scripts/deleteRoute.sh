#!/bin/bash
# $1: environment name

   subdomain=""$1"".lab.cyneuro.org
   echo $subdomain;

#check to see if command has succeeded or not
function checkErr() {
    echo -e "{\"status\":\"fail\"} at $1 ">&2; exit 1;
}

output=$(sudo curl -H "Content-Type: application/json" -X DELETE http://127.0.0.1:5555/api/routes/""$subdomain""/ 2>&1) && grep Failed <<< "$output"
echo $output
