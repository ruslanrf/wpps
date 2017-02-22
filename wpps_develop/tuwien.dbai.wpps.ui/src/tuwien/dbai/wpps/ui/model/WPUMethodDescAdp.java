/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import org.eclipse.swt.graphics.RGB;

import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 13, 2012 5:51:58 PM
 */
public class WPUMethodDescAdp {
	
	public WPUMethodDescAdp(AWPUMethodDescription desc, RGB color) {
		this.desc = desc;
		this.color = color;
	}
	
	private final AWPUMethodDescription desc;
	public AWPUMethodDescription getDescription() {
		return desc;
	}
	
	private final RGB color;
	public RGB getColor() {
		return color;
	}

}
