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
    
    //TODO: refactor to make it work here
    public static void calculateCRC(String bits, String divisor) {
        //append the zeros;
        StringBuilder str = new StringBuilder(bits);
        str.append("0".repeat(divisor.length() - 1));
        System.out.println(str);

        //starting set of bits
        StringBuilder res = new StringBuilder(bits.substring(0, divisor.length()));
        int index = divisor.length() - 1;

        while (index <= str.length()) {
            StringBuilder temp = new StringBuilder();
            if (res.charAt(0) == '0') {
                temp.append(res.substring(1, res.length()));
            }
            else {
                for (int i = 1; i < res.length(); i++) {
                    if (res.charAt(i) == divisor.charAt(i)) {
                        temp.append('0');
                    }
                    else {
                        temp.append('1');
                    }
                }
            }
            res = temp;
            index += 1;
            if (index >= str.length()) break;
            res.append(str.charAt(index));
        }
        System.out.println(res);
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
