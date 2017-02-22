/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.xycut;

import java.util.ArrayList;
import java.util.Collection;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 30, 2012 1:15:15 PM
 */
public class XYBlock {
	
//	private Tree xyTree = null;
//	private Position position = null;
	
	public static enum EDirection {NONE, HORIZONTAL, VERTICAL;
	public EDirection opposite() {
		switch (this) {
		case HORIZONTAL:
			return VERTICAL;
		case VERTICAL:
			return HORIZONTAL;
		default:
			throw new GeneralUncheckedException("");
		}
	}
	}
	
	public Collection<IInstanceAdp> getElements() {
		return elements;
	}

	public void setElements(Collection<IInstanceAdp> elements) {
		this.elements = elements;
	}

	public ArrayList<Integer> getProjectionProfile() {
		return projectionProfile;
	}

	public void setProjectionProfile(ArrayList<Integer> projection) {
		this.projectionProfile = projection;
	}

//	public ArrayList<Integer> getYProjection() {
//		return yProjection;
//	}
//
//	public void setYProjection(ArrayList<Integer> yProjection) {
//		this.yProjection = yProjection;
//	}
	public Rectangle2D getArea() {
		return area;
	}

	public void setArea(Rectangle2D area) {
		this.area = area;
	}

	private Collection<IInstanceAdp> elements = null;
	
	private ArrayList<Integer> projectionProfile = null;
	
//	private ArrayList<Integer> yProjection = null;
	
	private Rectangle2D area = null;
	
	private EDirection direction = null;
	
	public EDirection getDirection() {
		return direction;
	}

	public void setDirection(EDirection direction) {
		this.direction = direction;
	}
	
//	public XYBlock(Collection<IInstanceAdp> elements, Rectangle2D area) {
//		this.elements = elements;
//		this.area = area;
//	}
	
	public XYBlock(Collection<IInstanceAdp> elements, Rectangle2D area, EDirection direction) {
		this.elements = elements;
		this.area = area;
		this.direction = direction;
	}
	
	@Override public String toString() {
		return area.toString()+", dir:"+direction;
	}

}
