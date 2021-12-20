/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.controller;

import chat.controller.command.CommandClient;
import chat.controller.command.CommandServer;
import chat.controller.command.CommandInvoker;
import chat.controller.command.CommandSend;
import chat.model.ChatModel;
import chat.utility.OhmLogger;
import chat.view.ChatView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * CommandController class for ActionEvents and Command Design
 * Pattern implementation
 */
public class CommandController implements ActionListener
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
   * Reference to CommandInvoker object for cdp
   */
  private CommandInvoker invoker;
  
  /**
   * Constructor which sets all references to given parameters
   *
   * @param view Given ChatView object
   * @param model Given Transmitter object
   * @param invoker Given CommandInvoker object
   */
  public CommandController(ChatView view, ChatModel model, CommandInvoker invoker)
  {
    this.view = view;
    this.model = model;
    this.invoker = invoker;
  }
  
  /**
   * Registers events for UI elements
   */
  public void registerEvents()
  {
    view.getBtnServer().addActionListener(this);
    view.getBtnSenden().addActionListener(this);
    view.getTfNachricht().addActionListener(this);
    view.getBtnClient().addActionListener(this);
    lg.info("Registered events");
  }
  
  /**
   * Registers commands for UI elements
   */
  public void registerCommands()
  {
    CommandSend cmdSend = new CommandSend(view,model);
    CommandServer cmdConnectS = new CommandServer(model);
    CommandClient cmdConnectC = new CommandClient(model);
    
    invoker.addCommand(view.getBtnServer(), cmdConnectS);
    invoker.addCommand(view.getBtnClient(), cmdConnectC);
    invoker.addCommand(view.getBtnSenden(), cmdSend);
    invoker.addCommand(view.getTfNachricht(), cmdSend);
    lg.info("Registered commands");
  }
  
  /**
   * Action to execute command and disable buttons when pressed
   *
   * @param evt Event that was triggered
   */
  @Override
  public void actionPerformed(ActionEvent evt)
  {
    Object key = evt.getSource();
    invoker.executeCommand(key);
    if(key == view.getBtnServer())
    {
      view.getBtnServer().setBackground(Color.GREEN);
      view.getBtnClient().setBackground(Color.RED);
    }
    if(key == view.getBtnClient())
    {
      view.getBtnServer().setBackground(Color.RED);
      view.getBtnClient().setBackground(Color.GREEN);
    }
    if((key == view.getBtnServer()) || (key == view.getBtnClient()) )
    {
      view.getBtnServer().setEnabled(false);
      view.getBtnClient().setEnabled(false);
    }
    lg.info("Executed command");
  }
}
