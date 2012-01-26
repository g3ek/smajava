/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.geek.smajava;

/**
 *
 * @author geek
 */
public class SmajavaException extends Exception {

    public enum Type {
        NORMAL,
        BLUETOOTH_RESET
    }
    
    private Type type;
    
    public SmajavaException(String string) {
        super(string);
        type = Type.NORMAL;
    }

    public SmajavaException(String message, Type type) {
        super(message);
        this.type = type;
    }
    
    public Type getType() {
        return this.type;
    }
    
}
