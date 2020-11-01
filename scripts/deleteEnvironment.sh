#!/bin/bash
# $1 is username
# $2 is environment type

   user=$1
   env_type=$2
   echo $user
   echo $env_type

#check to see if there are 5 arguments attached to omni command
if [ "$#" -lt 2 ]
then
    echo -e "Not enough arguments supplied, please provide 2 arguments. Exiting..."
    exit
fi

#check to see if command has succeeded or not
function checkErr() {
    echo -e "{\"status\":\"fail\"} at $1 ">&2; exit 1;
}

output=$(sudo docker rmi $2 2>&1) && grep Failed <<< "$output"
echo $output
