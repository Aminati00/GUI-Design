/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import chat.controller.CommandController;
import chat.controller.GraphicsController;
import chat.controller.ReceiveAdapter;
import chat.controller.command.CommandInvoker;
import chat.model.ChatModel;
import chat.model.Transmitter;
import chat.model.GraphicData;
import chat.view.ChatView;
import chat.view.DrawView;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Start class
 */
public class Start
{

  /**
   * Constructor which creates all objects and calls all necessary methods
   */
  public Start()
  {
    DrawView drview = new DrawView();
    GraphicData drmodel = new GraphicData();
    ChatModel model = new ChatModel();
    drview.setModel(model);
    ChatView view = new ChatView();
    view.getPnGraphic().add(drview);
    view.getPnGraphic().setBackground(Color.WHITE);

    Transmitter trans = new Transmitter();
    CommandInvoker invoker = new CommandInvoker();
    CommandController commandctrl = new CommandController(view, model, invoker);
    commandctrl.registerCommands();
    commandctrl.registerEvents();
    GraphicsController grctrl = new GraphicsController(drview, model);
    grctrl.registerEvents();
    ReceiveAdapter adapter = new ReceiveAdapter(model, view, drview);
    model.subscribe();
    adapter.subscribe();

    view.setVisible(true);
  }

  /**
   * Method that sets LookAndFeel and creates Start object
   *
   * @param args
   */
  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(null, ex.toString());
    }

    new Start();
  }

}
