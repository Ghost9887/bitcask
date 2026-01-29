package bitcask.core.app.backend;

import java.nio.ByteBuffer;

public final class LogData {

    private final long time = System.nanoTime(); 
    private final int keySize;
    private final int valueSize;
    private final String key; 
    private final String value;

    private String bytes;

    public LogData(int keySize, int valueSize, String key, String value) {
        this.keySize = keySize;
        this.valueSize = valueSize;
        this.key = key;
        this.value = value;
    }

    public void convertToBinary() {
        StringBuilder str = new StringBuilder();
        
        str.append(Long.toBinaryString(time));
        str.append(Integer.toBinaryString(keySize));
        str.append(Integer.toBinaryString(valueSize));
        str.append(strToBinary(key));
        str.append(strToBinary(value));

        bytes = str.toString();
    }

    public String strToBinary(String data) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            int ascii = (int) data.charAt(i);
            str.append(Integer.toBinaryString(ascii));
        }

        return str.toString();
    }

    public String getBits() {
        return bytes;
    }
}
