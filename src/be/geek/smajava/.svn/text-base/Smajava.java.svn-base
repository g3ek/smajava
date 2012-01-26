/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.geek.smajava;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author geek
 */
public class Smajava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {        
        Log.info("main", "Starting smajava");
        
        Configuration.init();        
        
        if (args.length  == 0) {
            Log.error(Smajava.class, "Missing inverter address");
            System.exit(-1);
        }
        
        String inverterAddress = args[0];
        
        if (args.length > 1 && args[1].length() > 0) {            
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date fromDate = dateFormat.parse(args[1]);
                Log.debug(Smajava.class, "from date: "+fromDate);
                Configuration.getEnvironment().setFromtime(fromDate.getTime() / 1000);
            } catch (ParseException ex) {
                Log.error(Smajava.class, "Could not parse fromdate arg: "+args[1], ex);
                System.exit(-1);
            }
        }
        
        Configuration.setDataFilename("/sma.in.new");
        Inverter inverter = new Inverter("3000TLHF", inverterAddress);
        inverter.setPassword("0000");
        Configuration.setInverter(inverter);        
        
        Engine engine = new Engine();
        try {
            Configuration.getInverter().openConnection();
            engine.process(inverter);
            Configuration.getInverter().closeConnection();
        } catch (Exception ex) {
            Log.error("Smajava", ex.getMessage(), ex);
            System.exit(-1);
        }
    }
}
