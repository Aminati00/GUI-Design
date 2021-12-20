/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model;

import chat.utility.OhmLogger;
import java.awt.Point;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.Flow;
import java.util.logging.Logger;

/**
 * Model class for network setup and message sending/receiving
 */
public class ChatModel implements Flow.Subscriber<Object>
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
   * Subscription
   */
  private Flow.Subscription subscription;
  
  /**
   *  Int cnt used in onSubscribe() as index of subscription
   */
  private int cnt;
  
  /**
   * Reference to Transmitter object 
   */
  private Transmitter trans;
  
  /**
   * Reference to GraphicData object 
   */
  private GraphicData graphic;

  /**
   * Constructor which initialises new SingleThreadExecutor, creates
   * SubmissionPublisher and isServer AtomicBoolean object
   */
  public ChatModel()
  {
    iPublisher = new SubmissionPublisher<>();
    trans = new Transmitter();
    graphic = new GraphicData();
    
  }
  
  public void senden(Object obj)
  {
    trans.senden(obj);
  }
  
  public void setState(Boolean isServer)
  {
    trans.setState(isServer);
  }
  
  /**
   * Adds given Point object to Figur object from figures
   *
   * @param p Given Point object
   */
  public void addPoint(Point p)
  {
    graphic.addPoint(p);
  }

  /**
   * Function to getLastFigure
   * @return last Figure object in figures ArrayList
   */
  public Figure getLastFigure()
  {
    return graphic.getLastFigure();
  }
  
  /**
   * Adds new Figure to figures
   */
  public void addFigure()
  {
    graphic.addFigure();
  }
  
  /**
   * Adds new Figure to figures
   */
  public void addFigure(Figure figure)
  {
    graphic.addFigure(figure);
  }
  
  /**
   * Method to return unmodifiable list of figures
   *
   * @return undmodifiable list of figures
   */
  public List<Figure> getFigures()
  {
    return graphic.getFigures();
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
   * Method to subscribe to NumberGen objects
   */
  public void subscribe()
  {
    trans.addValueSubscription(this);

    }
  
  
  @Override
  public void onSubscribe(Flow.Subscription subscription)
  {
    this.subscription = subscription;
    subscription.request(1);
    lg.info("Model subscribed to Transmitter");
  }

  @Override
  public void onNext(Object item)
  {
    iPublisher.submit(item);
    subscription.request(1);
  }

  @Override
  public void onError(Throwable throwable)
  {
  }

  @Override
  public void onComplete()
  {
  }
}
