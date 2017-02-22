/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.javaframe;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IJavaQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 4, 2012 3:19:45 PM
 */
@Deprecated
public class JavaQntBlock implements IJavaQntBlock, IQntBlock {

	protected JavaQntBlock() {}
	
	public JavaQntBlock(final Rectangle2D rect) {
		xMin = rect.xMin;
		yMin = rect.yMin;
		xMax = rect.xMax;
		yMax = rect.yMax;
	}
	
	// =================================
	//   As Qnt Block 
	// =================================
		
	protected double xMin = Double.NaN;
	protected double yMin = Double.NaN;
	protected double xMax = Double.NaN;
	protected double yMax = Double.NaN;
		
		
	@Override
	public Double getXMin() {
		return xMin;
	}

	@Override
	public Double getYMin() {
		return yMin;
	}

	@Override
	public Double getXMax() {
		return xMax;
	}

	@Override
	public Double getYMax() {
		return yMax;
	}

	@Override
	public Double getWidth() {
		return xMax - xMin;
	}

	@Override
	public Double getHeight() {
		return yMax - yMin;
	}

//	// TODO: Implement!
//	@Override
//	public Point2D getCentre() {
//		return null;
//	}

//	// TODO: Implement!
//	@Override
//	public double getRelationAsDouble(IAbstractBlock refInst,
//			EQntBlockRelationType relationClass) {
//		return 0;
//	}

	// ==================
	// 
	// ==================
	
	
	// ==================
	// IInstanceAdp
	// ==================
	
	@Override
	public <U extends IInstanceAdp> boolean canAs(Class<U> view) {
		if (view.isInstance(this))
			return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U extends IInstanceAdp> U as(Class<U> view) {
		return (U)this;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getArea()
	 */
	@Override
	public Rectangle2D getArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getNorthArea()
	 */
	@Override
	public Rectangle2D getNorthArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getNorthEastArea()
	 */
	@Override
	public Rectangle2D getNorthEastArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getEastArea()
	 */
	@Override
	public Rectangle2D getEastArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getSouthEastArea()
	 */
	@Override
	public Rectangle2D getSouthEastArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getSouthArea()
	 */
	@Override
	public Rectangle2D getSouthArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getSouthWestArea()
	 */
	@Override
	public Rectangle2D getSouthWestArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getWestArea()
	 */
	@Override
	public Rectangle2D getWestArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getNorthWestArea()
	 */
	@Override
	public Rectangle2D getNorthWestArea() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock#getRelationAsDouble(tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp, tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType)
	 */
	@Override
	public Double getRelationAsDouble(IInstanceAdp refInst,
			EBlockQntRelationType relationClass) {
		// TODO Auto-generated method stub
		return 0d;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp#getString()
	 */
	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

//	/* (non-Javadoc)
//	 * @see tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp#mark(java.lang.String)
//	 */
//	@Override
//	public void addMarker(String mark) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* (non-Javadoc)
//	 * @see tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp#getMarker()
//	 */
//	@Override
//	public String getMarker() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/* (non-Javadoc)
//	 * @see tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp#addGroupMarker(java.lang.String)
//	 */
//	@Override
//	public void addGroupMarker(String mark) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/* (non-Javadoc)
//	 * @see tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp#getGroupMarker()
//	 */
//	@Override
//	public String getGroupMarker() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Integer getDrawId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public boolean hasDrawId() {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public Double getXCenter() {
		// TODO Auto-generated method stub
		return 0d;
	}

	@Override
	public Double getYCenter() {
		// TODO Auto-generated method stub
		return 0d;
	}

//	@Override
//	public <T extends IInstanceAdp> T makeAs(Class<T> view) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	// ==================
	// 
	// ==================
		
}
