

inputFolder=$1

outputFolder=$2
echo
echo
echo "### TOKENIZING HAS BEGUN, STARTING MODULES ### "
echo
echo

STARTTIME=$(date +%s)
javac Textprocessing.flex.java
java Textprocessing $inputFolder 
ENDTIME=$(date +%s)
echo

echo
echo "### TOKENIZING COMPLETED, INITIATING INDEXING ###"
echo
echo
echo "--> IT TOOK $[$ENDTIME - $STARTTIME] SECONDS TO COMPLETE THIS TASK"
echo
echo
echo
STARTTIME=$(date +%s)

echo "### INDEXING HAS BEGUN, STARTING MODULES ### "
echo
javac Pass1.java
java Pass1 tempout $outputFolder

echo
echo "### INDEXING COMPLETED ### "
echo

rm -rf *.class tempout	output


ENDTIME=$(date +%s)
echo
echo
echo "--> IT TOOK $[$ENDTIME - $STARTTIME] SECONDS TO COMPLETE THIS TASK"
echo
echo


