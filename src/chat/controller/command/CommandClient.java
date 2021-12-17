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
 * Class for command to set client state
 */
public class CommandClient implements CommandInterface
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
   * Boolean signal client status
   */
  private boolean isServer = false;
  
  /**
   * Constructor which model to given parameter
   * @param model Given Transmitter object
   */
  public CommandClient(ChatModel model)
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
    lg.info("Client: Client choosen");
  }
}
