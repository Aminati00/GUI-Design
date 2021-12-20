/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.controller;

import chat.model.ChatModel;
import chat.model.Figure;
import chat.utility.OhmLogger;
import chat.view.ChatView;
import chat.view.DrawView;
import java.util.concurrent.Flow;
import java.util.logging.Logger;

/**
 * ReceiveAdapter class for subscriber publisher design pattern
 */
public class ReceiveAdapter implements Flow.Subscriber<Object>
{
  /**
   * Reference to OhmLogger
   */
  private static Logger lg = OhmLogger.getLogger();
  
  /**
   * Reference to ChatView object
   */
  private ChatView view;
  
  /**
   * Reference to Transmitter object
   */
  private ChatModel model;
  
  /**
   * Reference to DrawView object
   */
  private DrawView drview;
  
  /**
   * Subscription to set text
   */
  private Flow.Subscription subscription;
  
  /**
   * Constructor for ReceiveAdapter, sets view and model to given objects
   * @param model Given Transmitter object
   * @param view Given ChatView object
   */
  public ReceiveAdapter(ChatModel model, ChatView view, DrawView drview)
  {
    this.view = view;
    this.model = model;
    this.drview = drview;
  }
  
  /**
   * Method to subscribe to model
   */
  public void subscribe()
  {
    model.addValueSubscription(this);
  }
  
  /**
   * Interface implementation, sets subscription and requests from publisher
   * @param subscription Given Subscription object
   */
  @Override
  public void onSubscribe(Flow.Subscription subscription)
  {
    this.subscription = subscription;
    lg.info("Adapter subscribed to Model");
    this.subscription.request(1);
  }
  
  /**
   * Interface implementation, appends text to given values
   * and requests from publisher
   * @param obj Given String message to be appended
   */
  @Override
  public void onNext(Object obj)
  {
    if(obj instanceof String)
    {
      view.getTaTextAusgabe().append("Partner: " + obj + "\n");
      lg.info("Text appended: " + obj);
    }
    if(obj instanceof Figure)
    {
      model.addFigure((Figure)obj);
      drview.drawfigure((Figure)obj);
      lg.info("Figure appended.");
    }
    this.subscription.request(1);
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
