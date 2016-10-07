package nennes.maze;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    // Return a list of filenames in a directory
    public static List<File> getFileList(String path){
        List<File> results = new ArrayList();

        //If this pathname does not denote a directory, then listFiles() returns null.
        File[] files = new File(path).listFiles();

        // Ignore any directories
        for (File file : files) {
            if (file.isFile()) {
                results.add(file);
            }
        }

        return results;
    }

    // Convert a String to an Integer
    public static int stringToInt(String input) {
        try {
            return Integer.valueOf(input);
        } catch(NumberFormatException e) {
            return -1; // Anything not corresponding to a file number will be treated as invalid input
        }
    }

}
