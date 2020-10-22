import java.io.*;
import java.util.Arrays;

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
		
        int pastmax = Integer.MIN_VALUE;
        int count =0;
        for(int i=0; i<hold.length; i++){
            count++;
            if(count>10){
                break;
            }
            int max = -1; 
            double maxval =0; 
      		for(int j=0; j<hold.length;j++){
           		if(maxval < hold[j]){
              		 maxval = hold[j];
               		 max = j;
               		 hold[j] = 0;
            		}
       			 }
    
            if(max < 0){
                break;
            }
            if(pastmax != max){
                pastmax = max;
                System.out.print("	doc_id: "+String.format("%3s",max)+"\t"+"rtf-idf: "+String.format("%10.7s",accumulator[max])+"\t");
              	getRecords gR1 = new getRecords(max);
                gR1.fromMapping();
            }
            else {
            	
                break;
            }



        }
        
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

