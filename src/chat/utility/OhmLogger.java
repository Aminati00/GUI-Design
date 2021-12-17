/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.utility;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.*;

/**
 * OhmLogger for all logging purposes
 */
public class OhmLogger 
{
  /**
   * Reference for logger
   */
  private static Logger lg = null;
  
  /**
   * Get rootLogger to disable consoleoutput
   */
  private static Logger rootLogger = Logger.getLogger("");
  
  /**
   * Properties to configure logger
   */
  private static Properties props;
  
  /**
   * String which saves level from properties
   */
  static String level;
  
  /**
   * String which saves path to logfile
   */
  private static String logpath;

  private OhmLogger()
  {
  }
  
  /**
   * Gets logger "OhmLogger" and calls initLogger method
   * @return Logger reference
   */
  public static Logger getLogger()
  {
    if (lg == null)
    {
      lg = Logger.getLogger("OhmLogger");
      initLogger();
    }
    return lg;
  }
  
  /**
   * Gets properties from logger.properties file
   */
  private static void getProperties()
  {
    props = new Properties();
    InputStream is = OhmLogger.class.getResourceAsStream("logger.properties");
    try
    {
      props.load(is);
    }
    catch (IOException ex)
    {
      System.err.print(ex.toString());
    }
    
    level = props.getProperty("LOG_LEVEL");
    logpath = props.getProperty("LOG_DATEI");
  }
  
  /**
   * Disables rootLogger console output, calls getProperties and
   * sets filehandler for OhmLogger with custom Formatter OhmFormatter
   */
  private static void initLogger()
  {
    Handler[] allHandlers = rootLogger.getHandlers();
    rootLogger.removeHandler(allHandlers[0]);
    try
    {
      FileHandler rh = new FileHandler();
      rootLogger.addHandler(rh);
    }
    catch (IOException ex)
    {
      System.err.print(ex.toString());
    }
    catch (SecurityException ex)
    {
      System.err.print(ex.toString());
    }
    
    getProperties();
    try
    {
      FileHandler fh = new FileHandler(logpath);
      fh.setFormatter(new OhmFormatter());
      lg.addHandler(fh);
    }
    catch (IOException ex)
    {
      System.err.print(ex.toString());
    }
    catch (SecurityException ex)
    {
      System.err.print(ex.toString());
    }
    
  }
}

/**
 * OhmFormatter for custom log format
 */
class OhmFormatter extends SimpleFormatter
{
  /**
   * Main format method
   * @param record Message to be logged
   * @return Formatted logentry
   */
  @Override
  public String format(LogRecord record)
  {
    String logLine = "";
    LocalDateTime ldt = LocalDateTime.now();
    String sldt = rightpad(ldt.toString(), 29);
    logLine += sldt;
    logLine += " ; " + OhmLogger.level;
    String sClass = rightpad(" ; " + record.getSourceClassName(),37);
    logLine += sClass;
    String sMsg = " ; " + record.getMessage();
    logLine += sMsg;
    logLine += "\n";
    return logLine;
  }
  
  /**
   * String formatting for consistent log length
   * @param text String to be formatted
   * @param length Length that the string will have
   * @return Formatted string
   */
  private String rightpad(String text, int length) {
    return String.format("%-" + length + "." + length + "s", text);
  }
}
