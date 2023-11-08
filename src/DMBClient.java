import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Properties;
public class DMBClient {

  static int maxTextLen_ = 256;
  static Configuration c_;

  // from configuration file
  static String server; // FQDN
  static int port; //server port

  public static void main(String[] args)
  {
    
    c_ = new Configuration("cs2003-net2.properties");

    try {
        server = c_.getAddress();
        port = c_.getPort();
    }
    catch (NumberFormatException e) {
        System.out.println("can't configure port: " + e.getMessage());
    }


    try {
      Socket       connection;
      OutputStream tx;
      InputStream  rx;
      byte[]       buffer;
      int          r;

      connection = startClient(server, port);
      tx = connection.getOutputStream();
      connection.setKeepAlive(true);
      String input;
     try{ 
    while(true){
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      input = reader.readLine();
      

      if(input.startsWith("%%to")){
        HashMap<String, String> list = CSV();
        String message = "";
        String[] task = input.split(" ");
        if (task.length == 1){
          System.out.println("%%to rrrr mmmmmmm");
          continue;
        }
        if(list.get(task[1]) == null){
          continue;

        }
        Socket con2 = startClient(task[1] + ".teaching.cs.st-andrews.ac.uk", Integer.valueOf(list.get(task[1])));
        OutputStream tx2 = con2.getOutputStream();
        for(int i = 2; i < task.length; i++){
          message = message+task[i]+" ";
        }
        String output = "from "+c_.getUser()+ " "+message;
        buffer = output.getBytes();
        r = buffer.length;
        if (r > maxTextLen_) {
        System.out.println("++ You entered more than " + maxTextLen_ + "bytes ... truncating.");
        r = maxTextLen_;
        
      }
      tx2.write(buffer, 0, r); // to server
     con2.close();
      continue;
      }
      buffer = input.getBytes();
      r = buffer.length;
      if (r > maxTextLen_) {
        System.out.println("++ You entered more than " + maxTextLen_ + "bytes ... truncating.");
        r = maxTextLen_;
      }
      System.out.println("Sending " + r + " bytes");
      tx.write(buffer, 0, r); // to server
    }
  
    }
  catch (SocketException e){
      System.out.println("Connection Broken!");
      connection.close();
    }
  }

    catch (IOException e) {
      
      System.err.println("IO Exception: " + e.getMessage());
     
    }
    catch(NullPointerException e){

    }
    
  } // main

  static Socket startClient(String hostname, int portnumber)
  {
    Socket connection = null;

    try {
      InetAddress address;
      int         port;

      address = InetAddress.getByName(hostname);
      port = portnumber;

      connection = new Socket(address, port); // make a socket

      System.out.println("++ Connecting to " + hostname + ":" + port
      + " -> " + connection);
    }

    catch (UnknownHostException e) {
      System.err.println("UnknownHost Exception: " + hostname + " "
                        + e.getMessage());
    }
    catch (IOException e) {
      System.err.println("IO Exception: " + e.getMessage());
    }

    return connection;
  } // startClient
  public static HashMap<String, String> CSV(){
    String line = "";
    HashMap<String, String> list = new HashMap<>();
     c_ = new Configuration("cs2003-net2.properties");
    try (BufferedReader br = new BufferedReader(new FileReader(c_.getCSV()))) {

      while ((line = br.readLine()) != null) {
        String[] student = line.split(",");
        list.put(student[0], student[1]);
      }

  } catch (IOException e) {
      e.printStackTrace();
  }
  return list;

  }
} // DMBClient
