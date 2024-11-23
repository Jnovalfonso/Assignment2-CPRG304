package appDomain;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import implementations.*;
import utilities.Iterator;

public class Driver {

	public static void main(String[] args) {
		File xmlFile = new File("C:\\Users\\asus\\SAIT Local\\Object-Oriented Programming III\\assignment2StartingCode\\assignment2StartingCode\\res\\sample2.xml");
		MyArrayList<String> lines = new MyArrayList<>();
		MyStack<String> openingTags = new MyStack<>();
		MyStack<String> closingTags = new MyStack<>();
		MyStack<String> selfClosingTags = new MyStack<>();
		
		
		if (xmlFile == null) {
			return;
		}
		
		Scanner input = null;
		
		
		try {
			input = new Scanner(xmlFile);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// The first line of XML file is valid but has no closing tag, is ignored.
		if (input.hasNext()) {
            input.nextLine(); 
        }
		
		while (input.hasNext()) {
			lines.add(input.nextLine());
		}
		
		
		for (int i = 0; i < lines.size(); i++) {
		    String tag = lines.get(i).trim(); 
		    lines.set(i, tag); 
		    String line = lines.get(i);
		    
		    
		    for (int j = 0; j < line.length(); j++) {
	            char character = line.charAt(j); 
	            MyStack<String> notClosedYet = new MyStack<>(); 
	            
	            if (character == '<') {
	                StringBuilder tagName = new StringBuilder();

	                // Move to the next character to start reading the tag name
	                j++;
	                while (j < line.length() && line.charAt(j) != '>' && line.charAt(j) != ' ') {
	                    tagName.append(line.charAt(j));
	                    j++;
	                }

	                // Print the extracted tag name
	                notClosedYet.push(tagName.toString());
	            }
	            
	            if (character == '>') {
	            	if (line.charAt(j-1) == '/'){
	            		selfClosingTags.push(notClosedYet.peek());
	            		notClosedYet.pop();
	            	} else {
	            		
	            	}
	            }
	        }
		}

	}
	
	private static boolean isSelfClosing (String tag) {
		return tag.endsWith("/>");
	}
	
	private static boolean isStart (String tag) {
		return tag.startsWith("<") && tag.endsWith(">") && !isEnd(tag);
	}
	
	private static boolean isEnd (String tag) {
		
		return tag.startsWith("</");
	}
	

}
