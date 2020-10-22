import java.util.*;
import java.io.*;
import java.sql.Timestamp;

class ProcessQuery{
	
	public static PrintWriter writer = null;
	public static int count =0;
	
	public static void main(String[] args) throws IOException{
	
	
	//int count = 0;

		
		if(args.length != 1){
			System.out.println("Please enter two arguments \"Input directory name\"  and \"Output directory name\" ");
			System.exit(-1);
			}
		File outputFolder = new File("processed_query");
		if(!outputFolder.exists()){
			if(outputFolder.mkdir()){System.out.println("Output directory is created.");}
			else{System.out.println("Failed to create Output directory. ");}
		}
		
		File inputFolder = new File(args[0]);
		File[] fileNames = inputFolder.listFiles();
		//Arrays.sort(fileNames);
		for(File file: fileNames){
			if(file.isFile()){
			File outputFile = null;
			try(BufferedReader br = new BufferedReader(new FileReader(file))){
				
				MyLexer yy = new MyLexer(br);
				
				try{
					outputFile = new File("processed_query" +File.separator + file.getName());
					count++;
					
					writer = new PrintWriter(outputFile);
					
					
					while (yy.yylex() !=null){
						
						}
				} 
				catch(IOException e){
            System.out.println("Cannot create output file "+file.getName()+".txt");
			
          }catch(Exception e){
            /*System.out.println("don't know what happend with file: " + file.getName()  );
			e.printStackTrace();
			System.out.println();*/
          }
				finally {
				if(writer != null){
					writer.flush();
					writer.close();
				}
			
			}
						
			}
			
			}
		
			
		}
		
		
	
	}
	
	public static void downcase(String str){
			writer.println(str.toLowerCase());
  }
}

%%
LETTER=[A-Za-z]
DIGIT=[0-9]+(-[0-9]+)*|[0-9]+(,[0-9]+)*|[0-9]+"."[0-9]+
EMAIL=[a-zA-Z_][A-Za-z0-9_]*"@"[a-zA-Z]+"."[a-zA-Z]+
URL=http[s]?:\/\/www.[a-zA-Z0-9_\.\/~!@#$%^&*]*
WHITESPACE = [\t\n|\r]  
%class MyLexer
%type String
%eofval{
	return null;
%eofval}

%%

\<[^\>]*\> {}
{URL} {ProcessQuery.downcase(new String(yytext()));}
{EMAIL} {ProcessQuery.downcase(new String(yytext()));}
[a-zA-Z_][a-zA-Z0-9_]* {ProcessQuery.downcase(new String(yytext()));}

{WHITESPACE} {}

. {}
