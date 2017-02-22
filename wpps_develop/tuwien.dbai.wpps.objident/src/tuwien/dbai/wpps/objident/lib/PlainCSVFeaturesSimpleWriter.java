/**
 * 
 */
package tuwien.dbai.wpps.objident.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.objident.command.SaveComparisonObjectsFeaturesCmdHdlr;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair.EExampleType;
import tuwien.dbai.wpps.objident.model.TObject;
import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 10:28:44 AM
 * @see SaveComparisonObjectsFeaturesCmdHdlr
 */
public final class PlainCSVFeaturesSimpleWriter {
	private static final Logger log = Logger.getLogger(PlainCSVFeaturesSimpleWriter.class);
	
	public static final char CSV_SEPARATOR = ';';
	
	public static final String ID = "id";
//	public static final String LOCAL_ID = "localId";
//	public static final String INDIVIDUAL_LOCAL_NAME = "individual";
	public static final String EXAMPLE_TYPE = "selected";
//	public static final String LOCAL_MASTER_ID = "localMasterId";
	public static final String MASTER_ID = "masterId";
	public static final String MASTER_COMPARATION_VALUE = "master";
	public static final String YES_COMPARATION_VALUE = "yes";
	public static final String NO_COMPARATION_VALUE = "no";
	
	public final static void write(File file, List<TObjectComparativePair> comparationList) {
		final Set<TObject> usedMasters = new HashSet<TObject>();
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(file), CSV_SEPARATOR);
			if (comparationList.size()>0) {
				String[] headers = getHeaders(comparationList.get(0).getComparativeObj()
										.getComputedFeatureValues() );
				writer.writeNext( addAdditionalHeaders(headers));
				Iterator<TObjectComparativePair> iter = comparationList.iterator();
				while (iter.hasNext()) {
					TObjectComparativePair pair = iter.next();
					if (!usedMasters.contains(pair.getMasterObj())) {
						writer.writeNext(
								addAdditionalValues(
										featureValuesToStringArr(pair.getMasterObj().getComputedFeatureValues(), headers)
										, 0
										, pair)
						);
						usedMasters.add(pair.getMasterObj());
					}
					writer.writeNext(
							addAdditionalValues(
									featureValuesToStringArr(pair.getComparativeObj().getComputedFeatureValues(), headers)
									, 1
									, pair)
					);
				}
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static final String[] getHeaders(Set<FeatureValue> computedFeatureValueSet) {
		List<String> headers = new ArrayList<String>(computedFeatureValueSet.size());
		Iterator<FeatureValue> iter = computedFeatureValueSet.iterator();
		while (iter.hasNext()) {
			headers.add(iter.next().getFeatureDescription().getSysName());
		}
		Set<String> hSet = new HashSet<String>(headers);
		Preconditions.checkArgument(hSet.size() == headers.size());
		
		return headers.toArray(new String[headers.size()]);
	}
	
	
	private static final String[] featureValuesToStringArr(Set<FeatureValue> computedFeatureValueSet, String[] headers) {
		String[] rez = new String[headers.length];
		for (int i=0; i<headers.length; i++) {
			rez[i] = getFeatureValue(computedFeatureValueSet, headers[i]).valueToString();
		}
		return rez;
	}

	private static final FeatureValue getFeatureValue(Set<FeatureValue> computedFeatureValueSet, String header) {
		Iterator<FeatureValue> iter = computedFeatureValueSet.iterator();
		FeatureValue fv = null;
		while (iter.hasNext()) {
			fv = iter.next();
			if (fv.getFeatureDescription().getSysName().equals(header))
				return fv;
		}
		throw new GeneralUncheckedException(log, "Feature value which has sys name different from other features:"
				+ ((fv==null)?null:fv.getFeatureDescription().getSysName()) );
	}

	private static int N = 3;
	private static final String[] addAdditionalHeaders(String[] headers) {
		String[] rez = new String[headers.length+N];
		rez[0] = ID;
//		rez[1] = LOCAL_ID;
//		rez[2] = INDIVIDUAL_LOCAL_NAME;
		rez[1] = EXAMPLE_TYPE;
//		rez[4] = LOCAL_MASTER_ID;
		rez[2] = MASTER_ID;
		for (int i=0; i<headers.length; i++)
			rez[i+N] = headers[i];
		return rez;
	}
	
	private static final String[] addAdditionalValues(String[] values, int mode, TObjectComparativePair pair) {
		String[] rez = new String[values.length+N];
		TObject o = (mode == 0)?pair.getMasterObj():pair.getComparativeObj();
		rez[0] = ((IRdfResourceAdp)o.getContainedObjects().iterator().next()).getRdfResource().getURI();
//		rez[1] = Integer.toString(o.getId());
//		rez[2] = ((IRdfResourceAdp)o.getContainedObjects().iterator().next()).getRdfResource().getLocalName();
		if (mode == 0) {
			rez[1] = "master";
//			rez[4] = null;
			rez[2] = null;
		}
		else {
			if (pair.getExampleType() == EExampleType.POSITIVE)
				rez[1] = "yes";
			else if (pair.getExampleType() == EExampleType.NEGATIVE)
				rez[1] = "no";
//			rez[4] = Integer.toString(pair.getMasterObj().getId());
			rez[2] = ((IRdfResourceAdp)pair.getMasterObj()
					.getContainedObjects().iterator().next()).getRdfResource().getURI();
		}
		for (int i=0; i<values.length; i++)
			rez[i+N] = values[i];
		return rez;
	}
	
	

}
