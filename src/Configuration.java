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
  public String serverAddress;
  public int serverPort;
  public Properties    properties_;
  public String        propertiesFile_ = "server.properties";

  public String        logFile_ = "server.log";

  // These default values can be overriden in the properties file.
  public String     serverName_="Test UDP Server"; // A name for the server
  public int        port_=12345; // A default port value

  Configuration(String propertiesFile)
  {
    if (propertiesFile != null) {
        propertiesFile_ = propertiesFile;
    }

    try {
      properties_ = new Properties();
      InputStream p = getClass().getClassLoader().getResourceAsStream(propertiesFile_);
      if (p != null) {
        properties_.load(p);

           this.serverAddress = properties_.getProperty("serverAddress");
           this.serverPort = Integer.valueOf(properties_.getProperty("serverPort"));
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
}
