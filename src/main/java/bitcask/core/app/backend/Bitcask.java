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
            logData.convertToBytes();

            BufferedWriter writer = new BufferedWriter(new FileWriter(log, true));
            byte[] bytes = logData.getBytes();
            for (byte b : bytes) {
                writer.write(b);
            }
            writer.newLine();
            writer.close();
        }catch (IOException e) {
            System.out.println("Failed to write to file: " + e.toString());
        }
    }

}
