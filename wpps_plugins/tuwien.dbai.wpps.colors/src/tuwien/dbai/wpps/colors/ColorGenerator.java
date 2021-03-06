/*
 *  ColorGenerator.java
 *
 *  Copyright (c) 1995-2012, The University of Sheffield. See the file
 *  COPYRIGHT.txt in the software or at http://gate.ac.uk/gate/COPYRIGHT.txt
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *  Valentin Tablan, 11/07/2000
 *
 *  $Id: ColorGenerator.java 15333 2012-02-07 13:18:33Z ian_roberts $
 */
package tuwien.dbai.wpps.colors;

import java.util.LinkedList;

import toxi.color.TColor;

/**
 * This class is used to generate random colours that are evenly distributed in
 * the colours space.
 *
 */
public class ColorGenerator {

  /** Debug flag
   */
  @SuppressWarnings("unused")
private static final boolean DEBUG = false;

  /**
   * Creates a new ColorGenerator
   *
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
public ColorGenerator() {
    for(int i = 0; i < 8; i++)availableSpacesList[i] = new LinkedList();
    ColorSpace usedCS = new ColorSpace(0,0,0,255);
    availableSpacesList[0].addLast(new ColorSpace(usedCS.baseR +
                                               usedCS.radius/2,
                                               usedCS.baseG,
                                               usedCS.baseB,
                                               usedCS.radius/2));
    availableSpacesList[1].addLast(new ColorSpace(usedCS.baseR,
                                               usedCS.baseG + usedCS.radius/2,
                                               usedCS.baseB,
                                               usedCS.radius/2));
    availableSpacesList[2].addLast(new ColorSpace(usedCS.baseR +
                                               usedCS.radius/2,
                                               usedCS.baseG + usedCS.radius/2,
                                               usedCS.baseB,
                                               usedCS.radius/2));

    availableSpacesList[3].addLast(new ColorSpace(usedCS.baseR,
                                               usedCS.baseG,
                                               usedCS.baseB + usedCS.radius/2,
                                               usedCS.radius/2));
    availableSpacesList[4].addLast(new ColorSpace(usedCS.baseR +
                                               usedCS.radius/2,
                                               usedCS.baseG,
                                               usedCS.baseB + usedCS.radius/2,
                                               usedCS.radius/2));
    availableSpacesList[5].addLast(new ColorSpace(usedCS.baseR,
                                               usedCS.baseG + usedCS.radius/2,
                                               usedCS.baseB + usedCS.radius/2,
                                               usedCS.radius/2));
  /*
    availableSpacesList[6].addLast(new ColorSpace(usedCS.baseR +
                                               usedCS.radius/2,
                                               usedCS.baseG + usedCS.radius/2,
                                               usedCS.baseB + usedCS.radius/2,
                                               usedCS.radius/2));

  */
  //    Color foo = getNextColor();
  }

  /**
   * Gets the next random colour
   *
   */
  @SuppressWarnings("unchecked")
public TColor getNextColor(){
    ColorSpace usedCS;
    listToRead = listToRead % 8;

    if(availableSpacesList[listToRead].isEmpty()){
      usedCS = (ColorSpace)usedSpacesList.removeFirst();
      availableSpacesList[listToRead].addLast(new ColorSpace(usedCS.baseR,
                                                 usedCS.baseG,
                                                 usedCS.baseB,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(
                                                 usedCS.baseR + usedCS.radius/2,
                                                 usedCS.baseG,
                                                 usedCS.baseB,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(usedCS.baseR,
                                                 usedCS.baseG + usedCS.radius/2,
                                                 usedCS.baseB,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(
                                                 usedCS.baseR + usedCS.radius/2,
                                                 usedCS.baseG + usedCS.radius/2,
                                                 usedCS.baseB,
                                                 usedCS.radius/2));

      availableSpacesList[listToRead].addLast(new ColorSpace(usedCS.baseR,
                                                 usedCS.baseG,
                                                 usedCS.baseB + usedCS.radius/2,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(
                                                 usedCS.baseR + usedCS.radius/2,
                                                 usedCS.baseG,
                                                 usedCS.baseB + usedCS.radius/2,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(usedCS.baseR,
                                                 usedCS.baseG + usedCS.radius/2,
                                                 usedCS.baseB + usedCS.radius/2,
                                                 usedCS.radius/2));
      availableSpacesList[listToRead].addLast(new ColorSpace(
                                                 usedCS.baseR + usedCS.radius/2,
                                                 usedCS.baseG + usedCS.radius/2,
                                                 usedCS.baseB + usedCS.radius/2,
                                                 usedCS.radius/2));

    }
    usedCS = (ColorSpace)availableSpacesList[listToRead].removeFirst();
    TColor res = TColor.newRGB((usedCS.baseR + usedCS.radius/2)/255f,
                          (usedCS.baseG + usedCS.radius/2)/255f,
                          (usedCS.baseB + usedCS.radius/2)/255f );
    usedSpacesList.addLast(usedCS);
    listToRead++;
    res = res.getLightened(0.5f);
    return res;
  }

  /**
   * Represents a colur space. A colour space is a cube in a tridimiensional
   * space (where the axes represent red/green/blue values) defined by a point
   * and a radius(the length of the edge).
   */
  class ColorSpace{
    /**
     * Creates a new ColorSpace
     *
     * @param r
     * @param g
     * @param b
     * @param radius
     */
    ColorSpace(int r, int g, int b, int radius){
      baseR = r;
      baseG = g;
      baseB = b;
      this.radius = radius;
    }

    /**      *
     */
    int baseR, baseG, baseB;
    /**      */
    int radius;
  }

  /**    */
  @SuppressWarnings("rawtypes")
LinkedList[] availableSpacesList = new LinkedList[8];

  /**    */
  @SuppressWarnings("rawtypes")
LinkedList usedSpacesList = new LinkedList();

  /**    */
  int listToRead = 0;

} // ColorGenerator