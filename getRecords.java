import java.io.*;
import java.util.Arrays;

public class getRecords
{

	int num_docs=0;
    long indexinPost=0;
	int indexinMap=0;
	
	public getRecords(int numdocs, long addr)
	{
		this.num_docs=numdocs;
		this.indexinPost=addr;
	}
	public getRecords(int lineNo)
	{
		this.indexinMap=lineNo;
	}	
	public void fromMapping() throws IOException
	{
	   
        RandomAccessFile rand = new RandomAccessFile("mapping.txt","r");
        FileInputStream stream= new FileInputStream("mapping.txt");
        BufferedReader br=null;
   
        try {
           
            rand.seek(indexinMap*20);
        	stream.skip(rand.getFilePointer());
            rand.close();
            br = new BufferedReader(new InputStreamReader(stream));
            String temp[] = br.readLine().split("\\s+");
            System.out.print("doc_name: "+String.format("%5s",temp[1])+"\n");
            br.close();

        }
        catch (Exception e){

        }
        finally {
            stream.close();
        }

	}
	
	public String [] fromPosting() throws IOException
	
	{
	
		String[] postTokens=new String[num_docs];

			
		try{


            RandomAccessFile rand = new RandomAccessFile("posting.txt","r");
            FileInputStream stream = new FileInputStream("posting.txt");
            rand.seek(indexinPost * 13);
            stream.skip(rand.getFilePointer());
            rand.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            for(int i=0; i< num_docs; i++ ){
                postTokens[i] = br.readLine();
                }
            stream.close();
            br.close();
            }
        catch(Exception e){
        }
        finally {
           
        }

        return postTokens;
	}

}

	
