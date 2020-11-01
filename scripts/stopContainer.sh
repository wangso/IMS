#!/bin/bash
# $1 user name
# $2 env name

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

output=$(sudo docker stop $1_$2 2>&1) && grep Failed <<< "$output"
echo $output
