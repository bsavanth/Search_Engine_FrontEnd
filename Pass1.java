import java.io.*;
import java.util.*;
import java.text.DecimalFormat;


public class Pass1 {
        
    
    public static void main(String[] args) throws Exception {

		//global hashtable(our dict file),  Actual size is (3 * 700000)
        HashTable globalhash = new HashTable(44000);


		// Create an output file if you dont have one
        File output = new File(args[1]);
        if (!output.exists()) {
             output.mkdir();
             }
        
         BufferedReader bufReader = new BufferedReader(new FileReader("stopwords.txt"));
         ArrayList<String> stopwords = new ArrayList<>(); 
         String curr = bufReader.readLine(); 
         while (curr != null) 
         {
          stopwords.add(curr.trim());
          curr = bufReader.readLine();
         }
          bufReader.close();
		
 
       // Reading tempoutput files
        File input = new File(args[0]);
        // Storing files in a file list
        File[] List_Of_Files = input.listFiles();
                double count =0;
        //iterate each file from the file list
        for (File file : List_Of_Files) {
        	// file exists and not null
            if (file.isFile()) {
				
				
				
                File output1 = null;
                //local hashtable (Actual size will be three times this size)
                HashTable localhash = new HashTable(2000);
                //local list - Arraylist
                ArrayList<String> tokenlist = new ArrayList<>();
                
				PrintWriter writer=null;
				
				Map<String, Integer> wordCounts = new HashMap<>();
				BufferedReader bread = new BufferedReader(new FileReader(file));
				String current="";
				while((current = bread.readLine()) != null){
					current=current.trim();
					if (!wordCounts.containsKey(current))
    						wordCounts.put(current, 1);
					else
    					wordCounts.put(current, wordCounts.get(current) + 1);
				
				}		
				bread.close();		
				
				//Always wrap file reader in buffered reader for thread safe operations
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    
                    //Creates output file in output directory with same name as it is in temmpout
                    output1 = new File(args[1] + "/"+ file.getName());
                    
                    writer = new PrintWriter(output1);
                    
                    //Read each line in the file (unsorted tokens)
                    String line;
                    while((line = br.readLine()) != null){
                        if( !line.equals("")&& !stopwords.contains(line) &&line.length()!=1 ){
                        	// Send tokens to Hashtable class for insertion(It will handle rest)
                            localhash.insert(line);
                            count++;
                        }

                        if( !tokenlist.contains(line)&& !stopwords.contains(line)&&line.length()!=1 ){
                        	//Calculate the vocabulary of the file]
                            tokenlist.add((line.toString()));
                        }

                    }
                    
                     for(int i=0; i<localhash.getSize(); i++){
                        String str = localhash.getaddr(i);
                        if(!str.equals("")){
                            String val = localhash.getName(localhash.getaddr(i));
                            double rtf = Integer.parseInt(val)/count;
                            DecimalFormat decimalFormat = new DecimalFormat("0.000000");
                            val =  decimalFormat.format(rtf);
                            localhash.insert(str,val);

                        }
                    }
                    count=0;


					//Sort vocabulary by term in ascending order, Crucial for pass2
                    Collections.sort(tokenlist);

                    //iterate each term in vocabulary list 
                    for(String str : tokenlist){
						
						//write in the file, term and term frequency
						//HashTable class already calculated term frequencies
                        writer.println(String.format(str) + " "+ String.format(localhash.getName(str)));
                    }
                    
                    //Dump local hashtable into global hash, grab the index of key of local hash table, put it in global hashtable on the same index

                    for(int i=0; i<localhash.getSize(); i++){
                        String str = localhash.getaddr(i);
                        if(!str.equals("")){
                            globalhash.insert(str);
                        }
                    }
                    
                     //flush localhash for next file iteration
        			localhash.clear();

                }
				//Flush and close to copy contents from buffer onto file
                finally {
                    if(writer != null){
                    	//dump content onto main memory which is file
                        writer.flush();
                        writer.close();

                    }

                }
            }
        }

       
	//call pass2, pass global hashtable and the output file we generated
         Pass2 two = new Pass2(globalhash, args[1]);

    }


}


