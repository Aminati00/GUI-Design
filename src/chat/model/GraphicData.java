/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.model;

import chat.utility.OhmLogger;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * GraphicData class for data handling of drawing
 */
public class GraphicData
{

  /**
   * Reference to OhmLogger
   */
  private static Logger lg = OhmLogger.getLogger();

  /**
   * ArrayList of Figure objects to save figures
   */
  private ArrayList<Figure> figures;

  /**
   * Preferences reference for last folder persistency
   */
  private Preferences prefs;
  
  /**
   * Refernece to last Figure object in figures
   */
  private Figure lastFigure;
  
  /**
   * Constructor which sets prefs, creates figures ArrayList and adds a new
   * Figur object to figures
   */
  public GraphicData()
  {
    prefs = Preferences.userRoot().node(getClass().getName());
    figures = new ArrayList<>();
    lastFigure = new Figure();
    figures.add(lastFigure);
    
  }

  /**
   * Function to get lastFolder for fileChooser
   *
   * @return String for last folder path
   */
  public String getLastFolder()
  {
    String lastFolder = prefs.get("lastFolder", System.getProperty("user.home"));
    return lastFolder;
  }

  /**
   * Puts lastFolder in prefs
   *
   * @param lastFolder String path to folder
   */
  public void setLastFolder(String lastFolder)
  {
    prefs.put("lastFolder", lastFolder);
  }

  /**
   * Adds given Point object to Figur object from figures
   *
   * @param p Given Point object
   */
  public void addPoint(Point p)
  {
    this.getLastFigure().addPoint(p);
  }

  /**
   * Function to getLastFigure
   * @return last Figure object in figures ArrayList
   */
  public Figure getLastFigure()
  {
    return lastFigure;
  }
  
  /**
   * Adds new Figure to figures
   */
  public void addFigure()
  {
    lastFigure = new Figure();
    figures.add(lastFigure);
  }
  
  /**
   * Adds new Figure to figures
   */
  public void addFigure(Figure figure)
  {
    lastFigure = figure;
    figures.add(lastFigure);
    this.addFigure();
  }
  
  /**
   * Method to return unmodifiable list of figures
   *
   * @return undmodifiable list of figures
   */
  public List<Figure> getFigures()
  {
    return Collections.unmodifiableList(figures);
  }

  /**
   * Saves figures at given parameter
   *
   * @param filename Given File object
   * @throws FileNotFoundException
   * @throws IOException
   */
  public void saveFigures(File filename) throws FileNotFoundException, IOException
  {
    FileOutputStream fos = new FileOutputStream(filename);
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(figures);
    oos.flush();
    oos.close();
    lg.info("Saved figures");
  }

  /**
   * Loads figures from given parameter and sets findex
   *
   * @param filename Given File object
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public void loadFigures(File filename) throws FileNotFoundException, IOException, ClassNotFoundException
  {
    FileInputStream fis = new FileInputStream(filename);
    BufferedInputStream bis = new BufferedInputStream(fis);
    ObjectInputStream ois = new ObjectInputStream(bis);
    Object obj = ois.readObject();
    if (obj instanceof ArrayList)
    {
      figures = (ArrayList<Figure>) obj;
    }
    ois.close();
    int size = figures.size() - 1;
    lg.info("Loaded figures with size: " + size);
  }
}
