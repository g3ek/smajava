package be.geek.smajava;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.TimeZone;

/**
 * Encodes data from instructions file
 * Encodes data to be received and matched according to instruction file
 * Decodes/extract received data
 * @author geek
 */
public class Codec {
    
    /**
     * counter for sent lines when encoding {@link VarEnum#CNT}.
     */
    private byte send_count = 0x00;
    
    public byte[] encode(String line) throws SmajavaException {
        byte[] result = new byte[500];
        int cc=0;
        // split line into tokens
        String[] tokens = line.substring(2, line.length() - 1).split("\\s");
        for(String token : tokens) {
            VarEnum varEnum = VarEnum.findVar(token);
            if (varEnum != null) {
                switch(varEnum) {
                    case END:
                        break;
                    case ADDR: // encode mac address of inverter
                        byte[] addrBytes = Configuration.getInverter().convertAddress();                    
                        result = Util.addToBuffer(result, addrBytes, cc);
                        cc += addrBytes.length;
                        break; 
                    case INVCODE: // extracted inverter code
                        result[cc] = Configuration.getInverter().getInverterCode();
                        cc++;
                        break;
                    case ADD2:
                        byte[] address2 = Configuration.getInverter().getSecondAddress();
                        for (int cntr = 0; cntr < address2.length; cntr++) {
                            result[cc] = address2[cntr];
                            cc++;
                        }
                        break;   
                    case UNKNOWN: // inverter type 
                        byte[] inverter = InvertersData.getInverterType(Configuration.getInverter().getCode());
                        for (int cntr = 0; cntr < 4; cntr++) {
                            result[cc] = inverter[cntr];
                            cc++;
                        }
                        break; 
                    case CNT: // sent line counter
                        send_count++;
                        result[cc] = send_count;
                        cc++;                        
                        break;
                    case CRC: // calculate FCS checksum
                        // short because we need a two byte result
                        short fcs = FCS.calcFcs(result, cc - 19);
                        result[cc] = (byte) (fcs & 0x00ff);    /* least significant byte first */
                        cc++;
                        result[cc] = (byte) ((fcs >> 8) & 0x00ff);
                        cc++;
                        Object[] escapeResults = Util.add_escapes(result, cc);
                        result = (byte[]) escapeResults[0];
                        cc = (Integer) escapeResults[1];
                        result = Util.fix_length_send(result, cc);                        
                        break;
                    case TIME: // encode current time
                        char[] tt = Long.toHexString(Configuration.getEnvironment().getReportTime()).toCharArray();
                        char[] ti = new char[2];
                        for (int cntr = 7; cntr > 0; cntr = cntr - 2) { // reverse order
                            ti[1] = tt[cntr];
                            ti[0] = tt[cntr - 1];
                            result[cc] = Util.encodeHexString(String.valueOf(ti));
                            cc++;
                        }  
                        break;
                    case PASSWORD: // encode password
                        char[] pwdchars = Configuration.getInverter().getPassword().toCharArray();
                        for (int cntr = 0; cntr < 12; cntr++) {
                            if (cntr < pwdchars.length) {
                                int pass_i = pwdchars[cntr];
                                result[cc] = (byte) ((pass_i + 0x88) % 0xff);
                            } else {
                                result[cc] = (byte) 0x88;
                            }
                            cc++;
                        }                        
                        break;
                    case TIMEZONE: // should be DST? I hope?
                        TimeZone timeZone = TimeZone.getDefault();
                        int tz = (timeZone.getDSTSavings() / 1000) + 1;                                
                        //System.out.println("dst: "+tz);                                
                        result[cc] = (byte) (tz - (tz/256)*256);
                        result[cc+1] = (byte) (tz/0xff);
                        cc+=2;                        
                        break;
                    case TIMESET: // unknown setting, no idea what this means
                        byte[] timeset = new byte[] { (byte)0x30,(byte)0xfe,(byte)0x7e,(byte)0x00 };
                        for(int cntr=0; cntr<4; cntr++ ) {                            
                            result[cc] = timeset[cntr];
                            cc++;
                        }
                        break;  
                    case TMPL: // current time + 1
                        // get report time and convert
                        tt = Long.toHexString(Configuration.getEnvironment().getReportTime()+1).toCharArray();
                        //sprintf(tt,"%x",(int)reporttime); //convert to a hex in a string
                        ti = new char[2];
                        for (int cntr = 7; cntr > 0; cntr = cntr - 2) { //change order and convert to integer
                            ti[1] = tt[cntr];
                            ti[0] = tt[cntr - 1];
                            result[cc] = Util.encodeHexString(String.valueOf(ti));
                            cc++;
                        }
                        break;    
                    case TMMI: // current time - 1
                        // get report time and convert
                        tt = Long.toHexString(Configuration.getEnvironment().getReportTime()-1).toCharArray();
                        //sprintf(tt,"%x",(int)reporttime); //convert to a hex in a string
                        ti = new char[2];
                        for (int cntr = 7; cntr > 0; cntr = cntr - 2) { //change order and convert to integer
                            ti[1] = tt[cntr];
                            ti[0] = tt[cntr - 1];
                            result[cc] = Util.encodeHexString(String.valueOf(ti));
                            cc++;
                        }
                        break; 
                    case TIMESTRING: // encode extracted time data from inverter
                        byte[] timestr = Configuration.getEnvironment().getTimedata();
                        for (int cntr=0;cntr<25;cntr++){
                            if (cntr < timestr.length) {
                                result[cc] = timestr[cntr];
                            } else {
                                result[cc] = 0x0;
                            }
                            cc++;
                        }
                        break;  
                    case ARCHCODE: // ??
                        result[cc] = InvertersData.getArchiveCode(Configuration.getInverter().getCode());
                        cc++;
                        break;  
                    case SER: // encode serial number
                        byte[] serial = Configuration.getInverter().getSerial();
                        for (int cntr=0;cntr<4;cntr++){
                            result[cc] = serial[cntr];
                            cc++;
                        }                                                        
                        break;
                    case TIMEFROM1: // encode start time
                        long fromtime = Configuration.getEnvironment().getFromtime();
                        tt = Long.toHexString(fromtime).toCharArray();
                        ti = new char[2];
                        for (int cntr=7;cntr>0;cntr=cntr-2){ //change order and convert to integer
                                ti[1] = tt[cntr];
                                ti[0] = tt[cntr-1];	
                                result[cc] = Util.encodeHexString(String.valueOf(ti));
                                cc++;		
                        }
                        break;
                    case TIMETO1: // encode end time
                        long totime=Configuration.getEnvironment().getReportTime();
                        //sprintf(tt,"%03x",(int)totime); //convert to a hex in a string
                        tt = Long.toHexString(totime).toCharArray();
                        ti = new char[2];
                        // get report time and convert
                        for (int cntr=7;cntr>0;cntr=cntr-2){ //change order and convert to integer
                                ti[1] = tt[cntr];
                                ti[0] = tt[cntr-1];	
                                result[cc] = Util.encodeHexString(String.valueOf(ti));
                                cc++;		
                        }
                        break;                       
                    default:
                        throw new SmajavaException("Don't know how to handle "+varEnum);
                }
            } else {
                result[cc] = Util.encodeHexString(token);
                cc++;
            }
        }
        return Arrays.copyOfRange(result, 0, cc);
    }
    
    public void decode(String line, byte[] received) throws SmajavaException, IOException, InterruptedException {
        String[] tokens = line.substring(2, line.length() - 1).split("\\s");
        for(String token : tokens) {
            VarEnum varEnum = VarEnum.findVar(token);
            if (varEnum != null) {
                switch(varEnum) {
                    case END:
                        break;                    
                    case INVCODE:
                        byte invcode = received[22];
                        Configuration.getInverter().setInverterCode(invcode);
                        break;
                    case ADD2: // extract 2nd address
                        byte[] address2 = Arrays.copyOfRange(received, 26, 32);
                        Configuration.getInverter().setSecondAddress(address2);
                        Log.debugBytes(this, "add2: ", address2);
                        break; 
                    case SIGNAL:
                        float strength = (received[22] * (float)100)/0xff;
                        Log.debug(this, "signal: %02x", received[22]);
                        Log.info(this, "bluetooth signal = " + strength);
                        break; 
                    case SER:
                        byte[] data = readStream(received);
                        byte[] serial = new byte[4];
                        serial[3]=data[19];
                        serial[2]=data[18];
                        serial[1]=data[17];
                        serial[0]=data[16];
                        Log.debug(this, "serial=%02x:%02x:%02x:%02x\n",serial[3]&0xff,serial[2]&0xff,serial[1]&0xff,serial[0]&0xff );                        
                        Configuration.getInverter().setSerial(serial);
                        break;
                    case TIMESTRING: // extract time data from inverter
                        byte[] timestr = null;
                        if(( received[60] == 0x6d )&&( received[61] == 0x23 ))
                        {
                            timestr = Arrays.copyOfRange(received, 63, 63+24);
                            Configuration.getEnvironment().setTimedata(timestr);
                            //System.out.println("timestr len: "+timestr.length);
                            //if (debug == 1) printf("extracting timestring\n");
                            byte[] timeset = Arrays.copyOfRange(received, 79, 79+4);
//                            for(int cntr=63;cntr<63+4;cntr++) {
//                                System.out.printf("%02x", readBuffer[cntr]);
//                            }
                            long idate = Util.convertToValue(received, 63, 4);                                    
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(idate*1000);
                            long reportTime = Configuration.getEnvironment().getReportTime();

                            /* Allow delay for inverter to be slow */
                            if( reportTime > idate ) {
                                Log.debug(this, "Time skew between inverter and us, waiting "+(reportTime-idate)+" seconds.");
                                Thread.sleep((reportTime - idate)*1000);
                            }
                        }
                        else
                        {
                            Log.error(this, "Could not extract timestring, resetting...");
                            Log.debugBytes(this, "received: ", received);
                            if (received.length > 63) {
                                timestr = Arrays.copyOfRange(received, 63, 63+24);
                                Log.debugBytes(this, "timestr: ", timestr);
                            }
                            // TODO comment this
                            //System.exit(-1);
                            throw new SmajavaException("Could not extract timestring", SmajavaException.Type.BLUETOOTH_RESET);
                        }
                        break;
                    case POW:
                        data = readStream(received);
                        int gap = 1;
                        //System.out.printf( "data=%02x\n",data[3] );
                        if( (data[3]) == 0x08 )
                            gap = 40; 
                        if( (data[3]) == 0x10 )
                            gap = 40; 
                        if( (data[3]) == 0x40 )
                            gap = 28;
                        if( (data[3]) == 0x00 )
                            gap = 28;
                        for (int cntr = 0; cntr<data.length; cntr+=gap ) 
                        {
                           long idate=Util.convertToValue(data,cntr+4, 4);

                           float currentpower_total = Util.convertToValue(data, cntr+8, 3 );
                           ReturnType toReturn = null;
                           Collection<ReturnType> returnTypes = Configuration.getReturnTypes();
                           for(ReturnType returnType : returnTypes )
                           {
                              if(( (data[cntr+1]) == returnType.getKey1() ) &&
                                      ((data[cntr+2]) == returnType.getKey2())) {
                                  toReturn=returnType;
                                  break;
                              }
                           }
                           if( toReturn != null ) {
                               Log.info(this, "%-20s = %.0f %-20s\n", toReturn.getDescription(), currentpower_total/toReturn.getDivisor(), toReturn.getUnits() );
                           }
                           else
                               Log.info(this, "NO DATA for %02x %02x = %.0f NO UNITS\n", data[cntr+1], data[cntr+2], currentpower_total );
                        }                        
                        break;
                    case ARCHIVEDATA1: // $ARCHIVEDATA1
                        boolean finished = false;
                        long idate=0;
                        long prev_idate = 0;
                        float gtotal, ptotal = 0;
                        int archdatalen = 0;
                        while(!finished) {                   
                            int togo = (int) Util.convertToValue(received, 43, 2);
                            Log.debug(this, "togo="+togo);
                            data = readStream(received); 
                            int j=0;
                            byte[] datarecord = new byte[250];
                            for(int cntr=0; cntr < data.length; cntr++) {
                                datarecord[j]=data[cntr];
                                j++;
                                if (j > 11) {
                                    if (idate>0) {
                                        prev_idate = idate;
                                    } else {
                                        prev_idate = 0;
                                    }
                                    idate = Util.convertToValue(datarecord, 0, 4);
                                    if (prev_idate == 0) {
                                        prev_idate = idate-300;
                                    }
                                    gtotal = Util.convertToValue(datarecord, 4, 8);
                                    if (archdatalen == 0) {
                                        ptotal = gtotal;
                                    }
                                    Log.info(this, "current: "+(gtotal-ptotal)*12);
                                    Log.info(this, "\ntotal=%.3f Kwh current=%.0f Watts i=%d ", gtotal/1000, (float)((gtotal-ptotal)*((float)12)), cntr);
                                    if (idate != prev_idate+300) {
                                        System.out.println("Date error!");
                                        System.exit(-1);
                                    }
                                    if (archdatalen == 0) {

                                    }                                            
                                    j=0;
                                    archdatalen++;
                                    ptotal = gtotal;
                                }
                            }
                            if (togo == 0) {
                                finished = true;
                            } else {
                                received = Configuration.getInverter().receive();
                            }

                        }
                        break;                          
                    default:
                        throw new SmajavaException("Don't know how to handle "+varEnum);
                }   
            } else {
                throw new SmajavaException("Don't know how to handle "+token);
            }
        }
    }
    
    /**
     * Extract data from the first read.
     * If no end byte found then read next chunk of data to be extracted until end byte if found.
     * @param received
     * @return
     * @throws IOException 
     */
    private byte[] readStream(byte[] received) throws IOException {        
        byte[] result = new byte[500];
        int cntr=0;
        boolean finished = false;
        boolean finished_record = false;
        int i=59;
        
        boolean terminated = received[received.length -1] == 0x7e;

        
        while(!finished) {
            for(; (i < (received.length)&&(!terminated || i < received.length-3 )); i++) {
                result[cntr] = received[i];
                cntr++;
            }
            if (!terminated) {
                received = Configuration.getInverter().receive();
                //received = readBluetooth(inStream);
                terminated = received[received.length -1] == 0x7e;
                i=18;
            } else {
                finished = true;
            }
        }
        result = Util.addToBuffer(new byte[cntr], result, 0);      
        return result;
    }        
    
}
