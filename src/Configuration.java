

import java.io.*;
import java.net.*;
import java.util.Properties;

public class Configuration

{
  private String serverAddress;
  private int serverPort;
  private String dir;
  private String csv;
  private String user;
  private Properties    properties_;



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
           this.csv = properties_.getProperty("directoryFile");
           this.user = properties_.getProperty("user");
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
    public String getCSV(){
    return csv;
  }
      public String getUser(){
    return user;
  }
}
