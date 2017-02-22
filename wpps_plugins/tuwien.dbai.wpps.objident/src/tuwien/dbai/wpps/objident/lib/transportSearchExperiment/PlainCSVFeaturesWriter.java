/**
 * 
 */
package tuwien.dbai.wpps.objident.lib.transportSearchExperiment;

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

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.objident.CsvCompatible;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TObjectExtended;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 15, 2012 2:29:40 AM
 */
public class PlainCSVFeaturesWriter {
	private static final Logger log = Logger.getLogger(PlainCSVFeaturesWriter.class);

	public static final String SCENARIO = "Scenario";
	public static final String WEB_PAGE_ID = "WebPageId";
	public static final String WEB_FORM_ATTR_TYPE = "ObjectAnnotation";
	public static final String OBJ_ID = "ObjectId";
	
	public final static void write(File file, Collection<TObjectExtended> objCol) {
		if (objCol.size() == 0) {
			if (log.isInfoEnabled()) log.warn("There are no data to save into the file "+file.getName());
						return;
		}
		String[] headersArr = getHeaders(objCol.iterator().next().gettObject().getComputedFeatureValues());
		CellProcessor[] processors = getProcessors(headersArr.length);
		ICsvMapWriter mapWriter = null;
		try {
			mapWriter = new CsvMapWriter(new FileWriter(file),
			        CsvPreference.STANDARD_PREFERENCE);
			mapWriter.writeHeader(headersArr);
			for (TObjectExtended o : objCol) {
				mapWriter.write(fillWithFeatureValues(o)
					, headersArr, processors);
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
		headers.add(SCENARIO);
		headers.add(WEB_PAGE_ID);
		headers.add(WEB_FORM_ATTR_TYPE);
		headers.add(OBJ_ID);
		Iterator<FeatureValue> iter = computedFeatureValueSet.iterator();
		while (iter.hasNext()) {
			headers.add(iter.next().getFeatureDescription().getSysName());
		}
// TEST FOR REPETITIONS
Set<String> hSet = new HashSet<String>(headers);
Preconditions.checkArgument(hSet.size() == headers.size(), "There are repetitions in features.");
		return headers.toArray(new String[headers.size()]);
	}
	
	private static CellProcessor[] getProcessors(int headersNum) {
		Preconditions.checkArgument(headersNum>0);
		final CellProcessor[] processors = new CellProcessor[headersNum];
		processors[0] = new NotNull();
		processors[1] = new NotNull();
		processors[2] = new NotNull();
		processors[3] = new NotNull();
		for (int i=4; i<headersNum; i++)
			processors[i] = new Optional();
		return processors;
	}
	
	private static final Map<String, Object> fillWithFeatureValues(TObjectExtended tObject) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SCENARIO, csvValueFormat(tObject.getScenario()));
		map.put(WEB_PAGE_ID, tObject.getWebPageId());
		map.put(WEB_FORM_ATTR_TYPE, csvValueFormat(tObject.getWebFormElement()));
		map.put(OBJ_ID, getId(tObject.gettObject()));
		Collection<FeatureValue> computedFeatureValueSet = tObject.gettObject().getComputedFeatureValues();
		for (FeatureValue val : computedFeatureValueSet) {
			map.put(val.getFeatureDescription().getSysName(), val.csvValueFormat());
		}
		return map;
	}
	
	public static Object csvValueFormat(Object data) {
		return (data instanceof CsvCompatible)?((CsvCompatible)data).csvValueFormat()
				:data.toString();
	}
	
	private static final String getId(TObject o) {
		if (o.getRdfTargetObject() instanceof IRdfResourceAdp)
			return ((IRdfResourceAdp)o.getRdfTargetObject()).getRdfResource().getURI();
		else {
			log.warn("Target object "+o+" does not have a relation to the ontoogy. Session unique id is used.");
			return String.valueOf(o.getId());
		}
	}
	
	
}
