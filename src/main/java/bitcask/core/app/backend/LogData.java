package bitcask.core.app.backend;

import java.nio.ByteBuffer;

public final class LogData {

    private final long time = System.nanoTime(); 
    private final int keySize;
    private final int valueSize;
    private final String key; 
    private final String value;

    private byte[] bytes;

    public LogData(int keySize, int valueSize, String key, String value) {
        this.keySize = keySize;
        this.valueSize = valueSize;
        this.key = key;
        this.value = value;
    }

    public void convertToBytes() {
        int capacity = Long.BYTES + Integer.BYTES + key.getBytes().length + value.getBytes().length;
        ByteBuffer buff = ByteBuffer.allocate(capacity);
        
        buff.putLong(time);
        buff.putInt(keySize);
        buff.putInt(valueSize);
        buff.put(key.getBytes());
        buff.put(value.getBytes());

        bytes = buff.array();
    }

    public byte[] getBytes() {
        return bytes;
    }
}
