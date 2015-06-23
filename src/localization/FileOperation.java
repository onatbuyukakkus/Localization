package localization;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileOperation {

	private final String NOT_FOUND = "";
	private String fileName = "";
	
	public void fileOperation(File fin) throws Exception {
		
		BufferedReader br = readFile(fin);
		String foundConcat = "";
		int lineNumber = 0;
		int length = 0;
		String line = null;
		String concatLine = "";
		String foundConstant = "";
		
	    PrintWriter constantFileOut = new PrintWriter(new BufferedWriter(new FileWriter("constantFileList1.txt", true)));
	    PrintWriter concatFileOut = new PrintWriter(new BufferedWriter(new FileWriter("concatFileList1.txt", true)));
		
		fileName = fin.getName();
		writeConstantsToFile("****************************************** class name:" +fileName+ " ************************************\n", constantFileOut);
		writeConcatsToFile("****************************************   class name:" +fileName+ " ************************************\n", concatFileOut);
		
		while ((line = br.readLine()) != null) {	
			line = line.trim();							

			length = line.length();
			lineNumber++;

			if(length == 0 || line.startsWith("//") )
				continue;
			concatLine = lineOperation(line, lineNumber, concatLine);
			if(!concatLine.equals(NOT_FOUND)) {
				foundConcat = findConcat(concatLine, lineNumber);
				foundConstant = findConstant(concatLine, lineNumber);
			
				if(!foundConcat.equals(NOT_FOUND)) {
					writeConcatsToFile("line - "+lineNumber +": " +foundConcat+"\n", concatFileOut);
					changeConcatToParam(concatLine);
				}
				
				if(!foundConstant.equals(NOT_FOUND))
					writeConstantsToFile("line - "+lineNumber +": " +foundConstant+"\n", constantFileOut);	

			}
		}
		
		br.close();
		constantFileOut.close();
		concatFileOut.close();
		
	}
	
	public BufferedReader readFile (File fin) throws Exception{
		FileReader f = new FileReader(fin);
		BufferedReader br = new BufferedReader(f);

		if(f.equals("") || f == null)
			throw new Exception("Dosya bulunamadý");
		return br;
	}
	
	
	public String lineOperation (String line, int lineNumber, String concatLine) throws Exception {
		
		line = line.trim();							
		if (line.endsWith(";")) {
			concatLine = new String();
			concatLine = concatLine.concat(line); 
//			findConcat(new String(concatLine), lineNumber);
//			findConstants(new String(concatLine), lineNumber);
			return concatLine;
//			
		} else if(line.endsWith("{") || line.startsWith("if") || line.startsWith("else if") || line.startsWith("else") || line.endsWith("}")){
			concatLine = new String();
		}else {
			concatLine = concatLine.concat(line); 
			return concatLine;
		}
		return NOT_FOUND;
	}
	
	public String findConcat(String concatLine, int lineNumber) throws Exception{
		String temp = "";
		String regex = "[\\S\\s]*(\"\\+\"|\\+\"|\"\\+)[\\S\\s]*";
		
		temp = concatLine.replaceAll("[\t\r\n\\s]*", "");
		
		if(temp.matches(regex) || temp.contains("append(\"") || temp.contains("concat(\"")){
			System.out.println("Concat: " +concatLine);
			return concatLine;
		}
		return NOT_FOUND;
	}
	
	public void writeConcatsToFile (String concatLine, PrintWriter out) throws Exception {
	    out.println(concatLine);
	}
	
	public String findConstant(String constantStr, int lineNumber) throws Exception{
		String regex  =  "\"([A-Z_]*|(\\w+\\.)+(\\w)+)\"";
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(constantStr);
		if(m.find()) {
			System.out.println(constantStr);
			return constantStr;
		}
		return NOT_FOUND;
	}
	
	public void writeConstantsToFile (String concatLine, PrintWriter out) throws Exception {		
	    out.println(concatLine);	
	}
	
	public void changeConcatToParam(String concatLine) throws Exception {		
		String regex = "\\\"(\\s)*(\\+)+[\\s\\S\\w]*(\\+)?";
		String param = " <:Par>";
		concatLine = concatLine.replaceAll(regex, param);
		System.out.println(concatLine);
	}
	 
}
