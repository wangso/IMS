#!/bin/bash
# $1 is username
# $2 is module name
# $3 is environment name
# $4 is port TCP
# $5 is port VNC  

   user=$1
   image=$2
   env_name=$3
   tcp=$4
   vnc=$5

   echo $user
   echo $image
   echo $env_name
   echo $tcp
   echo $vnc

#check to see if there are 5 arguments attached to omni command
if [ "$#" -lt 5 ]
then
    echo -e "Not enough arguments supplied, please provide 5 arguments. Exiting..."
    exit
fi

#check to see if command has succeeded or not
function checkErr() {
    echo -e "{\"status\":\"fail\"} at $1 ">&2; exit 1;
}

output=$(sudo docker run -p $4:80 -p $5:5900 -d -e USER=$1 -e PASSWORD=$1 -e HTTP_PASSWORD=$1  -v /dev/shm/$1_$3:/dev/shm --name $1_$3 $2 2>&1) && grep Failed <<< "$output"
echo $output

