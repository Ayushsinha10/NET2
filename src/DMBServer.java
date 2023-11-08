import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
public class DMBServer{


  static ServerSocket  server_;
  static int           sleepTime_ = 100; // milliseconds
  static int           bufferSize_ = 256; 
  static int           soTimeout_ = 10; // 10 ms

  public static void main(String[] args) {
    startServer();

    try {
      Socket       connection;
      OutputStream tx;
      InputStream  rx;

      connection = server_.accept(); // waits for connection
      tx = connection.getOutputStream();
      rx = connection.getInputStream();
      server_.close(); // no need to wait now
      
      System.out.println("New connection ... " +
        connection.getInetAddress().getHostName() + ":" +
        connection.getPort());
        connection.setKeepAlive(true);
    try{
    while(true){
      byte[] buffer = new byte[bufferSize_];
      int b = 0;
      while (b < 1) {
        Thread.sleep(sleepTime_);
        try{
          byte[] b3 = new byte[1];
          tx.write(b3, 0, 1);
        }
        catch(IOException e){
          connection.close();
          return;

        }

        buffer = new byte[bufferSize_];
        b = rx.read(buffer);
      }

      if (b > 0) {
        String s = new String(buffer);
        FileDir(buffer);
        System.out.println("Received " + b + " bytes --> " + s);      
      }
    }
  }
  catch (SocketException e){
    connection.close();
    return;
  }
    }

    catch (SocketTimeoutException e) {
      // no incoming data - just ignore
      
    }
    catch (InterruptedException e) {
      System.err.println("Interrupted Exception: " + e.getMessage());
    }
    catch (IOException e) {
      System.err.println("IO Exception: " + e.getMessage());
    }
  }


  public static void startServer() {
    try {
        Configuration C = new Configuration("cs2003-net2.properties");
      server_ = new ServerSocket(C.getPort()); // make a socket
      System.out.println("--* Starting server " + server_.toString());
    }

    catch (IOException e) {
      System.err.println("IO Exception: " + e.getMessage());
    }
  }
  public static void FileDir(byte[] b){
    Date               d = new Date();
    String            df = new String("yyyy-MM-dd_HH-mm-ss.SSS");
    String            df2 = new String("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat(df2);
    SimpleDateFormat sdf = new SimpleDateFormat(df);
    String            s2 =  sdf2.format(d);
    String             s = sdf.format(d);
    Configuration C = new Configuration("cs2003-net2.properties");
    try{
    File dir = new File(C.getBoard() + "/"+s2);

  
    if (dir.exists()) {
      File file = new File(C.getBoard() +"/"+s2+"/"+s);
      file.createNewFile();
      FileOutputStream os = new FileOutputStream(file);
      os.write(b);
      os.close();
    }
    else{
      dir.mkdir();
      File file = new File(C.getBoard() +"/"+s2+"/"+s);
      file.createNewFile();
      FileOutputStream os = new FileOutputStream(file);
      os.write(b);
      os.close();
    }
    
    }
    catch(IOException e){
      System.out.println(e.getMessage());
    }

  }

}