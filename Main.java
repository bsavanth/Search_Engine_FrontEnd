import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    
    public static void main(String args[])throws  IOException{
    
    	double[] accumulator = new double[956];        
        BufferedReader br=new BufferedReader(new FileReader("processed_query/query.txt"));
        String curr_word ="";
    	
    	while((curr_word = br.readLine()) != null)
    	
    	{
           
		
				RandomAccessFile rand = new RandomAccessFile("dict.txt","r");
				FileInputStream stream = new FileInputStream("dict.txt");
				rand.seek(find(curr_word)*21);
				stream.skip((rand.getFilePointer())); 
				rand.close();
				BufferedReader bre = new BufferedReader(new InputStreamReader(stream));
				String dictEntry = bre.readLine().trim();

				 while(!String.format("%-2.2s",dictEntry).equals(String.valueOf(-1)) && !String.format("%-10.9s",dictEntry).equals(String.format("%-10.9s",curr_word))){
						dictEntry = bre.readLine().trim();
						if(String.format("%-2.2s",dictEntry).equals(String.valueOf(-1))){
							dictEntry = String.valueOf(-1);
						}
						else if(String.format("%-10.9s",dictEntry).equals(String.format("%-10.9s",curr_word))){
							break;
						}
					}
					stream.close();
					bre.close();

					if(String.format("%-2.2s",dictEntry).equals("-1"))
					{
						System.out.println("\nNo files found\n");
					}
					else 
					{
						String[] dictEntryArray = (dictEntry.substring(10)).split("\\s+");
						getRecords gR = new getRecords(Integer.parseInt(dictEntryArray[0]), Long.parseLong(dictEntryArray[1]));
						String entries[]=gR.fromPosting();
						for(int i=0; i<entries.length;i++)
						{

							String temp[] = entries[i].split("\\s+");
							int docid = Integer.parseInt(temp[0]);
							double rtf= Double.parseDouble(temp[1]);
							accumulator[docid] +=rtf;
						}

					}
        }
	
		 double[] hold= new double[accumulator.length];
		
		 for(int i=0; i<accumulator.length; i++)
		 
		 {
        		 hold[i]=accumulator[i];
         }

		
        HashMap<Integer, Double> hash_map = new HashMap<Integer, Double>();
        
        for(int i=0; i<accumulator.length; i++)
		 
		 if (accumulator[i]>0)
		 {
              	hash_map.put(i, accumulator[i]);	
         }
         
         Map<Integer, Double> sortedMap = hash_map.entrySet().stream()
                         .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                         .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
         
        int count=0;    
         StringBuilder str = new StringBuilder("");            
        for (Map.Entry<Integer, Double> entry : sortedMap.entrySet()) 
        
        {
        	if(count<10)
        	{
        	count++;
    		int val = entry.getKey();
    		getRecords gR1 = new getRecords(val);
            str.append(gR1.fromMapping()+" "+accumulator[val]+"\n");
             } 	
		}
                              
    	System.out.println(str.toString());
 
    }

    public static int find(String str)
    {
        long sum=0;
        long index;

        
        for(int i=0;i<str.length();i++)
            sum=(sum*19)+str.charAt(i); 

        if(sum < 0)				
            sum = sum * -1;

        index= sum%(44000*3);
        int index2 = (int) index;



        return index2;
    }


}

