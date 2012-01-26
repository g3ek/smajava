/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.geek.smajava;

import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author geek
 */
public class Util {
    
    public static byte encodeHexString(String hexCode) {
        return DatatypeConverter.parseHexBinary(hexCode)[0];
    }
    
    public static byte[] addToBuffer(byte[] dest, byte[] toAdd, int cntr) {
        for (int i = 0; i < toAdd.length && i < dest.length; i++) {
            dest[cntr] = toAdd[i];
            cntr++;
        }
        return dest;
    }  
    
    public static byte[] fix_length_received(byte[] received, int len) {
        if (received[1] != len) {
            if ((received[3] != 0x13) && (received[3] != 0x14)) {
                int sum = received[1] + received[3];
                received[1] = (byte) (len);
                switch (received[1]) {
                    case 0x52:
                        received[3] = 0x2c;
                        break;
                    case 0x5a:
                        received[3] = 0x24;
                        break;
                    case 0x66:
                        received[3] = 0x1a;
                        break;
                    case 0x6a:
                        received[3] = 0x14;
                        break;
                    default:
                        received[3] = (byte) (sum - received[1]);
                        break;
                }
            }
        }
        return received;
    }   
    
    /*
     * Recalculate and update length to correct for escapes
     */
    public static byte[] fix_length_send(byte[] cp, int len) {
        int delta = 0;

        if ((cp[1] != (len) + 1)) {
            delta = (len) + 1 - cp[1];
            cp[3] = (byte) ((cp[1] + cp[3]) - ((len) + 1));
            cp[1] = (byte) ((len) + 1);

            switch (cp[1]) {
                case 0x3a:
                    cp[3] = 0x44;
                    break;
                case 0x3b:
                    cp[3] = 0x43;
                    break;
                case 0x3c:
                    cp[3] = 0x42;
                    break;
                case 0x3d:
                    cp[3] = 0x41;
                    break;
                case 0x3e:
                    cp[3] = 0x40;
                    break;
                case 0x3f:
                    cp[3] = 0x41;
                    break;
                case 0x40:
                    cp[3] = 0x3e;
                    break;
                case 0x41:
                    cp[3] = 0x3f;
                    break;
                case 0x42:
                    cp[3] = 0x3c;
                    break;
                case 0x52:
                    cp[3] = 0x2c;
                    break;
                case 0x53:
                    cp[3] = 0x2b;
                    break;
                case 0x54:
                    cp[3] = 0x2a;
                    break;
                case 0x55:
                    cp[3] = 0x29;
                    break;
                case 0x56:
                    cp[3] = 0x28;
                    break;
                case 0x57:
                    cp[3] = 0x27;
                    break;
                case 0x58:
                    cp[3] = 0x26;
                    break;
                case 0x59:
                    cp[3] = 0x25;
                    break;
                case 0x5a:
                    cp[3] = 0x24;
                    break;
                case 0x5b:
                    cp[3] = 0x23;
                    break;
                case 0x5c:
                    cp[3] = 0x22;
                    break;
                case 0x5d:
                    cp[3] = 0x23;
                    break;
                case 0x5e:
                    cp[3] = 0x20;
                    break;
                case 0x5f:
                    cp[3] = 0x21;
                    break;
                case 0x60:
                    cp[3] = 0x1e;
                    break;
                case 0x61:
                    cp[3] = 0x1f;
                    break;
                case 0x62:
                    cp[3] = 0x1e;
                    break;
                default:
                    break;
            }
        }
        return cp;
    }    
    
    /*
     * Add escapes (7D) as they are required
     */
    public static Object[] add_escapes(byte[] bytes, int len) {
        int i, j;

        for (i = 19; i < (len); i++) {
            switch (bytes[i]) {
                case 0x7d:
                case 0x7e:
                case 0x11:
                case 0x12:
                case 0x13:
                    for (j = len; j > i; j--) {
                        bytes[j] = bytes[j - 1];
                    }
                    bytes[i + 1] = (byte) (bytes[i] ^ 0x20);
                    bytes[i] = 0x7d;
                    len++;
            }
        }
        return new Object[]{bytes, len};
    }    
    
//    public static long convertToTime(byte[] bytes, int start, int length) {
//        long result=0;
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("0x");
//        boolean nullvalue = false;
//        for(int i=start+(length-1); i >= start; i--) {
//            if (bytes[i] == 0xff) {
//                nullvalue = true;                
//                break;
//            }
//            stringBuffer.append(String.format("%02x", bytes[i]));
//        }
//        if (!nullvalue) {
//            //System.out.println("hex totime: "+stringBuffer.toString());
//            result = Long.decode(stringBuffer.toString());
//        }
//        return result;
//    }    
    
    public static long convertToValue(byte[] bytes, int start, int length) {
        long result = 0;
        byte[] data = Arrays.copyOfRange(bytes, start, start+length);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("0x");        
        boolean nullvalue = false;
        for(int i=data.length-1; i >= 0; i--) {
            if (data[i] == 0xff) {
                nullvalue = true;                
                break;
            }            
            stringBuffer.append(String.format("%02x", data[i]));
        }
        if (!nullvalue) {
            //System.out.println("hex totime: "+stringBuffer.toString());
            result =  Long.decode(stringBuffer.toString());
            //System.out.println("result: "+result);
        }
        return result;
    }    
}
