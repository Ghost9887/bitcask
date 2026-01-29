package bitcask.core.app.backend;

import java.util.Hashtable;
import java.util.Optional;

public class Database {
    
    private static Hashtable<String, String> table = new Hashtable<>();
    
    public Database() {}

    public static void add(String key, String value) {
        table.put(key, value);
    }

    public static Optional<String> get(String key) {
        String value = table.get(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }
    
    public static void delete(String key) {
        table.remove(key);
    }
}
