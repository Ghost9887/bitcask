package bitcask.core.app.backend;

import java.nio.ByteBuffer;

public final class LogData {


    private final String divisor = "100000100110000010001110110110111";

    private final long time = System.nanoTime(); 
    private int keySize;
    private int valueSize;
    private final String key; 
    private final String value;

    private String bits;

    public LogData(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public String calculateCRC(String bits) {
        //append the zeros;
        StringBuilder str = new StringBuilder(bits);
        str.append("0".repeat(divisor.length() - 1));

        //starting set of bits
        StringBuilder res = new StringBuilder(bits.substring(0, divisor.length()));
        int index = divisor.length() - 1;

        while (true) {
            StringBuilder temp = new StringBuilder();
            //System.out.println(res);
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
        //System.out.println(res);

        return res.toString();
    }

    public void convertToBinary() {
        StringBuilder temp = new StringBuilder();
        
        temp.append(Long.toBinaryString(time));

        String keyBin = strToBinary(key);
        keySize = keyBin.length();

        String valueBin = strToBinary(value);
        valueSize = valueBin.length();

        temp.append(Integer.toBinaryString(keySize));
        temp.append(Integer.toBinaryString(valueSize));
        temp.append(keyBin);
        temp.append(valueBin);
        
        StringBuilder res = new StringBuilder();
        res.append(calculateCRC(temp.toString()));
        res.append(temp);

        //System.out.println(res);
        bits = res.toString();
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
        return bits;
    }
}
