package bitcask.core.app.backend;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class LogData {

    private final long time = System.nanoTime(); 
    private int keySize;
    private int valueSize;
    private final String key; 
    private final String value;

    private byte[] bytes;

    public LogData(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public void convertToBytes() {
    try {
        ByteArrayOutputStream payloadOut = new ByteArrayOutputStream();
        DataOutputStream payload = new DataOutputStream(payloadOut);

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);

        payload.writeLong(time);
        payload.writeInt(keyBytes.length);
        payload.writeInt(valueBytes.length);
        payload.write(keyBytes);
        payload.write(valueBytes);
        payload.flush();

        byte[] payloadBytes = payloadOut.toByteArray();

        CRC32 crc = new CRC32();
        crc.update(payloadBytes);
        long crcValue = crc.getValue();

        ByteArrayOutputStream finalOut = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(finalOut);

        data.writeLong(crcValue);
        data.write(payloadBytes);
        data.flush();

        bytes = finalOut.toByteArray();

    } catch (IOException e) {
        System.out.println("Failed to convert to bytes: " + e);
    }
}

    public byte[] getBytes() {
        return bytes;
    }
}
