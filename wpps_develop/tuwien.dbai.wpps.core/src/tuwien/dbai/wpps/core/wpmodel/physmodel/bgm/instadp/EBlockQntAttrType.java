/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 7, 2011 5:47:55 PM
 */
public enum EBlockQntAttrType {
	// --- should be stored in ontology ---
	X_MIN, Y_MIN, X_MAX, Y_MAX,
	DRAW_ID, LAYER_ID,
	// --- can be computed ---
	QNT_WIDTH, QNT_HEIGHT,
	/**
	 * TODO: may be better to have center for every axis separatelly?
	 */
	X_CENTER, Y_CENTER
}
