/*
  Using java.util.Properties to demonstrate configuration information
  
  Based on code from Saleem Bhatti
  Sep 2019
  Oct 2018

*/

import java.io.*;
import java.net.*;

// https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Properties.html
import java.util.Properties;

public class Configuration

{
  private String serverAddress;
  private int serverPort;
  private String dir;
  private Properties    properties_;


  private String        logFile_ = "server.log";

  // These default values can be overriden in the properties file.

  public Configuration(String propertiesFile)
  {


    try {
      properties_ = new Properties();
      InputStream p = getClass().getClassLoader().getResourceAsStream(propertiesFile);
      if (p != null) {
        properties_.load(p);

           this.serverAddress = properties_.getProperty("serverAddress");
           this.serverPort = Integer.valueOf(properties_.getProperty("serverPort"));
           this.dir = properties_.getProperty("boardDirectory");
        p.close();
      }

    }

    catch (UnknownHostException e) {
      System.out.println("Problem: " + e.getMessage());
    }

    catch (NumberFormatException e) {
      System.out.println("Problem: " + e.getMessage());
    }

    catch (IOException e) {
      System.out.println("Problem: " + e.getMessage());
    }

  }
  public int getPort(){
    return this.serverPort;
  }
  public String getAddress(){
    return this.serverAddress;
  }
  public String getBoard(){
    return dir;
  }
}
