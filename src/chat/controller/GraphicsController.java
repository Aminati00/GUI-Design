/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.controller;

import chat.model.ChatModel;
import chat.model.Figure;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import chat.utility.OhmLogger;
import chat.view.DrawView;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * CommandController class for MouseEvents, ActionEvents and Command Design
 * Pattern implementation
 */
public class GraphicsController extends MouseAdapter implements MouseMotionListener
{

  /**
   * Reference to OhmLogger
   */
  private static Logger lg = OhmLogger.getLogger();
  
  /**
   * Reference to DrawView object
   */
  private DrawView view;

  /**
   * Reference to GraphicData object
   */
  private ChatModel model;

  /**
   * Int index to keep cnt of figures in drawing
   */
  private int index = 0;

  /**
   * Constructor which sets all references to given parameters
   *
   * @param view Given DrawView object
   * @param model Given GraphicData object
   */
  public GraphicsController(DrawView view, ChatModel model)
  {
    this.view = view;
    this.model = model;
  }

  /**
   * Registers events for UI elements
   */
  public void registerEvents()
  {
    view.addMouseMotionListener(this);
    view.addMouseListener(this);
    lg.info("Registered events");
  }

  /**
   * Action for mouseReleased event, increases index by one and repaints drawing
   *
   * @param evt Event that was triggered
   */
  @Override
  public void mouseReleased(MouseEvent evt)
  {
    if (evt.getButton() == MouseEvent.BUTTON1)
    {
      Figure lastFigure = model.getLastFigure();
      model.senden(lastFigure);
      model.addFigure();
      index += 1;
      lg.info("Left mousebutton released and new index: " + index);
    }
  }

  /**
   * Action for mouseDragged event, draws points
   *
   * @param evt Event that was triggered
   */
  @Override
  public void mouseDragged(MouseEvent evt)
  {
    if (SwingUtilities.isLeftMouseButton(evt))
    { 
      Point p = evt.getPoint();  
      if(model.getLastFigure().getLastPoint() != null)
      {
        view.drawline(p);
      }
      model.addPoint(p);
    }
  }
  
  @Override
  public void mouseMoved(MouseEvent evt)
  {
  }
}
