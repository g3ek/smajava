/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.geek.smajava;

/**
 *
 * @author geek
 */
public enum VarEnum {
    
    END("$END"),
    ADDR("$ADDR"),
    TIME("$TIME"),
    SER("$SER"),
    CRC("$CRC"),
    POW("$POW"),
    DTOT("$DTOT"),
    ADD2("$ADD2"),
    CHAN("$CHAN"),
    ITIME("$ITIME"),
    TMMI("$TMMI"),
    TMPL("$TMPL"),
    TIMESTRING("$TIMESTRING"),
    TIMEFROM1("$TIMEFROM1"),
    TIMETO1("$TIMETO1"),
    TIMEFROM2("$TIMEFROM2"),
    TIMETO2("$TIMETO2"),
    TESTDATA("$TESTDATA"),
    ARCHIVEDATA1("$ARCHIVEDATA1"),
    PASSWORD("$PASSWORD"),
    SIGNAL("$SIGNAL"),
    UNKNOWN("$UNKNOWN"),
    INVCODE("$INVCODE"),
    ARCHCODE("$ARCHCODE"),
    INVERTERDATA("$INVERTERDATA"),
    CNT("$CNT"),
    TIMEZONE("$TIMEZONE"), 
    TIMESET("$TIMESET");

    private String code;

    private VarEnum(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return this.code;
    }   
    
    public static VarEnum findVar(String token) {
        VarEnum result = null;
        for(VarEnum varEnum : VarEnum.values()) {
            if (token.equals(varEnum.getCode())) {
                result = varEnum;
                break;
            }
        }
        return result;
    }    
}
