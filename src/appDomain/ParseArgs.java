package appDomain;

import java.io.File;

public class ParseArgs {
	private static File file = null;


    public static File getFile() {
        return file;
    }

    public static void parseArgs(String[] args) {
        for (String arg : args) {
            if (arg.endsWith(".xml")) { // Check if the argument is an XML file
                file = new File(arg);  // Use the argument directly as the file name
                if (!file.exists() || !file.isFile()) {
                    System.out.println("Invalid file: " + file);
                    file = null; // Reset file if invalid
                } else {
                    System.out.println("Valid file: " + file.getName());
                }
                break; // Stop checking further arguments after finding the file
            }
        }

        if (file == null) {
            System.out.println("No valid XML file provided.");
        }
    }
}
