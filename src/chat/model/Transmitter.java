/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model;

import chat.utility.OhmLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Model class for network setup and message sending/receiving
 */
public class Transmitter implements Runnable
{

  /**
   * Reference to OhmLogger
   */
  private static Logger lg = OhmLogger.getLogger();

  /**
   * Publisher for String
   */
  private SubmissionPublisher<Object> iPublisher;

  /**
   * ExecutorService for thread
   */
  private ExecutorService eService;

  /**
   * AtomicBoolean stop for thread signal
   */
  private AtomicBoolean isServer;

  /**
   * AtomicBoolean
   */
  private AtomicBoolean exceptionReceived;

  /**
   * AtomicBoolean
   */
  private AtomicBoolean canSend;

  /**
   * PORT int for network setup
   */
  final static int PORT = 35000;

  /**
   * IPADRESSE String for network setup
   */
  final static String IPADRESSE = "127.0.0.1";

  /**
   * obj received/to be sent
   */
  private Object obj;

  /**
   * Socket for network setup
   */
  private Socket s;

  /**
   * ServerSocket for network setup
   */
  private ServerSocket ss;

  /**
   * InputStream reference
   */
  private InputStream is;

  /**
   * InputStreamReader reference
   */
  private BufferedInputStream bis;

  /**
   * OutputStream reference
   */
  private OutputStream os;

  /**
   * OutputStreamReader reference
   */
  private BufferedOutputStream bos;

  /**
   * BufferedReader to read
   */
  private ObjectInputStream ois;

  /**
   * PrintWriter to write
   */
  private ObjectOutputStream oos;

  /**
   * Constructor which initialises new SingleThreadExecutor, creates
   * SubmissionPublisher and isServer AtomicBoolean object
   */
  public Transmitter()
  {
    iPublisher = new SubmissionPublisher<>();
    eService = Executors.newSingleThreadExecutor();
    isServer = new AtomicBoolean();
    exceptionReceived = new AtomicBoolean(false);
    canSend = new AtomicBoolean(false);
  }

  /**
   * Publisher subscribes to given parameter
   *
   * @param subscriber Given Subscriber
   */
  public void addValueSubscription(Flow.Subscriber<Object> subscriber)
  {
    iPublisher.subscribe(subscriber);
  }

  /**
   * Sets isServer to given parameter and submits the task
   *
   * @param state
   */
  public void setState(boolean state)
  {
    isServer.set(state);
    eService.submit(this);
  }

  /**
   * Method to send given String msg and flushs PrintWriter object
   *
   * @param obj Given String object
   */
  public void senden(Object obj)
  {
    if (canSend.get())
    {
      try
      {
        this.oos.writeObject(obj);
        lg.info("Gesendet Objekt der Klasse: " + obj.getClass());
        this.oos.flush();
      }
      catch (IOException ex)
      {
        lg.info(ex.toString());
      }
    }
  }

  /**
   * run method initialises the network and then indefinetly runs to read from
   * BufferedReader object
   */
  @Override
  public void run()
  {
    while (true)
    {
      if (isServer.get())
      {
        try
        {
          this.ss = new ServerSocket(PORT);
          lg.info("Server: Warte auf Verbindung");

          this.s = ss.accept();
          lg.info("Server: Verbindung da");
        }
        catch (IOException ex)
        {
          lg.info(ex.toString());
          exceptionReceived.set(true);
        }
      }
      else
      {
        try
        {
          this.s = new Socket(IPADRESSE, PORT);
          lg.info("Client: Verbindung da");
        }
        catch (IOException ex)
        {
          lg.info(ex.toString());
          exceptionReceived.set(true);
        }
      }
      try
      {
        this.os = s.getOutputStream();
        this.bos = new BufferedOutputStream(os);
        this.oos = new ObjectOutputStream(bos);
        oos.flush();
        this.is = s.getInputStream();
        this.bis = new BufferedInputStream(is);
        this.ois = new ObjectInputStream(bis);
      }
      catch (IOException ex)
      {
        lg.info(ex.toString());
        exceptionReceived.set(true);
      }
      while (!exceptionReceived.get())
      {
        canSend.set(true);
        try
        {
          obj = this.ois.readObject();
          iPublisher.submit(obj);
          lg.info("Submitted obj: " + obj);
        }
        catch (IOException ioex)
        {
          lg.info(ioex.toString());
          exceptionReceived.set(true);
        }
        catch (ClassNotFoundException ex)
        {
          lg.info(ex.toString());
          exceptionReceived.set(true);
        }
      }
      canSend.set(false);
      if (isServer.get())
      {
        try
        {
          s.close();
          ss.close();
        }
        catch (IOException ex)
        {
          lg.info(ex.toString());
        }
      }
      else
      {
        try
        {
          s.close();
          Thread.sleep(1000);
        }
        catch (IOException ex)
        {
          lg.info(ex.toString());
        }
        catch (InterruptedException ex)
        {
          lg.info(ex.toString());
        }
      }
      try
      {
        this.oos.close();
        this.bos.close();
        this.os.close();

        this.ois.close();
        this.bis.close();
        this.is.close();
      }
      catch (IOException ex)
      {
        lg.info(ex.toString());
      }
      exceptionReceived.set(false);
    }
  }
}
