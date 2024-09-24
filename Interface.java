/**
 * This class implements the user interface.
 * 
 * @author Ivory Huo 
 */

//removed the package
import java.io.*;

public class Interface {
	
	/**
	 * Entry point for the application.
	 * This method initializes the application, loads data into the dictionary from a specified file,
	 * and enters an interactive mode allowing for various commands to be executed until an 'exit' command is issued.
	 * 
	 * @param args Command-line arguments, expecting exactly one argument: the path to the input file.
	 */
	public static void main(String[] args) {
	    // Ensure exactly one command-line argument is provided. If not, display usage instructions and exit.
	    if (args.length != 1) {
	        System.out.println("Usage: java Interface inputFile");
	        return;
	    }
	    
	    // Initialize the dictionary where records will be stored.
	    String inputFile = args[0];
	    BSTDictionary dictionary = new BSTDictionary();
	    
	    // Attempt to open and read from the input file.
	    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
	        String label; // To hold the label part of the record.
	        
	        // Loop through each line of the file, reading labels and their corresponding data.
	        while ((label = reader.readLine()) != null) {
	            // Normalize label to lower case for consistent handling.
	            label = label.toLowerCase();
	            String data = reader.readLine(); // Read the data associated with the label.
	            
	            // Create a record from the label and data, and add it to the dictionary.
	            Record record = createRecord(label, data);
	            dictionary.put(record);
	        }

	    } catch (IOException e) {
	        // Handle potential I/O exceptions from reading the file.
	        System.out.println("Error reading file: " + e.getMessage());
	        return;
	    } catch (DictionaryException e) {
	        // Handle exceptions related to dictionary operations.
	        System.out.println("Dictionary error: " + e.getMessage());
	        return;
	    }
	    
	    // Interactive command loop for processing user commands.
	    StringReader keyboard = new StringReader();
	    String line; // To hold user input.
	    
	    // Continuously prompt the user for commands until 'exit' is entered.
	    while (true) {
	        line = keyboard.read("Enter next command: ").trim(); // Read and trim the command.
	        
	        if (line.equals("exit")) {
	            // Exit the loop if the command is 'exit'.
	            break;
	        }
	        
	        // Process the command entered by the user.
	        command(line, dictionary);
	    }
	}

	/**
	 * Creates a Record object by associating the provided label and type-specific data.
	 * This method interprets the prefix or suffix of the typeData to determine the type of the record
	 * (e.g., sound file, music file, voice file, etc.) and formats the data accordingly before storing.
	 * 
	 * @param label The label for the record, representing the key under which the data is stored.
	 * @param typeData The raw data string that may start with a specific character or end with a specific extension
	 *                 indicating its type (e.g., sound, music, voice, translation, image, animated image, URL).
	 * @return A new Record object with the formatted data and a key generated from the label and data type.
	 */
	private static Record createRecord(String label, String typeData) {
	    int type; // Variable to store the type of the record based on the typeData's prefix/suffix.
	    String data; // Variable to store the formatted data after removing type indicators.
	    
	    // Determine the type of the record based on specific indicators in typeData.
	    if (typeData.startsWith("-")) {
	        type = 3; // Indicates a sound file.
	        data = typeData.substring(1); // Remove the indicator before storing.
	    } else if (typeData.startsWith("+")) {
	        type = 4; // Indicates a music file.
	        data = typeData.substring(1); // Remove the indicator before storing.
	    } else if (typeData.startsWith("*")) {
	        type = 5; // Indicates a voice file.
	        data = typeData.substring(1); // Remove the indicator before storing.
	    } else if (typeData.startsWith("/")) {
	        type = 2; // Indicates a French translation.
	        data = typeData.substring(1); // Remove the indicator before storing.
	    } else if (typeData.endsWith(".gif")) {
	        type = 7; // Indicates an animated image file.
	        data = typeData; // No need to remove any indicator.
	    } else if (typeData.endsWith(".jpg")) {
	        type = 6; // Indicates an image file.
	        data = typeData; // No need to remove any indicator.
	    } else if (typeData.endsWith(".html")) {
	        type = 8; // Indicates a URL.
	        data = typeData; // No need to remove any indicator.
	    } else {
	        type = 1; // Default type, indicates a definition.
	        data = typeData; // No need to remove any indicator.
	    }
	    
	    // Create and return a new Record object with the determined type and formatted data.
	    return new Record(new Key(label, type), data);
	}
	
	/**
	 * Processes user commands by interpreting the input and invoking the corresponding method in the BSTDictionary.
	 * The method splits the input command into parts to identify the action, target word, type, and additional data if any.
	 * It then executes the corresponding dictionary operation or media action based on the command.
	 *
	 * @param command   The full user command input as a String.
	 * @param dictionary The BSTDictionary instance where records are stored and managed.
	 */
	private static void command(String command, BSTDictionary dictionary) {
	    // Split the command into its constituent parts for analysis.
	    String[] parts = command.split(" ");
	    String cmd = parts[0]; // The primary command indicating the desired action.
	    String word = parts.length > 1 ? parts[1] : ""; // The target word for the command, if applicable.
	    int type = -1; // The type of the media or translation, initialized to an invalid value.
	    String data = ""; // Additional data required for some commands, like 'add'.
	    
	    // Determine if a type is specified for commands that require it (e.g., delete).
	    if (parts.length >= 3) {
	        try {
	            type = Integer.parseInt(parts[2]);
	        } catch (NumberFormatException e) {
	            System.out.println("Error: Type must be an integer.");
	            return;
	        }
	    }
	    // For commands that include data, capture the data part of the command.
	    if (parts.length >= 4) {
	        data = parts[3];
	    }
	    
	    // Process the command using a switch statement to match the primary command part.
	    switch (cmd) {
	        case "define":
	            defineOrTranslate(word, dictionary, 1, "The word " + word + " is not in the dictionary");
	            break;
	        case "translate":
	            defineOrTranslate(word, dictionary, 2, "There is no definition for the word " + word);
	            break;
	        case "sound":
	            playMedia(word, dictionary, 3, "There is no sound file for " + word);
	            break;
	        case "play":
	            playMedia(word, dictionary, 4, "There is no music file for " + word);
	            break;
	        case "say":
	            playMedia(word, dictionary, 5, "There is no voice file for " + word);
	            break;
	        case "show":
	            showMedia(word, dictionary, 6, "There is no image file for " + word);
	            break;
	        case "animate":
	            showMedia(word, dictionary, 7, "There is no animated image file for " + word);
	            break;
	        case "browse":
	            showMedia(word, dictionary, 8, "There is no webpage called " + word);
	            break;
	        case "delete":
	            delete(word, type, dictionary);
	            break;
	        case "add":
	            add(word, type, data, dictionary);
	            break;
	        case "list":
	            list(word, dictionary);
	            break;
	        case "first":
	            first(dictionary);
	            break;
	        case "last":
	            last(dictionary);
	            break;
	        default:
	            System.out.println("Invalid command.");
	            break;
	    }
	}

	/**
	 * Attempts to define a word or translate it based on the provided type.
	 * This method retrieves a record from the dictionary using the given word and type.
	 * If the record exists, it displays the associated data; otherwise, it shows an error message.
	 * 
	 * @param word The word to define or translate.
	 * @param dictionary The BSTDictionary from which to retrieve the record.
	 * @param type The type of data to retrieve (e.g., definition, translation).
	 * @param errorMessage The error message to display if the record does not exist.
	 */
	private static void defineOrTranslate(String word, BSTDictionary dictionary, int type, String errorMessage) {
	    // Attempt to retrieve the record associated with the given word and type.
	    Record record = dictionary.get(new Key(word, type));
	    
	    // Check if the record exists and display the data or an error message accordingly.
	    if (record != null) {
	        // Record found - display its data.
	        System.out.println(record.getDataItem());
	    } else {
	        // Record not found - display the specified error message.
	        System.out.println(errorMessage);
	    }
	}
	
	/**
	 * Plays media associated with a word if available.
	 * This method looks up a record by word and type, attempting to play the associated media file.
	 * If the media file is found, it is played using a SoundPlayer; otherwise, an error message is displayed.
	 * 
	 * @param word The word associated with the media file to play.
	 * @param dictionary The BSTDictionary from which to retrieve the media file.
	 * @param type The type of media to play (e.g., sound, music, voice).
	 * @param errorMessage The error message to display if no associated media file is found.
	 */
	private static void playMedia(String word, BSTDictionary dictionary, int type, String errorMessage) {
	    // Attempt to retrieve the record associated with the given word and media type.
	    Record record = dictionary.get(new Key(word, type));
	    
	    // Check if the media file exists.
	    if (record != null) {
	        // Initialize a SoundPlayer to play the media file.
	        SoundPlayer soundPlayer = new SoundPlayer();
	        try {
	            // Attempt to play the media file.
	            soundPlayer.play(record.getDataItem());
	        } catch (MultimediaException e) {
	            // An error occurred while playing the media - display the error message.
	            System.out.println("Error playing media: " + e.getMessage());
	        }
	    } else {
	        // No associated media file found - display the specified error message.
	        System.out.println(errorMessage);
	    }
	}
    
	/**
	 * Displays media (images, animated images, web pages) associated with a word, if available.
	 * This method retrieves a record by word and type and attempts to display the associated media.
	 * It handles different types of media, including static images, animated images, and webpages, using appropriate viewers.
	 * 
	 * @param word The word associated with the media to be displayed.
	 * @param dictionary The BSTDictionary from which to retrieve the media.
	 * @param type The type of media to display (image, animated image, or webpage).
	 * @param errorMessage The error message to display if no associated media is found.
	 */
	private static void showMedia(String word, BSTDictionary dictionary, int type, String errorMessage) {
	    // Attempt to retrieve the record associated with the given word and media type.
	    Record record = dictionary.get(new Key(word, type));
	    
	    // Check if the record exists.
	    if (record != null) {
	        try {
	            // Determine the type of media and use the appropriate viewer to display it.
	            if (type == 6) { // For static images.
	                PictureViewer pictureViewer = new PictureViewer();
	                pictureViewer.show(record.getDataItem()); // Display the image.
	            } else if (type == 7) { // For animated images.
	                PictureViewer animatedViewer = new PictureViewer();
	                animatedViewer.show(record.getDataItem()); // Display the animated image.
	            } else if (type == 8) { // For web pages.
	                ShowHTML showHTML = new ShowHTML();
	                showHTML.show(record.getDataItem()); // Display the webpage.
	            }
	        } catch (MultimediaException e) {
	            // An error occurred while displaying the media - print the error message.
	            System.out.println("Error displaying media: " + e.getMessage());
	        }
	    } else {
	        // No associated media found - display the specified error message.
	        System.out.println(errorMessage);
	    }
	}

	/**
	 * Deletes a record from the dictionary based on the given word and type.
	 * This method attempts to remove a record identified by a specific word and type.
	 * If the record does not exist, an error message is displayed to the user.
	 * 
	 * @param word The word that identifies the record to be deleted.
	 * @param type The type of the record to be deleted.
	 * @param dictionary The BSTDictionary from which to remove the record.
	 */
	private static void delete(String word, int type, BSTDictionary dictionary) {
	    try {
	        // Attempt to remove the record with the specified key from the dictionary.
	        Key key = new Key(word, type);
	        dictionary.remove(key);
	    } catch (DictionaryException e) {
	        // The record could not be found - display an error message indicating the issue.
	        System.out.println("No record in the ordered dictionary has key (" + word + "," + type + ").");
	    }
	}
    
	/**
	 * Adds a new record to the dictionary if the key does not already exist.
	 * This prevents duplicate keys in the dictionary, ensuring data integrity.
	 * 
	 * @param word The word associated with the record to add.
	 * @param type The type of the record to add.
	 * @param data The data of the new record.
	 * @param dictionary The BSTDictionary to which the new record will be added.
	 */
	private static void add(String word, int type, String data, BSTDictionary dictionary) {
	    try {
	        Key key = new Key(word, type); // Create a new key for the record.
	        
	        if (dictionary.get(key) == null) {
	            // If the key does not exist, proceed to add the new record.
	            Record record = new Record(key, data);
	            dictionary.put(record);
	        } else {
	            // If the key already exists, inform the user.
	            System.out.println("Skipping addition: A record with the given key (" + word + "," + type + ") already exists in the dictionary.");
	        }
	    } catch (DictionaryException e) {
	        // Display an error message if adding the record encounters issues.
	        System.out.println("Error adding record: " + e.getMessage());
	    }
	}


    /**
     * Lists all records from the ordered dictionary whose labels start with the specified prefix.
     * This method iterates over the dictionary, beginning with the smallest record and proceeding to each
     * successor, to find and print labels that match the given prefix. It ensures a comma-separated list
     * without a trailing comma for a clean presentation.
     * 
     * @param prefix The prefix to search for among the record labels.
     * @param dictionary The BSTDictionary instance containing the records to be searched.
     */
    private static void list(String prefix, BSTDictionary dictionary) {
        StringBuilder output = new StringBuilder(); // StringBuilder to construct the output string efficiently.
        boolean found = false; // Flag to track if any matching records have been found.

        // Begin with the smallest record in the dictionary for an ordered traversal.
        Record curr = dictionary.smallest();
        
        // Iterate through the dictionary to find all records that start with the given prefix.
        while (curr != null) {
            // Check if the current record's label starts with the prefix.
            if (curr.getKey().getLabel().startsWith(prefix)) {
                // If it's not the first match, append a comma for separation.
                if (found) {
                    output.append(", ");
                }
                // Append the label of the current record to the output.
                output.append(curr.getKey().getLabel());
                found = true; // Indicate that at least one matching record has been found.
            }
            // Move to the next record in order, ensuring an alphabetical listing.
            curr = dictionary.successor(curr.getKey());
        }
        
        // If any records were found, print the comma-separated list of labels.
        if (found) {
            System.out.println(output.toString());
        } else {
            // If no matching records were found, print a message indicating so.
            System.out.println("No label attributes in the dictionary start with prefix " + prefix);
        }
    }

    /**
     * Retrieves and displays the first (smallest key) record in the ordered dictionary.
     * This method finds the record with the smallest key based on the dictionary's ordering criteria
     * and prints its details, including the label, type, and associated data.
     * 
     * @param dictionary The BSTDictionary instance from which to retrieve the first record.
     */
    private static void first(BSTDictionary dictionary) {
        // Retrieve the record with the smallest key in the dictionary.
        Record small = dictionary.smallest();

        // Print the details of the smallest record: label, type, and data.
        System.out.println(small.getKey().getLabel() + "," + small.getKey().getType() + "," + small.getDataItem());
    }

    /**
     * Retrieves and displays the last (largest key) record in the ordered dictionary.
     * This method finds the record with the largest key based on the dictionary's ordering criteria
     * and prints its details, including the label, type, and associated data.
     * 
     * @param dictionary The BSTDictionary instance from which to retrieve the last record.
     */
    private static void last(BSTDictionary dictionary) {
        // Retrieve the record with the largest key in the dictionary.
        Record large = dictionary.largest();

        // Print the details of the largest record: label, type, and data.
        System.out.println(large.getKey().getLabel() + "," + large.getKey().getType() + "," + large.getDataItem());
    }

}