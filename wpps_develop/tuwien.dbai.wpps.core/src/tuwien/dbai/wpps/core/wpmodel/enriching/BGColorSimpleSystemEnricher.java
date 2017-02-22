/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.enriching;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.optimization.FunctionWithMemory;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVOQntAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpl.PlainVisualObjectImpl;

import com.google.inject.Inject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 26, 2012 9:42:28 PM
 */
public class BGColorSimpleSystemEnricher extends AWPModelSystemEnricher {
	private static final Logger log = Logger.getLogger(BGColorSimpleSystemEnricher.class);
	
	private IPredicate<IInstanceAdp> predicate = null;
	
	private final TColor DEFAULT_BG_COLOR = (TColor)TColor.WHITE;
	
	private final FunctionWithMemory<IInstanceAdp, Boolean> plainVO_qntBlock_hasDrawId
		= new FunctionWithMemory<IInstanceAdp, Boolean>(
				new IFunction<IInstanceAdp, Boolean>() {
					@Override public Boolean apply(IInstanceAdp avar) {
						return avar.canAs(IPlainVisualObject.class)
								&& avar.canAs(IQntBlock.class)
								&& avar.as(IQntBlock.class).getDrawId() != null;
		} }	);
	
	private final FunctionWithMemory<IInstanceAdp, Boolean> plainVO_withTransparentBGColor
		= new FunctionWithMemory<IInstanceAdp, Boolean>(
				new IFunction<IInstanceAdp, Boolean>() {
					@Override public Boolean apply(IInstanceAdp avar) {
						return avar.as(IPlainVisualObject.class).transparentBGColor();
		} }	);
	
	
	/**
	 * Has Draw ID and has no Background Color or it is transparent.
	 */
	private final IPredicate<IInstanceAdp> noBGColorPredicate
			= new IPredicate<IInstanceAdp>() {
				@Override public Boolean apply(IInstanceAdp avar) {
					if (plainVO_qntBlock_hasDrawId.apply(avar)) {
						return plainVO_withTransparentBGColor.apply(avar);
					}
					return false;
				} } ; 
				
	
	private IIEBasisAPI api = null;
	private PlainVisualObjectImpl plainVisualObjectImpl = null;
	
	private final WPPSConfig config;
	
	@Inject
	public BGColorSimpleSystemEnricher(
			final WPPSConfig config
			, final IIEBasisAPI api
			, final PlainVisualObjectImpl plainVisualObjectImpl
			) {
		super();
		this.config = config;
		this.api = api;
		this.plainVisualObjectImpl = plainVisualObjectImpl;
	}
	
	public void init() {
		super.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"BGColorEnricher. Initialization.");
		_canInit();
	}
	
	public void init(final IPredicate<IInstanceAdp> predicate) {
		super.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"BGColorEnricher. Initialization.");
		_canInit();
		this.predicate = predicate;
	}
	
	/**
	 * @param clazz class to be considered.
	 * @param typArr relations to be set.
	 */
	@SuppressWarnings("unchecked")
	public void init(final Class<? extends IInstanceAdp> clazz) {
		super.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"BGColorEnricher. Initialization.");
		_canInit();
		initWithClasses(new Class[]{clazz});
	}
	
	/**
	 * @param clazzArr classes to be considered.
	 * @param typArr relations to be set.
	 */
	public void init(final Class<? extends IInstanceAdp>[] clazzArr
			, final EBlockQltRelation... typArr) {
		super.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"BGColorEnricher. Initialization.");
		_canInit();
		initWithClasses(clazzArr);
	}
	
	private final void initWithClasses(final Class<? extends IInstanceAdp>[] clazzArr) {
		this.predicate = new IPredicate<IInstanceAdp>() {
			@Override
			public Boolean apply(IInstanceAdp avar) {
				for (int i=0; i<clazzArr.length; i++) {
					if (avar.canAs(clazzArr[i]))
						return true;
				}
				return false;
			}
		};
	}
	
	private final void _canInit() {
		if (!config.getCreateInOntology().contains(EBlockQntAttrType.DRAW_ID)
				&& !config.getComputeByRequestBasedOnQntFeatures().contains(EBlockQntAttrType.DRAW_ID))
			new GeneralUncheckedException(log, "Draw id cannot be acquired.");
		if (!config.getCreateInOntology().contains(EVOQntAttrType.BG_RGBA_COLOR) &&
				!config.getComputeByRequestBasedOnQntFeatures().contains(EVOQntAttrType.BG_RGBA_COLOR))
			new GeneralUncheckedException(log, "Background color cannot be acquired.");
	}
	
//	private final 
	
	@SuppressWarnings("unchecked")
	@Override
	protected void _enrich() {
		
		final IPredicate<IInstanceAdp> predUnion = new IPredicate<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar) {
				return noBGColorPredicate.apply(avar)
						&& (predicate == null
						|| predicate.apply(avar));
			}
		};
		
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Getting objects");
		// get objects without background as qntBlocks.
		IResults vObjWOBGColor = api.getObjectsByType2(IQntBlock.class, predUnion, IQntBlock.class);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Sorting objects");
		// sort antBlocks by drawID in descending order.
		vObjWOBGColor = api.orderBy(vObjWOBGColor,
				new IFunction<IInstanceAdp, Integer>() {
					@Override public Integer apply(IInstanceAdp avar) {
						return avar.as(IQntBlock.class).getDrawId();
					}
				}
				, 1);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. For every obj without BGColor");
		for (IInstanceAdp tmpAdp : vObjWOBGColor.getResultContent()) {
			final IPlainVisualObject voWOBGColor = tmpAdp.as(IPlainVisualObject.class);
			final IQntBlock qntBlock = tmpAdp.as(IQntBlock.class);
			final int di = qntBlock.getDrawId();
			
			final Integer[] lastDI = new Integer[]{null};
			final IQntBlock[] lastqntBlock = new IQntBlock[]{null};
			
			IResults tmpRes = api.getObjectsIntersectingArea(qntBlock.getArea(), 
					new IGenericIEFilter<IQntBlock>() {
						@Override public EFilterResult apply(IQntBlock avar) {
							Integer tmpDI = null;
							if (plainVO_qntBlock_hasDrawId.apply(avar)
									&& !plainVO_withTransparentBGColor.apply(avar)) {
								tmpDI = avar.getDrawId();
							}
							if (tmpDI != null && tmpDI < di && (lastDI[0]==null || lastDI[0]<tmpDI) ) {
								lastDI[0] = tmpDI;
								lastqntBlock[0] = avar;
							}
							return EFilterResult.REJECT;
						}
					});
			TColor newBGColor = DEFAULT_BG_COLOR; 
			if (lastDI[0] != null) {
				newBGColor = lastqntBlock[0].as(IPlainVisualObject.class).getBackgroundTColor();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+((IRdfResourceAdp)voWOBGColor).getRdfResource().getLocalName()
		+" has now default bg color ");
			}
			
			if (voWOBGColor instanceof IRdfResourceAdp) {
				plainVisualObjectImpl.addBackgroundTColor(((IRdfResourceAdp)voWOBGColor).getRdfResource()
						, newBGColor);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+((IRdfResourceAdp)voWOBGColor).getRdfResource().getLocalName()
		+" has now bg color "+newBGColor);
			}
			
		}
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. For every obj without BGColor");
	}

}
