import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LincolnParser {
	int numLinks = 0;
	String inputFilePath;
	String htmlRaw;
	ArrayList<String> subsections;
	HashMap<String,String> tags;
	
	public LincolnParser(String inputFilePath){
        this.inputFilePath = inputFilePath;
        htmlRaw = "";
        tags = new HashMap<String,String>();
    }
	public void read(){
	     String line = null;
	        try {
	       //read text file into a string
	            FileReader fileReader = 
	                new FileReader(inputFilePath);

	            BufferedReader bufferedReader = 
	                new BufferedReader(fileReader);

	            while((line = bufferedReader.readLine()) != null) {
	            	htmlRaw+=line;
	            }   

	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file.");                
	        }
	        catch(IOException ex) {
	        	ex.printStackTrace();
	        }
	   
	}
	public String getPlainText(){
		//only use this with plaintext files
		String fill = "&#160;";
		int textCheck = htmlRaw.indexOf(fill);
		String str = htmlRaw;
		while (textCheck >= 0){
			str = str.substring(0, textCheck) + " "
					+ str.substring(textCheck + fill.length(), str.length());
			textCheck = str.indexOf(fill);
		}
		return str.trim().replaceAll("\\s+", " ");
	}
}
