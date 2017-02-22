/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.vips;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 27, 2012 2:14:10 PM
 */
public class Separator {
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setSubWP1(Block subWP1) {
		this.subWP1 = subWP1;
	}

	public void setSubWP2(Block subWP2) {
		this.subWP2 = subWP2;
	}

	public void setStartPx(int startPx) {
		this.startPx = startPx;
	}

	public void setEndPx(int endPx) {
		this.endPx = endPx;
	}

	public static enum Direction {HORIZONTAL, VERTICAL}
	
	private Direction direction;
	public Direction getDirection() {
		return direction;
	}

	private Block subWP1;
	public Block getSubWP1() {
		return subWP1;
	}

	private Block subWP2;
	public Block getSubWP2() {
		return subWP2;
	}
	
	private int startPx;
	public int getStartPx() {
		return startPx;
	}
	
	private int endPx;
	public int getEndPx() {
		return endPx;
	}
	
	public int getWidth() {
		return endPx-startPx;
	}

	public Separator(Block subWP1, Block subWP2, Direction direction, int startPx, int endPx) {
		this.subWP1 = subWP1;
		this.subWP2 = subWP2;
		this.direction = direction;
		this.startPx = startPx;
		this.endPx = endPx;
	}


}
