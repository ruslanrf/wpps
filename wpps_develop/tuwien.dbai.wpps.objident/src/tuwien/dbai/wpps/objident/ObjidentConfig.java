/**
 * 
 */
package tuwien.dbai.wpps.objident;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.swt.graphics.RGB;

import toxi.color.TColor;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair.EExampleType;

import com.google.inject.Singleton;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 12, 2012 8:05:21 PM
 */
@Singleton
public class ObjidentConfig {
	
	private static final File wppsConfig = new File(ObjIdentActivator.getPluginFolder(), "config/wpps-config.xml");
	public File getWPPSConfig() {
		return wppsConfig;
	}

	private final Set<EIMInstType> consideredObjectTypes;
	private final Set<Class<? extends IInstanceAdp>> consideredObjectJavaTypes;
	
	public Set<EIMInstType> getConsideredObjectTypes() {
		return consideredObjectTypes;
	}
	public Set<Class<? extends IInstanceAdp>> getConsideredObjectJavaTypes() {
		return consideredObjectJavaTypes;
	}
	
	public EIMInstType[] getConsideredObjectTypesAsArray() {
		return (EIMInstType[])consideredObjectTypes.toArray(new EIMInstType[consideredObjectTypes.size()]);
	}
	
	// TODO: ? extends IInstanceAdp
	@SuppressWarnings("unchecked")
	public Class<IInstanceAdp>[] getConsideredObjectJavaTypesAsArray() {
		return (Class<IInstanceAdp>[])consideredObjectJavaTypes
				.toArray(new Class[consideredObjectJavaTypes.size()]);
	}
	
	public EIMInstType[] getConsideredObjectTypesWithText() {
		return DEFAULT_CONSIDERED_OBJECT_TYPES_WITH_TEXT;
	}
	
	public EIMInstType[] getEditableObjectTypes() {
		return DEFAULT_EDITABLE_OBJECT_TYPES;
	}
	
	// ===========
	// === GUI ===
	// ===========
	
	private final TObjectComparativePair.EExampleType comparationObjectSelectionMode;
	public TObjectComparativePair.EExampleType getComparationObjectSelectionMode() {
		return comparationObjectSelectionMode;
	}
	
	private final RGB maserBrowserColor;
	public RGB getMaserBrowserColor() {
		return maserBrowserColor;
	}

	private final RGB secondBrowserColor;
	public RGB getSecondBrowserColor() {
		return secondBrowserColor;
	}
	
	private final TColor targetColor;
	public TColor getTargetColor() {
		return targetColor;
	}
	
	private final TColor contextColor;
	public TColor getContextColor() {
		return contextColor;
	}
	
	private final TColor containedContextObjectsColor;
	//TColor.newRGBA(0.5686f, 0.3607f, 0.5137f, 1f); // Antique Fuchsia
	public TColor getContainedContextObjectsColor() {
		return containedContextObjectsColor;
	}
	
	private final TColor positiveExampleColor;
	public TColor getPositiveExampleColor() {
		return positiveExampleColor;
	}
	private final TColor negativeExampleColor;
	public TColor getNegativeExampleColor() {
		return negativeExampleColor;
	}
	private TColor masterExampleColor;
	public TColor getMasterExampleColor() {
		return masterExampleColor;
	}
	
	public ObjidentConfig() {
		consideredObjectTypes = DEFAULT_CONSIDERED_OBJECT_TYPES;
		consideredObjectJavaTypes = new HashSet<Class<? extends IInstanceAdp>>();
		for(EIMInstType t : consideredObjectTypes) {
			consideredObjectJavaTypes.add(t.getJavaInterface());
		}
		
		// --- GUI ---
		
		comparationObjectSelectionMode = EExampleType.POSITIVE;
		
		maserBrowserColor = new RGB(255, 223, 0);
		secondBrowserColor = new RGB(193, 84, 193); // Fuchsia (Crayola) (193, 84, 193)
		
		targetColor = TColor.newRGBA(1f, 0f, 1f, 1f); // Fuchsia (Crayola)
		contextColor = TColor.newRGBA(0.7804f, 0.2627f, 0.4588f, 1f); // Fuchsia Rose
			//TColor.newRGBA(0.7568f, 0.3294f, 0.7568f, 1f); // Fandango
		containedContextObjectsColor = TColor.newRGBA(0.7568f, 0.3294f, 0.7568f, 1f);
			//TColor.newRGBA(0.5686f, 0.3607f, 0.5137f, 1f); // Antique Fuchsia
		positiveExampleColor = TColor.newRGBA(0f, 1f, 0f, 1f);
		negativeExampleColor = TColor.newRGBA(0.5451f, 0.5373f, 0.5373f, 1f); // RGB(139, 137, 137) //Snow 4  http://www.tayloredmktg.com/rgb/
		masterExampleColor = TColor.newRGBA(1f, 0.8745f, 0f, 1f); //Gold. rgb(255,223,0) http://www.rapidtables.com/web/color/Gold_Color.htm
		
	}
	
	private static final Set<EIMInstType> DEFAULT_CONSIDERED_OBJECT_TYPES
		= new LinkedHashSet<EIMInstType>();
	
	static {
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_BUTTON);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_CHECKBOX);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_FILE_UPLOAD);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_IMAGE);
//		DEFAULT_CONSIDERED_OBJECT_TYPES.add(IHtmlLink.class);
//		DEFAULT_CONSIDERED_OBJECT_TYPES.add(IHtmlListItem.class);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_PASSWORD_INPUT);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_RADIOBUTTON);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_SELECT);
//		DEFAULT_CONSIDERED_OBJECT_TYPES.add(IHtmlTableCell.class);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_TEXT);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_TEXT_AREA);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_TEXT_INPUT);
	}
	
	private static final EIMInstType[] DEFAULT_CONSIDERED_OBJECT_TYPES_WITH_TEXT = new EIMInstType[] {
		EIMInstType.HTML_BUTTON,
		EIMInstType.HTML_FILE_UPLOAD,
		EIMInstType.HTML_SELECT,
		EIMInstType.HTML_TEXT,
		EIMInstType.HTML_TEXT_AREA,
		EIMInstType.HTML_TEXT_INPUT,
	};
	
	private static final EIMInstType[] DEFAULT_EDITABLE_OBJECT_TYPES = new EIMInstType[] {
		EIMInstType.HTML_CHECKBOX,
		EIMInstType.HTML_FILE_UPLOAD,
		EIMInstType.HTML_PASSWORD_INPUT,
		EIMInstType.HTML_RADIOBUTTON,
		EIMInstType.HTML_SELECT,
		EIMInstType.HTML_TEXT_AREA,
		EIMInstType.HTML_TEXT_INPUT
	};
	
}
