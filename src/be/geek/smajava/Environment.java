package be.geek.smajava;

/**
 * Environment placeholder
 * @author geek
 */
public class Environment {
    
    /**
     * time in seconds
     */
    private long reporttime;
    
    /**
     * time data from inverter
     */
    private byte[] timedata;
    
    /**
     * from time in seconds
     */
    private long fromtime;
    
    private long totime;
    
    public Environment() {
        reporttime = System.currentTimeMillis() / 1000;
        // set default from time to 5 minutes in the past
        fromtime = reporttime - 300;
        totime = reporttime;
    }
    
    public long getReportTime() {
        return this.reporttime;
    }

    public byte[] getTimedata() {
        return timedata;
    }

    public void setTimedata(byte[] timedata) {
        this.timedata = timedata;
    }

    public long getFromtime() {
        return fromtime;
    }

    public void setFromtime(long fromtime) {
        this.fromtime = fromtime;
    }

    public long getTotime() {
        return totime;
    }

    public void setTotime(long totime) {
        this.totime = totime;
    }
    
    
}
