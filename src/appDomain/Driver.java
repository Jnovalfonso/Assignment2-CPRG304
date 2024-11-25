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
        MyArrayList<String> tags = new MyArrayList<>();
        
        MyStack<String> tagsStack = new MyStack<>();
        MyQueue<String> errorQ = new MyQueue<>();
        MyQueue<String> extrasQ = new MyQueue<>();
        
        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner(xmlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Skip the first line of the XML file 
        if (fileScanner.hasNext()) {
            fileScanner.nextLine();
        }

        // Read the XML content line by line into the `lines` list
        while (fileScanner.hasNext()) {
            lines.add(fileScanner.nextLine());
        }

        // Iterate through each line of the XML file
        tags = getTags(lines);
        
        for (int index = 0; index < tags.size(); index++) {
        	String currentTag = tags.get(index);
        	
        	if (currentTag.endsWith("/")) {
        		continue;
        	} else if (!currentTag.startsWith("/")) {
        		tagsStack.push(currentTag);
        	}
        	else {
        		currentTag = currentTag.substring(1);
        		
        		// IF MATCHES TOP OF STACK
        		if (tagsStack.peek().equals(currentTag)) {
        			tagsStack.pop();
        		}
        		
        		// MATCHES HEAD OF ERRORQ
        		else if (!errorQ.isEmpty() && errorQ.peek().equals(currentTag)) {
        			errorQ.dequeue();
        		}
        		
        		// IF STACK IS EMPTY
        		else if (tagsStack.isEmpty()) {
        			errorQ.enqueue(currentTag);
        		}
        		
        		else {
        			
        			if (tagsStack.contains(currentTag)) {
        				
        				// SEARCH FOR A MATCHING START TAG 'currentTag'
        				boolean foundMatch = false;
        				while (!tagsStack.isEmpty()) {
        				    String startTag = tagsStack.pop();
        				    if (startTag.equals(currentTag)) {
        				        foundMatch = true;
        				        break; // Stop searching once a match is found
        				    } else {
        				        errorQ.enqueue(startTag); // Add mismatched tags to errorQ
        				    }
        				}

        				if (!foundMatch) {
        				    extrasQ.enqueue(currentTag); // Add unmatched end tag to extrasQ
        				}
        			}
        			
        			else {
        				extrasQ.enqueue(currentTag);
        			}
        			
        		}
        	}
        }
        
        System.out.println("========== ERROR LOG ==========");
  
        // IF STACK IS NOT EMPTY 
        while (!tagsStack.isEmpty()) {
            String errorTag = tagsStack.pop();
            errorQ.enqueue(errorTag); // Move to errorQ
        }
        
        if (errorQ.isEmpty() && extrasQ.isEmpty()) {
        	System.out.println("No errors");
        }

        
        // IF ONE QUEUE IS EMPTY
        if (!(errorQ.isEmpty() == extrasQ.isEmpty())) {
        	if (errorQ.isEmpty()) {
                // Report all elements in extrasQ as errors
                while (!extrasQ.isEmpty()) {
                    String errorElement = extrasQ.dequeue();  // Dequeue the element from extrasQ
                    System.out.println("Error: " + errorElement);  // Report as error
                }
            } else if (extrasQ.isEmpty()) {
                // Report all elements in errorQ as errors
                while (!errorQ.isEmpty()) {
                    String errorElement = errorQ.dequeue();  // Dequeue the element from errorQ
                    System.out.println("Error: " + errorElement);  // Report as error
                }
            }
        }
        
        while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {
            if (errorQ.peek().equals(extrasQ.peek())) {
                errorQ.dequeue();
                extrasQ.dequeue();
            } else {
                System.out.println("Error: Mismatched tag <" + errorQ.dequeue() + ">");
            }
        }

        // Report remaining items in errorQ
        while (!errorQ.isEmpty()) {
            System.out.println("Error: Unmatched start tag <" + errorQ.dequeue() + ">");
        }

        // Report remaining items in extrasQ
        while (!extrasQ.isEmpty()) {
            System.out.println("Error: Unmatched end tag </" + extrasQ.dequeue() + ">");
        }
        
        
        
   }
    
    private static MyArrayList<String> getTags (MyArrayList<String> lines) {
    	MyArrayList<String> tags = new MyArrayList<>();
    	
    	for (int index = 0; index < lines.size(); index++) {
            String currentLine = lines.get(index);  
            String trimmedLine = currentLine.trim();
            int charIndex = 0;

            while (charIndex < trimmedLine.length()) {
                char currentChar = trimmedLine.charAt(charIndex);
                
                if (currentChar == '<' ) {
                    StringBuilder tagNameBuilder = new StringBuilder();
                    charIndex++; 

                    while (charIndex < trimmedLine.length() && trimmedLine.charAt(charIndex) != '>' && trimmedLine.charAt(charIndex) != ' ') {
                        tagNameBuilder.append(trimmedLine.charAt(charIndex));
                        charIndex++;
                    }
                    
                    String tagName = tagNameBuilder.toString();
                    
                    if (trimmedLine.charAt(trimmedLine.length() - 2) == '/') {
                    	tagName = tagName + '/';
                    }
                    
                    tags.add(tagName);
                }
                
                charIndex++;
            }
        }
    	
    	return tags;
    }


}

