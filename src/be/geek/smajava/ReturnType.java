/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.geek.smajava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author geek
 */
public class ReturnType {
    
    private int key1;
    private int key2;
    private String description;
    private String units;
    private float divisor;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDivisor() {
        return divisor;
    }

    public void setDivisor(float divisor) {
        this.divisor = divisor;
    }

    public int getKey1() {
        return key1;
    }

    public void setKey1(int key1) {
        this.key1 = key1;
    }

    public int getKey2() {
        return key2;
    }

    public void setKey2(int key2) {
        this.key2 = key2;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }    
    
    public static Collection<ReturnType> load() throws IOException {
        Collection<ReturnType> result = new ArrayList<ReturnType>();
        
        InputStream inputStream = ReturnType.class.getResourceAsStream("/sma.in.new");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line = null;
        boolean readStart = false;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith(":unit conversions")) {
                readStart = true;
                continue;
            }
            if (line.startsWith(":end unit conversions")) {
                readStart = false;
            }
            if (readStart) {
                ReturnType returnType = new ReturnType();
                // regex to split line into words, while not splitting quotes
                Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
                Matcher regexMatcher = regex.matcher(line);
                regexMatcher.find();
                //System.out.println("words: "+regexMatcher.groupCount());                
                returnType.setKey1( Util.encodeHexString(regexMatcher.group()) );
                regexMatcher.find();
                returnType.setKey2( Util.encodeHexString(regexMatcher.group()) );
                regexMatcher.find();
                final String group = regexMatcher.group();
                returnType.setDescription(group.substring(1, group.length()-1));                
                regexMatcher.find();
                returnType.setUnits(regexMatcher.group());
                regexMatcher.find();
                returnType.setDivisor(Float.parseFloat(regexMatcher.group()));
                
                result.add(returnType);
            }
        }        
        bufferedReader.close();
        Log.debug(ReturnType.class, "Found "+result.size());
        return result;
        
    }    
}
