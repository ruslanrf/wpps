/**
 * 
 */
package tuwien.dbai.wpps.objident.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import tuwien.dbai.wpps.common.Mapping1_1N_generic;
import tuwien.dbai.wpps.common.Mapping1_1N_generic.ECollectionType;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair.EExampleType;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 9:54:41 PM
 */
public class PlainCSVFeaturesWriter2 {
private static final Logger log = Logger.getLogger(PlainCSVFeaturesSimpleWriter.class);
	
	public static final String ID = "id";
	public static final String EXAMPLE_TYPE = "selected";
	public static final String MASTER_ID = "masterId";
	public static final String MASTER_COMPARATION_VALUE = "master";
	public static final String YES_COMPARATION_VALUE = "yes";
	public static final String NO_COMPARATION_VALUE = "no";
	
	public final static void write(File file, List<TObjectComparativePair> comparationList) {
		if (comparationList.size() == 0) {
if (log.isInfoEnabled()) log.info("There are no data rto save into the file "+file.getName());
			return;
		}
		
		String[] headersArr = getHeaders(comparationList.get(0).getMasterObj().getComputedFeatureValues());
		CellProcessor[] processors = getProcessors(headersArr.length);
		
		ICsvMapWriter mapWriter = null;
		try {
			mapWriter = new CsvMapWriter(new FileWriter(file),
			        CsvPreference.STANDARD_PREFERENCE);
			mapWriter.writeHeader(headersArr);
//			Map<String, String> consideredPair = new HashMap<String, String>();
			Mapping1_1N_generic<String, String> consideredPairs = new Mapping1_1N_generic
					<String, String>(ECollectionType.SET);
//			final Set<String> usedId = new HashSet<String>();
			final Set<String> usedMasterId = new HashSet<String>();
			for (TObjectComparativePair pair : comparationList) {
				String compObjId = getId(pair.getComparativeObj());
				String mastObjId = getId(pair.getMasterObj());
				if (!consideredPairs.contains(mastObjId, compObjId)) {
					consideredPairs.addMapping(mastObjId, compObjId);
					consideredPairs.addMapping(compObjId, mastObjId);
					if (!usedMasterId.contains(mastObjId)) {
						usedMasterId.add(mastObjId);
						mapWriter.write(
							fillWithFeatureValues(pair.getMasterObj(), true
									, null, null)
						, headersArr, processors);
					}
					mapWriter.write(
					fillWithFeatureValues(pair.getComparativeObj(), false
							, pair.getExampleType(), pair.getMasterObj())
					, headersArr, processors);
				}
				else
					log.warn("Object "+mastObjId+" and "+compObjId+" are compared several times");
				
				
				
//				if (!compObjId.equals(mastObjId)) {
//					if (!usedId.contains(compObjId)) {
//						if (!usedId.contains(mastObjId) || usedMasterId.contains(mastObjId)) {
//							if (!usedMasterId.contains(mastObjId)) {
//								usedId.add(mastObjId);
//								usedMasterId.add(mastObjId);
//								mapWriter.write(
//									fillWithFeatureValues(pair.getMasterObj(), true
//											, null, null)
//								, headersArr, processors);
//							}
//							usedId.add(compObjId);
//							mapWriter.write(
//									fillWithFeatureValues(pair.getComparativeObj(), false
//											, pair.getExampleType(), pair.getMasterObj())
//									, headersArr, processors);
//						}
//						else
//							log.warn("Master object "+mastObjId+" was already added as comparative object");
//					}
//					else
//						log.warn("Comparative object "+compObjId+" was already added");
//				}
//				else
//					log.warn("Master object "+mastObjId+" is compared with itself");
				
				
				
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if( mapWriter != null ) {
				try {
					mapWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private static final String[] getHeaders(Collection<FeatureValue> computedFeatureValueSet) {
		List<String> headers = new ArrayList<String>(computedFeatureValueSet.size());
		headers.add(ID);
		headers.add(EXAMPLE_TYPE);
		headers.add(MASTER_ID);
		Iterator<FeatureValue> iter = computedFeatureValueSet.iterator();
		while (iter.hasNext()) {
			headers.add(iter.next().getFeatureDescription().getSysName());
		}
// TEST FOR REPETITIONS
Set<String> hSet = new HashSet<String>(headers);
Preconditions.checkArgument(hSet.size() == headers.size(), "There are repetitions in features.");
		return headers.toArray(new String[headers.size()]);
	}
	
	private static final Map<String, Object> fillWithFeatureValues(TObject tObject
			, boolean isMaster
			, EExampleType exType
			, TObject tObjectMaster) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ID, getId(tObject));
		if (isMaster) {
			map.put(EXAMPLE_TYPE, "master");
			map.put(MASTER_ID, null);
		}
		else {
			if (exType == EExampleType.POSITIVE)
				map.put(EXAMPLE_TYPE, "yes");
			else
				map.put(EXAMPLE_TYPE, "no");
			map.put(MASTER_ID, getId(tObjectMaster));
		}
		
		Collection<FeatureValue> computedFeatureValueSet = tObject.getComputedFeatureValues();
		for (FeatureValue val : computedFeatureValueSet) {
			map.put(val.getFeatureDescription().getSysName(), val.csvValueFormat());
		}
		return map;
	}
	
	private static final String getId(TObject o) {
		if (o.getRdfTargetObject() instanceof IRdfResourceAdp)
			return ((IRdfResourceAdp)o.getRdfTargetObject()).getRdfResource().getURI();
		else {
			log.warn("Target object "+o+" does not have a relation to the ontoogy. Session unique id is used.");
			return String.valueOf(o.getId());
		}
	}
	
	private static CellProcessor[] getProcessors(int headersNum) {
		Preconditions.checkArgument(headersNum>0);
		final CellProcessor[] processors = new CellProcessor[headersNum];
		processors[0] = new NotNull();
		processors[1] = new NotNull();
		processors[2] = new Optional();
		for (int i=3; i<headersNum; i++)
			processors[i] = new Optional();
		return processors;
	}
	
	public static int PRECISION = 6;
	
//	private static Object processVal(FeatureValue fval) {
//		Object value = fval.getValue();
//		if (value != null) {
//			if (value instanceof Double || value instanceof Float) {
//				if (value instanceof Double) {
//					if (Double.isNaN((double)value))
//							throw new GeneralUncheckedException(log, "NAN"+ " sysName: "+fval.getFeatureDescription().getSysName());
//					else
//						if (Double.isInfinite((double)value))
//							throw new GeneralUncheckedException(log, "Infinite"+ " sysName: "+fval.getFeatureDescription().getSysName());
//					value = MathUtils.round((double)value, PRECISION);
//				}
//				else {
//					if (Float.isNaN((float)value))
//						throw new GeneralUncheckedException(log, "NAN"+ " sysName: "+fval.getFeatureDescription().getSysName());
//				else
//					if (Float.isInfinite((float)value))
//						throw new GeneralUncheckedException(log, "Infinite"+ " sysName: "+fval.getFeatureDescription().getSysName());
//					value = MathUtils.round((double)(float)value, PRECISION);
//				}
//			} else if (value instanceof TColor) {
//				TColor tColor = (TColor)value;
//				value = Integer.toString(tColor.toARGB());
//			}
//		}
//		return value;
//	}
	
//	public String valueToString() {
//		String rez = null;
//		if (value != null) {
//			if (value instanceof Double || value instanceof Float) {
//				Double dRez = null;
//				if (value instanceof Double) {
//					if (Double.isNaN((double)value))
//							throw new GeneralUncheckedException(log, "NAN"+ " sysName: "+featureDesc.getSysName());
//					else
//						if (Double.isInfinite((double)value))
//							throw new GeneralUncheckedException(log, "Infinite"+ " sysName: "+featureDesc.getSysName());
//					dRez = MathUtils.round((double)value, PRECISION);
//				}
//				else {
//					if (Float.isNaN((float)value))
//						throw new GeneralUncheckedException(log, "NAN"+ " sysName: "+featureDesc.getSysName());
//				else
//					if (Float.isInfinite((float)value))
//						throw new GeneralUncheckedException(log, "Infinite"+ " sysName: "+featureDesc.getSysName());
//				dRez = MathUtils.round((double)(float)value, PRECISION);
//				}
//				
//				NumberFormat f = NumberFormat.getInstance();
//				f.setGroupingUsed(false);
//				rez = f.format(dRez);
//				
//				
//			} else if (value instanceof TColor) {
//				TColor tColor = (TColor)value;
//				rez = Integer.toString(tColor.toARGB());
//			}
//			else {
//				rez = value.toString();
//			}
//		}
//		return (rez==null)?"null":rez;
//	}
	
	

}
