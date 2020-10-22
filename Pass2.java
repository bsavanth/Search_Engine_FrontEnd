import java.io.*;
import java.util.*;

public class Pass2 {   
	
	
// Key=term=token, file =document; term frequency = frequency of a token in that file;  nums_docs = number of files the word appeared
	
	
	//Default constructor for pass2
    Pass2(HashTable globalhash, String output) throws Exception{
        
        // define, declare writer to map filenames to indices
        PrintWriter writer1 = new PrintWriter("mapping.txt");

		// output directory of pass1 is input directory of pass2
        File inputFolder = new File(output);
        // store list of files of output directory in file array
        File[] filesList = inputFolder.listFiles();
        
    	int n = filesList.length;

        
		//Generating mapping file, just map indices to file array contents in that order
        for(int i=0; i<filesList.length; i++){
         	 writer1.println(String.format("%-5.5s",i) +" "+ String.format("%13.13s", filesList[i].toString().substring(7)));

         } 
        
        //We have about 1000 files, so we need 1000 file pointers(Buffered readers)
        
        //Buffered reader array to store buffered readers
        BufferedReader[] readers = new BufferedReader[filesList.length];
        double[] filelen = new double[filesList.length];

        
      
      	//Iterate over file list, create buffered reader pointers and store in buff reader array
        for(int i=0; i<readers.length; i++){
        	BufferedReader br = new BufferedReader(new FileReader(filesList[i]));
            readers[i] =br;
           
        }
       
        
		// Now, we are working with first entry of each file so we have about 1000 files, we need about 1000 strings to hold entries
        String[] firstWords = new String[filesList.length];
	
		//Use buffered readers of each file to read first entry of each file.
		// buffered reader 0 helps in getting contents of file 0 (Hypothetically)
		// Hypothetically because files in file array
        for(int i=0; i<readers.length; i++){

            try {
            		
            	//firstline of ith file, get it by using buffreader of ith file
                firstWords[i] = readers[i].readLine();
            }
            catch (Exception e){
                System.out.println(e);
            }

        }


	   // Initialize writer for post
       PrintWriter writer = new PrintWriter("posting.txt");
        try{
        	String tempo="";
        	// This stores the most recent least lexicographic entry among 1000 first lines we grabbed 
            String previous = null;
            //We Start entering posting records from 0th index
            int start = 0;
            double idf=0;
            
             boolean nullcheck = true;
			 //check if any of 1000 lines we grabbed are null
       		 for(int i=0; i<firstWords.length; i++){
           		 if(firstWords[i] != null){
                		nullcheck = false;
                		break;
            		}
       			 }
       		//Stop indexing if null
            while(!nullcheck){
			  // Stores current least Lexicographic entry
			  String current ;
			  //We are trying to find least lexicographic entry among 1000 keys here
               String firstAlphabetically = "";
     		   int leastlexico =0;
      		   if(firstWords[0] != null){
      		   		//Split the entry to two, entry has two things, term and term frequency
        			String[] arr = firstWords[0].split(" ");
        			// term of the entry
					firstAlphabetically = arr[0] ;}
			   else {firstAlphabetically = "zzzzzzzzzzzzzzzzzzzzzzzzzz";}
				for(int i =0; i<firstWords.length; i++){
          			  if(firstWords[i] != null) {
            				String[] arr = firstWords[i].split(" ");
            		       if (arr[0].compareTo(firstAlphabetically) < 0) {
                    				firstAlphabetically = arr[0];
                    				leastlexico = i;
               				 }
           			 	}
       			 }

           
                //We found the least entry, An entry has term and term frequency, we need term/key
				String[] arr = firstWords[leastlexico].split(" ");
				double total_tokens= filelen[leastlexico];
				//Got the key, least lexicograhic key
                current = arr[0];
                if(!current.equals(previous)){
                    previous = current;
					//update global hashtable record, append start address to it
                    String value = globalhash.getName(current) + " "+start; 
                    //updated
                	idf = (double) (1+(Math.log((1+n)/(1+Double.parseDouble(globalhash.getName(current))))));
                	tempo = String.format("%.8f",idf);
				    globalhash.insert(current, value);
                    //move to next index
                    start++;

                }
                
                else {
                    start++;
                }
				
				//Keep writing least lexicographic elements in posting file
				//Entry will be doc_id, term frequency. Doc_id to Doc_name see mapping.txt
				
				double tfidf = Double.parseDouble(arr[1])*Double.parseDouble(tempo);
				String temp = String.format("%.8f",tfidf);
				writer.println(String.format("%-5.5s",leastlexico) + String.format("%7.7s",temp) );                
                
                // If there is a hit at ith file, the next entry in that file is read to compare with other 1000 entries
                String next = readers[leastlexico].readLine();
	
       			 if(next == null){
           			 firstWords[leastlexico] = null;
       			 }
       			 else {
       			 //Reading next entry from the same file we got a hit on least lexico previously
         		     firstWords[leastlexico] = new String(next);
        		}
        		nullcheck=true;
				//Checking for null entries, loops stops if there is null
				for(int p=0; p<firstWords.length; p++){
           		 if(firstWords[p] != null){
                		nullcheck = false;
                		break;
            		}
       			 }
                

            }
        }
        finally {
        
        	//print global hashtable which is nothing but dict file
        	//Hashtable class takes care of creating files and righting bla bla...
    		globalhash.print("dict.txt");
    		//Flush file writers to dump content onto main memory completely and close
            if(writer != null || writer1!=null){
                writer.flush();
                writer.close();
                writer1.flush();
                writer1.close();
                
                //Test case
               
				
				// Test string
// 				String s = "algorithm";
// 				
// 				Find index of test string in global hashtable
// 				int temp = globalhash.find(s);
// 				Got the index, now print it
// 				System.out.println("HashFunction('algorithm') = "+temp);
// 				System.out.println();
// 				Got the entry, print string, number of docs it is repeated in, starting address in post file.
// 				System.out.println("Dict record ["+temp+"] = "+globalhash.getaddr(temp)+" "+globalhash.getName(s));	
// 				
		
		
		

                System.out.println("\n");

                
                
                
                
            }
        }



    }




   

}


