
#! /bin/bash



echo

read name
echo

mkdir Input_Tokens
inputFolder=Input_Tokens
echo "$name" > $inputFolder/query.txt
STARTTIME=$(date +%s)
javac ProcessQuery.flex.java
java ProcessQuery $inputFolder 





javac Main.java
echo

java Main
ENDTIME=$(date +%s)
rm -r *.class Input_Tokens processed_query


echo
echo
echo
echo

echo
echo