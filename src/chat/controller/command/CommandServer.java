/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.controller.command;

import chat.model.ChatModel;
import chat.utility.OhmLogger;
import java.util.logging.Logger;

/**
 * Class for command to set server state
 */
public class CommandServer implements CommandInterface
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
   * Boolean to signal server status
   */
  private boolean isServer = true;
  
  /**
   * Constructor which model to given parameter
   * @param model Given Transmitter object
   */
  public CommandServer(ChatModel model)
  {
    this.model = model;
  }
  
  /**
   * Calls setState of Transmitter object with isServer parameter
   */
  @Override
  public void execute()
  {
    model.setState(isServer);
    lg.info("Server: Server choosens");
  }
}
