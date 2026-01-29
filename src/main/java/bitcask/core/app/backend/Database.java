package bitcask.core.app.backend;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;

public class Database {
    
    private static Hashtable<String, String> table = new Hashtable<>();
    private final static String data = "data/bitcask.0";
    
    public Database() {}

    public static void add(String key, String value) {
        table.put(key, value);
        writeToFile();
    }

    public static Optional<String> get(String key) {
        String value = table.get(key);
        if (value == null) {
            return Optional.empty();
        }
        writeToFile();
        return Optional.of(value);
    }
    
    public static void delete(String key) {
        table.remove(key);
        writeToFile();
    }

    public static void writeToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(data));
            
            for(Map.Entry<String, String> entry : table.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                writer.write(key + ":" + value + "\n");
            }

            writer.close();
        }catch (IOException e) {
            System.out.println("Failed to write to file: " + e.toString());
        }
    }

    public static void rebuild() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(data));
            
            String line;
            while((line = reader.readLine()) != null) {
                String[] entry = line.split(":"); 
                String key = entry[0];
                String value = entry[1];
                table.put(key, value);
            }

        }catch (IOException e) {
            System.out.println("Failed to rebuild table: " + e.toString());
        }
    }
}
