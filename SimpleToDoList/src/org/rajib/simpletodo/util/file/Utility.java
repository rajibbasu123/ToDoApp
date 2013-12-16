package org.rajib.simpletodo.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Utility {

	private static final boolean MODE_APPEND = true;
	private static BufferedReader buffreader;

	/**
	 * This method reads data from the given file and returns a list.
	 * 
	 * @param fileName
	 * @return
	 */
	public static ArrayList<String> readLines(File fileName)
			throws FileNotFoundException {

		ArrayList<String> lvItems = new ArrayList<String>();

		// check if given file exists
		if (fileName.exists()) {

			try {

				InputStream instream = new FileInputStream(fileName);

				if (instream != null) {

					// prepare the file for reading
					InputStreamReader inputreader = new InputStreamReader(
							instream);
					buffreader = new BufferedReader(inputreader);

					String line = null;

					while ((line = buffreader.readLine()) != null) {
						// buffered reader reads only one line at a time, hence
						// we give a while loop to read all till the text is
						// null

						lvItems.add(line);
					}
				}

			} catch (IOException ioex) {
				System.out.println("Exception :: " + ioex.getMessage());
				// Throw this exception to the caller
			}
		} else {
			throw new FileNotFoundException("File does not Exist");
		}
		return lvItems;
	}

	/**
	 * This method appends the given text to the given file.
	 * 
	 * @param todo
	 * @param text
	 */
	public static void writeLines(File todo, String text) {
		// Check if data is not null
		if (text != null) {
			try {
				// check if the file exist, else create a new
				if (!todo.exists()) {
					todo.createNewFile();
					// todo.setWritable(true);
				}

				OutputStreamWriter out = new OutputStreamWriter(
						new FileOutputStream(todo, MODE_APPEND));

				out.write(text);
				out.write('\n');
				out.close();
			} catch (IOException ioe) {
				System.out.println("Exception in File Utils :: "
						+ ioe.getMessage());
				// Throw this exception to the caller
			}
		}
	}

	/**
	 * Deletes the file from the disk.
	 * 
	 * @param todoFile
	 */
	public static void deleteLines(File todoFile) throws FileNotFoundException {
		// delete the file
		if (todoFile.exists()) {
			todoFile.delete();
		} else {
			throw new FileNotFoundException("File does not Exist");
		}

	}

}
