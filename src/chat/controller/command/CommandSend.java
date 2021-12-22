/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.controller.command;

import chat.model.ChatModel;
import chat.utility.OhmLogger;
import chat.view.ChatView;
import java.util.logging.Logger;

/**
 * Class for command to send message
 */
public class CommandSend implements CommandInterface
{
  /**
   * Reference to OhmLogger
   */
  private static Logger lg = OhmLogger.getLogger();
  
  /**
   * Reference to Transmitter object
   */
  private ChatModel model;
  
  /**
   * Reference to ChatView object
   */
  private ChatView view;
  
  public CommandSend(ChatView view, ChatModel model)
  {
    this.view = view;
    this.model = model;
  }
  
  /**
   * Gets msg from view, appends it to JTextArea object and sends it
   */
  @Override
  public void execute()
  {
      System.out.println("hallo");
    String msg = view.getTfNachricht().getText();
    if(!msg.isBlank())
    {
      model.senden(msg);
      view.getTaTextAusgabe().append("Ich: " + msg + "\n");
    }
    view.getTfNachricht().setText("");
  }
}
