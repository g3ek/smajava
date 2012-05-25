/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.geek.smajava;

/**
 *
 * @author geek
 */
public class ByteResult {
    
    private boolean terminated = false;
    
    private byte[] result;

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
    
    
}
