package appDomain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import implementations.*;
import utilities.Iterator;

public class Driver {

    public static void main(String[] args) {
    	ParseArgs.parseArgs(args); // Parse the command-line arguments
        File parsedFile = ParseArgs.getFile();
        if (parsedFile != null) {
            System.out.println("Parsed file name: " + parsedFile.getName());
        }
        File xmlFile = parsedFile;
        MyArrayList<String> lines = new MyArrayList<>();
        MyArrayList<TagLine> tags = new MyArrayList<>();
        
        MyStack<TagLine> tagsStack = new MyStack<>();
        MyQueue<TagLine> errorQ = new MyQueue<>();
        MyQueue<TagLine> extrasQ = new MyQueue<>();
        
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
        
        System.out.println("========== ERROR LOG ==========");

        // Iterate through each line of the XML file
        tags = getTags(lines);
        
        for (int index = 0; index < tags.size(); index++) {
        	TagLine currentTag = tags.get(index);
        	
        	if (currentTag.getTag().endsWith("/")) {
        		continue;
        	} else if (!currentTag.getTag().startsWith("/")) {
        		tagsStack.push(currentTag);
        	}
        	else {
        		currentTag.setTag(currentTag.getTag().substring(1));
        		
        		// IF MATCHES TOP OF STACK
        		if (tagsStack.peek().getTag().equals(currentTag.getTag())) {
        			tagsStack.pop();
        		}
        		
        		// MATCHES HEAD OF ERRORQ
        		else if (!errorQ.isEmpty() && errorQ.peek().getTag().equals(currentTag.getTag())) {
        			System.out.println(errorQ.dequeue());
        		}
        		
        		// IF STACK IS EMPTY
        		else if (tagsStack.isEmpty()) {
        			errorQ.enqueue(currentTag);
        		}
        		
        		else {
        			
        			boolean foundMatch = false;
        		    MyStack<TagLine> tempStack = new MyStack<>();

        		    while (!tagsStack.isEmpty()) {
        		        TagLine topTag = tagsStack.pop();
        		        
        		        // Check if the tag matches the current one
        		        if (topTag.getTag().equals(currentTag.getTag())) {
        		            foundMatch = true;
        		            break; // Stop once a match is found
        		        } else {
        		            tempStack.push(topTag); // Save unmatched tags
        		        }
        		    }

        		    // Restore the original stack from the temporary stack
        		    while (!tempStack.isEmpty()) {
        		        tagsStack.push(tempStack.pop());
        		    }

        		    if (!foundMatch) {
        		        extrasQ.enqueue(currentTag); // Add unmatched end tag to extrasQ
        		    }
        			
        		}
        	}
        }
       
  
        // IF STACK IS NOT EMPTY 
        while (!tagsStack.isEmpty()) {
            TagLine errorTag = tagsStack.pop();
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
                    TagLine errorElement = extrasQ.dequeue();  // Dequeue the element from extrasQ
                    System.out.println("Error: " + errorElement.getTag());  // Report as error
                }
            } else if (extrasQ.isEmpty()) {
                // Report all elements in errorQ as errors
                while (!errorQ.isEmpty()) {
                	TagLine errorElement = errorQ.dequeue();  // Dequeue the element from errorQ
                    System.out.println("Error: " + errorElement.getTag());  // Report as error
                }
            }
        }
        
        while (!errorQ.isEmpty() && !extrasQ.isEmpty()) {
            if (errorQ.peek().equals(extrasQ.peek())) {
                errorQ.dequeue();
                extrasQ.dequeue();
            } else {
            	TagLine errorTag = errorQ.dequeue();
                System.out.println("Error: Mismatched tag <" + errorTag.getTag() + ">" + " Line: " + errorTag.getLineNumber());
            }
        }

        // Report remaining items in errorQ
        while (!errorQ.isEmpty()) {
        	TagLine errorTag = errorQ.dequeue();
            System.out.println("Error: Unmatched tag <" + errorTag.getTag() + ">" + " Line: " + errorTag.getLineNumber());
        }

        // Report remaining items in extrasQ
        while (!extrasQ.isEmpty()) {
        	TagLine extraTag = extrasQ.dequeue();
            System.out.println("Error: Unmatched end tag </" + extraTag.getTag() + ">"+ " Line: " + extraTag.getLineNumber());
        }
        
        
        
   }
    
    private static MyArrayList<TagLine> getTags (MyArrayList<String> lines) {
    	MyArrayList<TagLine> tags = new MyArrayList<>();
    	
    	for (int index = 0; index < lines.size(); index++) {
            String currentLine = lines.get(index);  
            String trimmedLine = currentLine.trim();
            MyStack <TagLine> lineTags = new MyStack<>();
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
                    
                    
                    
                    TagLine tagName = new TagLine(tagNameBuilder.toString(), index + 2);
                    
                    if (trimmedLine.charAt(trimmedLine.length() - 2) == '/') {
                    	tagName.setTag(tagName.getTag() + '/');
                    }
                    
                    lineTags.push(tagName);
                }
                else if (currentChar == '>') {
                	
                	if (lineTags.isEmpty()) {
                		System.out.println("Error: Extra closing tag '>'" + "Line: " + (index + 2));
                	} else {
                		tags.add(lineTags.pop());
                	}
                }
              
                
                charIndex++;
            }
            
            while (!lineTags.isEmpty()) {
            	tags.add(lineTags.pop());
            }
        }
    	
    	return tags;
    }


}

