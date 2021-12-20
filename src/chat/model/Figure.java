/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat.model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for single figure data
 */
public class Figure implements Serializable
{

  /**
   * ArrayList of Point objects
   */
  private ArrayList<Point> points;
  
  /**
   * Last Point object
   */
  private Point lastPoint = null;
  
  /**
   * Constructor which creates points ArrayList
   */
  public Figure()
  {
    points = new ArrayList<>();
  }

  /**
   * Adds given Point object to points
   *
   * @param p Given Point object
   */
  public void addPoint(Point p)
  {
    points.add(p);
    lastPoint = p;
  }
  
  /**
   * Function to return last Point object from points
   * @return 
   */
  public Point getLastPoint()
  {
    return lastPoint;
  }
  
  
  /**
   * Method to return unmodifiable list of points
   *
   * @return undmodifiable list of points
   */
  public List<Point> getPoints()
  {
    return Collections.unmodifiableList(points);
  }
}
