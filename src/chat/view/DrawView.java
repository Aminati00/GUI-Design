/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.view;

import chat.model.ChatModel;
import chat.model.Figure;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.DialogTypeSelection;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import chat.utility.OhmLogger;
import java.awt.BasicStroke;
import java.awt.Point;
import java.util.logging.Logger;

/**
 * Class DrawView which creates the graphic for the drawing
 */
public class DrawView extends JComponent implements Printable
{

  /**
   * Reference to OhmLogger
   */
  private static Logger lg = OhmLogger.getLogger();

  /**
   * Dimension for pixel
   */
  private final static Dimension DIM_ONE = new Dimension(1, 1);
  
  /**
   * Reference to BasicStroke object
   */
  private BasicStroke pinsel;
  
  /**
   * Rectangle to create a pixel
   */
  private Rectangle2D.Float pixel;

  /**
   * Reference to Line2D.Float object to draw lines
   */
  private Line2D.Float line;

  /**
   * Reference to model for data handling
   */
  private ChatModel model;
  
  
  
  /**
   * Constructor which creates Rectangle and Line object
   */
  public DrawView()
  {
    pixel = new Rectangle2D.Float();
    line = new Line2D.Float();
    pinsel = new BasicStroke(3);
  }

  /**
   * Method to set model reference
   *
   * @param model Given GraphicData object
   */
  public void setModel(ChatModel model)
  {
    this.model = model;
    lg.info("Set model");
  }

  /**
   * paintComponent method to draw graphic
   *
   * @param g Given Graphics object
   */
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(pinsel);
    drawmodel(g2);
    lg.info("Called paintComponent");
  }
  
  /**
   * Draws line from lastPoint to given Point object
   * @param p Given Point object
   */
  public void drawline(Point p)
  {
    Graphics2D g2 = (Graphics2D)this.getGraphics();
    Figure lastFigure = model.getLastFigure();
    Point temp = lastFigure.getLastPoint();
    line.setLine(temp, p);
    g2.setStroke(pinsel);
    g2.draw(line);
    
    g2.dispose();
  }
  
  public void drawfigure(Figure f)
  {
    Graphics2D g2 = (Graphics2D)this.getGraphics();
    Point oldP = null;
    for (Point p : f.getPoints())
    {
      if (oldP == null)
      {
        oldP = p;
      }
      line.setLine(oldP, p);
      g2.setStroke(pinsel);
      g2.draw(line);

      oldP = p;
    }
    
    g2.dispose();
  }        
  /**
   * Draws the lines from point to point from model data
   *
   * @param g2 Given Graphics2D object
   */
  public void drawmodel(Graphics2D g2)
  {
    model.getFigures().forEach(f ->
    {
      Point oldP = null;
      for(Point p : f.getPoints())
      {
        if(oldP == null)
        {
          oldP = p;
        }
        line.setLine(oldP, p);
        g2.draw(line);
        oldP = p;
      }
    });
  }
  
  
  /**
   * Setup method to print the drawing
   */
  public void doPrint()
  {
    HashPrintRequestAttributeSet printSet
            = new HashPrintRequestAttributeSet();
    printSet.add(DialogTypeSelection.NATIVE);
    PrinterJob pj = PrinterJob.getPrinterJob();
    pj.setPrintable(this);
    if (pj.printDialog(printSet))
    {
      try
      {
        pj.print(printSet);
      }
      catch (PrinterException ex)
      {
        JOptionPane.showMessageDialog(this, ex.toString());
      }
    }
  }

  /**
   * Method to print drawing
   *
   * @param gp Given Graphics object
   * @param pf Given pageFormat object
   * @param pageIndex Given pageIndex object
   * @return PAGE_EXIST if the page exists or NO_SUCH_PAGE if not
   * @throws PrinterException
   */
  @Override
  public int print(Graphics gp, PageFormat pf, int pageIndex) throws PrinterException
  {
    Graphics2D g2p = (Graphics2D) gp;
    if (pageIndex == 0)
    {
      g2p.translate(pf.getImageableX(), pf.getImageableY());
      g2p.scale(pf.getImageableWidth() / this.getWidth(),
              pf.getImageableHeight() / this.getHeight());
      super.print(g2p);
      return Printable.PAGE_EXISTS;
    }
    else
    {
      return Printable.NO_SUCH_PAGE;
    }
  }
}
