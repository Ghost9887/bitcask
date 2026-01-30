package bitcask.core.app.backend;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;

public final class Bitcask {
    
    private static Hashtable<String, String> table = new Hashtable<>();
    private final static String data = "data/bitcask.0";
    private final static String log = "data/0.log";
    
    public Bitcask() {}

    public static void add(String key, String value) {
        table.put(key, value);
        writeToFile(key, value);
    }

    public static Optional<String> get(String key) {
        String value = table.get(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }
    
    public static void delete(String key) {
        //table.remove(key);
        //writeToFile();
    }

    public static void writeToFile(String key, String value) {
        try {
            LogData logData = new LogData(key, value);
            logData.convertToBinary();

            BufferedWriter writer = new BufferedWriter(new FileWriter(log, true));
            writer.write(logData.getBits());
            writer.newLine();
            writer.close();
        }catch (IOException e) {
            System.out.println("Failed to write to file: " + e.toString());
        }
    }

    /*
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
    */
}
