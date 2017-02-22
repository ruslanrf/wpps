/**
 * 
 */
package tuwien.dbai.wpps.objident.features;

import java.text.NumberFormat;

import org.apache.commons.math.util.MathUtils;
import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.objident.CsvCompatible;
import tuwien.dbai.wpps.objident.features.descr.AFeatureDescription;

import com.google.common.base.Objects;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 31, 2012 9:14:36 PM
 */
public class FeatureValue implements CsvCompatible {
	private static final Logger log = Logger.getLogger(FeatureValue.class);
	
	private final AFeatureDescription featureDesc;
	public AFeatureDescription getFeatureDescription() {
		return featureDesc;
	}
	
//	private final EventBus eventBus;
	
	private final Object value;
	public Object getValue() {
		return value;
	}
	
	public Class<?> getValueType() {
		return featureDesc.getValueType();
	}
	
	public FeatureValue(
//			final EventBus eventBus
			final AFeatureDescription featureDesc
			, final Object value
			) {
		if (value != null && !featureDesc.getValueType().isInstance(value))
			throw new GeneralUncheckedException(log, "Value "+value+" is of wrong type! it should be "
					+featureDesc.getValueType().getName());
//		this.eventBus = eventBus;
		this.featureDesc = featureDesc;
		this.value = value;
	}
	
	public static int PRECISION = 6;
	public static int ALPHA_PRECISION = 2;
	
	public String valueToString() {
		String rez = null;
		if (value != null) {
			if (value instanceof Double || value instanceof Float) {
				Double dRez = null;
				if (value instanceof Double) {
					if (Double.isNaN((double)value))
							throw new GeneralUncheckedException(log, "NAN"+ " sysName: "+featureDesc.getSysName());
					else
						if (Double.isInfinite((double)value))
							throw new GeneralUncheckedException(log, "Infinite"+ " sysName: "+featureDesc.getSysName());
					dRez = MathUtils.round((double)value, PRECISION);
				}
				else {
					if (Float.isNaN((float)value))
						throw new GeneralUncheckedException(log, "NAN"+ " sysName: "+featureDesc.getSysName());
				else
					if (Float.isInfinite((float)value))
						throw new GeneralUncheckedException(log, "Infinite"+ " sysName: "+featureDesc.getSysName());
				dRez = MathUtils.round((double)(float)value, PRECISION);
				}
				
				NumberFormat f = NumberFormat.getInstance();
				f.setGroupingUsed(false);
				rez = f.format(dRez);
				
				
			} else if (value instanceof TColor) {
				TColor tColor = (TColor)value;
				rez = Integer.toString(tColor.toARGB());
			}
			else {
				rez = value.toString();
			}
		}
		return (rez==null)?"null":rez;
	}
	
	@Override
	public Object csvValueFormat() {
		Object rez = value;
		if (rez != null) {
			if (rez instanceof Double || rez instanceof Float) {
				if (rez instanceof Double) {
					if (Double.isNaN((double)rez))
							throw new GeneralUncheckedException(log, "NAN"+ " sysName: "+featureDesc.getSysName());
					else
						if (Double.isInfinite((double)rez))
							throw new GeneralUncheckedException(log, "Infinite"+ " sysName: "+featureDesc.getSysName());
					rez = MathUtils.round((double)rez, PRECISION);
				}
				else {
					if (Float.isNaN((float)rez))
						throw new GeneralUncheckedException(log, "NAN"+ " sysName: "+featureDesc.getSysName());
				else
					if (Float.isInfinite((float)rez))
						throw new GeneralUncheckedException(log, "Infinite"+ " sysName: "+featureDesc.getSysName());
					rez = MathUtils.round((double)(float)rez, PRECISION);
				}
			} else if (rez instanceof TColor) {
				TColor tColor = (TColor)rez;
				rez = Integer.toString(tColor.toARGB());
			}
		}
		return rez;
	}
	
	public String valueTypeToString() {
		return (featureDesc.getValueType()==null)?"null":featureDesc.getValueType().toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof FeatureValue) {
			final FeatureValue fv = (FeatureValue)o;
			return featureDesc.equals(fv.featureDesc) &&
					value != null && value.equals(fv.value);
		}
		else
			return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(featureDesc, value);
	}
	
	@Override
	public String toString() {
		return "{"+featureDesc.getSysName()+": "+valueToString()+"}";
	}
	
}
