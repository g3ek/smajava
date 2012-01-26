/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.geek.smajava;

/**
 *
 * @author geek
 */
public class InvertersData {
 
//Set a value depending on inverter
    public static byte[] getInverterType(String code) {
        byte[] invertercode = new byte[4];
        if (code.equals("1700TL")) {
            invertercode[0] = 0x12;
            invertercode[1] = 0x1a;
            invertercode[2] = (byte) 0xd9;
            invertercode[3] = 0x38;
            //conf->ArchiveCode    = 0x63;
        }
        if (code.equals("2100TL")) {
            invertercode[0] = 0x17;
            invertercode[1] = (byte) 0x97;
            invertercode[2] = 0x51;
            invertercode[3] = 0x38;
            //conf->ArchiveCode    = 0x63;
        }
        if (code.equals("3000TL")) {
            invertercode[0] = 0x12;
            invertercode[1] = 0x1a;
            invertercode[2] = (byte) 0xd9;
            invertercode[3] = 0x38;
            invertercode[0] = 0x32;
            invertercode[1] = 0x42;
            invertercode[2] = (byte) 0x85;
            invertercode[3] = 0x38;
            //conf->ArchiveCode    = 0x71;
        }
        if (code.equals("3000TLHF")) {
            invertercode[0] = 0x1b;
            invertercode[1] = (byte) 0xb1;
            invertercode[2] = (byte) 0xa6;
            invertercode[3] = 0x38;
            //conf->ArchiveCode    = 0x83;
        }
        if (code.equals("4000TL")) {
            invertercode[0] = 0x78;
            invertercode[1] = 0x21;
            invertercode[2] = (byte) 0xbf;
            invertercode[3] = 0x3a;
            //conf->ArchiveCode    = 0x4e;
        }
        if (code.equals("5000TL")) {
            invertercode[0] = 0x3f;
            invertercode[1] = 0x10;
            invertercode[2] = (byte) 0xfb;
            invertercode[3] = 0x39;
            //conf->ArchiveCode     = 0x4e;
        }
        if (code.equals("7000")) {
            invertercode[0] = (byte) 0xcf;
            invertercode[1] = (byte) 0x84;
            invertercode[2] = (byte) 0x84;
            invertercode[3] = 0x3a;
            //conf->ArchiveCode     = 0x63;
        }
        if (code.equals("10000TL")) {
            invertercode[0] = 0x69;
            invertercode[1] = 0x45;
            invertercode[2] = 0x32;
            invertercode[3] = 0x39;
            //conf->ArchiveCode     = 0x80;
        }
        if (code.equals("XXXXTL")) {
            invertercode[0] = (byte) 0x99;
            invertercode[1] = 0x35;
            invertercode[2] = 0x40;
            invertercode[3] = 0x36;
            //conf->ArchiveCode     = 0x4e;
        }
        return invertercode;
    }
    
    public static byte getArchiveCode(String code) {
        byte result = 0;
        if (code.equals("1700TL")) {
            result = 0x63;
        }
        if (code.equals("2100TL")) {
            result = 0x63;
        }
        if (code.equals("3000TL")) {
            result = 0x71;
        }
        if (code.equals("3000TLHF")) {
            result = (byte) 0x83;
        }
        if (code.equals("4000TL")) {
            result  = 0x4e;
        }
        if (code.equals("5000TL")) {
            result   = 0x4e;
        }
        if (code.equals("7000")) {
            result    = 0x63;
        }
        if (code.equals("10000TL")) {
            result  = (byte) 0x80;
        }
        if (code.equals("XXXXTL")) {
            result   = 0x4e;
        }
        return result;        
    }    
}
