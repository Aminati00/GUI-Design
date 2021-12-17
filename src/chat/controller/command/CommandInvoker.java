/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.controller.command;

import java.util.HashMap;

/**
 * Invoker for command design pattern
 */
public class CommandInvoker 
{
  /**
   * HashMap for commands includes the object and the command
   */
  private HashMap<Object, CommandInterface> commands;
  
  /**
   * Constructor which creates commands HashMap
   */
  public CommandInvoker()
  {
    commands = new HashMap<>();
  }
  
  /**
   * Maps Object to command
   *
   * @param key Object to add command to
   * @param value Command that is added to key object
   */
  public void addCommand(Object key, CommandInterface value)
  {
    commands.put(key, value);
  }
  
  /**
   * Calls execute for command of the key Object
   *
   * @param key Object to execute command from
   */
  public void executeCommand(Object key)
  {
    commands.get(key).execute();
  }
}
