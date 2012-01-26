package be.geek.smajava;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 * Read and write to the inverter.
 */
public class Inverter {
    
    /**
     * RFCOMM url
     */
    private static final String URLTemplate = "btspp://%s:1;authenticate=false;encrypt=false;master=false";
    
    /**
     * Type name 
     */
    private String code;
    
    /**
     * mac address
     */
    private String address;
    
    private InputStream inputStream;
    private OutputStream outputStream;
    
    /**
     * extracted inverter code
     */
    private byte inverterCode;
    
    /**
     * extracted second address
     */
    private byte[] secondAddress;
    
    private String password;

    /**
     * extracted serial
     */
    private byte[] serial;
    

    public Inverter(String code, String address) {
        this.address = address;
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public byte getInverterCode() {
        return inverterCode;
    }

    public void setInverterCode(byte inverterCode) {
        this.inverterCode = inverterCode;
    }

    public String getCode() {
        return this.code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getSerial() {
        return serial;
    }

    public void setSerial(byte[] serial) {
        this.serial = serial;
    }
    
    
    /**
     * Split address string into 6 bytes, reverse order
     * @return 
     */
    public byte[] convertAddress() {
        String[] codes = address.split("(?<=\\G..)");
        byte[] result = new byte[codes.length];
        int cntr = 0;
        for (int i = codes.length - 1; i >= 0; i--) {
            result[cntr] = Util.encodeHexString(codes[i]);
            cntr++;
        }
        return result;
    }    
    
    public String getConnectionURL() {
        return URLTemplate.replaceFirst("\\%s", address);
    }

    public void openConnection() throws IOException {
        StreamConnection connection = (StreamConnection) Connector.open(getConnectionURL());        
        inputStream = connection.openInputStream();
        outputStream = connection.openOutputStream();
    }
    
    public void closeConnection() throws IOException {
        this.inputStream.close();
        this.outputStream.close();
        inputStream = null;
        outputStream = null;
    }
    
    public void send(byte[] data) throws IOException {
        Log.debugBytes(this, "Sending data: ", data);
        outputStream.write(data);
        outputStream.flush();        
    }
    
    public byte[] receive() throws IOException {
        int read = 0;

        byte[] result;
        
        // header is always 3 bytes
        byte[] header = new byte[3];
        read = inputStream.read(header);
        int contentlength = (new Byte(header[1])).intValue();
        Log.debug(this, "Content length is " + contentlength + " bytes.");
        
        // construct byte array to receive data in
        result = new byte[contentlength];
        result[0] = header[0];
        result[1] = header[1];
        result[2] = header[2];
        Log.debug(this, "Receiving...");
        read = inputStream.read(result, 3, contentlength - 3);
        read+=3;                
        Log.debug(this, "Read "+read+" bytes.");
        Log.debugBytes(this, "received: ", result);
                        
        byte[] tempBuffer = new byte[read];
        int cntr=0;
        for (int i = 0; i < read; i++) {
            if (result[i] == 0x7d) { //did we receive the escape char
                switch (result[i + 1]) {   // act depending on the char after the escape char

                    case 0x5e:
                        tempBuffer[cntr] = 0x7e;
                        break;

                    case 0x5d:
                        tempBuffer[cntr] = 0x7d;
                        break;

                    default:
                        tempBuffer[cntr] = (byte) (result[i + 1] ^ 0x20);
                        break;
                }
                i++;
            } else {
                tempBuffer[cntr] = result[i];
            }
            cntr++;
        }
        tempBuffer = Util.addToBuffer(new byte[cntr], tempBuffer, 0);
        return Util.fix_length_received(tempBuffer, cntr);
    }

    public void setSecondAddress(byte[] address2) {
        this.secondAddress = address2;
    }

    byte[] getSecondAddress() {
        return secondAddress;
    }
    
}
