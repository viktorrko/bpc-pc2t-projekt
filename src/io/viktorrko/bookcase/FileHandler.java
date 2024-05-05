package io.viktorrko.bookcase;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FileHandler {
	public static File createFile(String newPath) {
		Path path = Paths.get(newPath);
		
		if (Files.exists(path)) {
			System.out.println(String.format("(File %s already exists. Overwriting.)", path));
		}
		else {
			try {
				Files.createFile(path);
			}
			catch (IOException e) {
				return null;
			}
		}
		
		return path.toFile();
	}
	
	public static void createDirectory(String newPath) {
		Path path = Paths.get(newPath);
		
		try {
            Files.createDirectory(path);
        } catch (IOException e) {
            System.out.println("An error occurred while creating the directory: " + e.getMessage());
        }
	}
	
	public static boolean writeToFile(String s, File file) {
		try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(s);
        } catch (IOException e) {
            return false;
        }
		return true;
	}
	
	public static String readFromFile(Path path) {
		String line;
		try (BufferedReader reader = Files.newBufferedReader(path)) {
            
            
            line = reader.readLine();
        }
		catch (IOException e) {
            return null;
        }
		return line;
	}
	
	public static boolean validPath(String s) {
		Path path = Paths.get(s);
				
		if (path.toFile().exists())
			return true;
		
		return false;
	}
}
