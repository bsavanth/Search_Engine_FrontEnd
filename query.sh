
#! /bin/bash


# echo 
# echo
# echo "	#*****************************************#"
# echo "	#                                         #"
# echo "	#                                         #"
# echo "	#   Welcome to my search engine           #"
# echo "	#                                         #"
# echo "	#                                         #"
# echo "	#*****************************************#"
# echo
# echo
# echo

echo
echo "Enter your query and blink exactly once->"
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
echo "Relevant Files --->"
echo

java Main
ENDTIME=$(date +%s)
rm -r *.class Input_Tokens processed_query


echo
echo "--> IT TOOK $[$ENDTIME - $STARTTIME] SECONDS TO COMPLETE THIS TASK"
echo
echo
echo
echo "Done."

echo
echo