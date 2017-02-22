package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.adp.instadp.rdfimpllib;
/**
 * File name: QltBlockLib.java
 * @created: Mar 31, 2011 5:11:30 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */


import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.Point;
import tuwien.dbai.wpps.common.exceptions.UnappropriateValueOfArgument;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.bgm.EBlockQltRelationStatic;
import tuwien.dbai.wpps.core.bgm.instadps.impl.RdfQltBlock;
import tuwien.dbai.wpps.core.bgm.instadps.interfaces.IAbstractBlock;
import tuwien.dbai.wpps.core.bgm.instadps.interfaces.IQltBlock;
import tuwien.dbai.wpps.core.bgm.instadps.lib.additFunctional.QltBlockAF;
import tuwien.dbai.wpps.core.models.IQltRelation;
import tuwien.dbai.wpps.core.physicmodel.fuzzy.Mu;
import tuwien.dbai.wpps.core.physicmodel.fuzzy.Nu;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
// TODO: implement ADR-relations independent from IR.
/**
 * <p>Class which provide necessary functions for qualitative block-based geometric model.</p>
 * 
 * <p>Functions to check an existence of interval relations between two intervals:
 *  {@linkplain #hasIRBeforeComp(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationTouchesCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationOverlapsCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationStartsCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationInsideCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationFinishesCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationEqualsCalc(double, double, Mu, Mu, Nu)},
 *  and their symmetric relations, such as
 *  {@linkplain #hasIRAfterComp(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationTouchedByCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationOverlappedByCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationStartedByCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationContainsCalc(double, double, Mu, Mu, Nu)},
 *  {@linkplain #hasIntervalRelationFinishedByCalc(double, double, Mu, Mu, Nu)}.
 *  These relations cover all possible relative positions between interval represented qualitatively. 
 * </p>
 * 
 * @created: Mar 31, 2011 5:11:30 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see IQltBlock
 * @see RdfQltBlock
 * @see QltBlockImpl
 * @see QltBlockImpl
 */
public final class QltBlockLibOld {
	final static private Logger log = Logger.getLogger(QltBlockLibOld.class);
	
	/**
	 * Function is used to get a relation between instances where relation is a direct sub-property of the property <code>superProp</code>.
	 * If a reasoner for computing transitive closures for the inheritance relations is applied, this function also works for indirect super properties. 
	 * @param primInst primary instance.
	 * @param refInst reference instance.
	 * @param model model, where relations are defined.
	 * @param superProp super property.
	 * @return predicate defined between instances of the type <code>superProp</code> which is a direct super property, <code>null</code> otherwise.
	 */
	public static final ObjectProperty getRelationAsObjectPropOfDirectType(final Resource primInst, final Resource refInst
			, final Model model, final ObjectProperty superProp) {
		StmtIterator iter = model.listStatements(
				new SimpleSelector(primInst, null, refInst) {
					boolean test = true;
					public boolean test(Statement s) {
						if (test)
							return super.test(s);
						else
							return false;
					}
					public boolean selects(Statement s)
		            {
						if (test && model.contains(s.getPredicate(), RDFS.subPropertyOf, superProp)) {
							test = false;
							return true;
						}
						return false;
		            }
				});
		
		ObjectProperty res = null;
		
		if (iter.hasNext())
			res = iter.next().getPredicate().as(ObjectProperty.class);
		
		return res;
	}
	
	/**
	 * @param inst1
	 * @param inst2
	 * @param model
	 * @param superProp
	 * @return collection of predicates defined between instances of the type <code>superProp</code> which is a direct super property, <code>null</code> otherwise.
	 * @see #getRelationAsObjectPropOfDirectType(Resource, Resource, Model, ObjectProperty)
	 */
	public static final Collection<ObjectProperty> listRelationAsObjectPropOfDirectType(final Resource inst1, final Resource inst2, final Model model
			, final ObjectProperty superProp) {
		StmtIterator iter = model.listStatements(
				new SimpleSelector(inst1, null, inst2) {
					public boolean selects(Statement s)
		            {
						return model.contains(s.getPredicate(), RDFS.subPropertyOf, superProp);
		            }
				});
		Set<ObjectProperty> resSet = new HashSet<ObjectProperty>();
		while (iter.hasNext()) {
			resSet.add(iter.next().getPredicate().as(ObjectProperty.class));
		}
		return resSet;
	}
	
//	public static final void rmRelationOfType(final Resource inst1, final Resource inst2, final Model model
//			, final ObjectProperty superProp) {
//		ObjectProperty[] propArr = listRelationAsObjectPropOfDirectType(inst1, inst2, model, superProp);
//		for (int i = 0; i < propArr.length; i++) {
//			model.removeAll(inst1, propArr[i], inst2);
//		}
//		model.removeAll(inst1, superProp, inst2);
//	}
	
	/**
	 * Add relation between instances.
	 * @param primInst
	 * @param refInst
	 * @param model
	 * @param prop
	 */
	public static final void addRelation(Resource primInst, Resource refInst, Model model, ObjectProperty prop) {
		model.add(primInst, prop, refInst);
	}
	
	/**
	 * Check the ontological model <code>model</code> for the existence of the triple <code>primInst-prop-refInst</code>.
	 * @param primInst
	 * @param refInst
	 * @param model
	 * @param prop
	 * @return
	 */
	public static final boolean hasRelationInOntology(Resource primInst, Resource refInst, Model model, ObjectProperty prop) {
		return model.contains(primInst, prop, refInst);
	}
	
//	public static final void setRelation(Resource inst1, Resource inst2, Model model
//			, ObjectProperty prop, ObjectProperty superProp) {
//		ObjectProperty[] propArr = listRelationAsObjectPropOfDirectType(inst1, inst2, model, superProp);
//		for (int i = 0; i < propArr.length; i++) {
//			model.removeAll(inst1, propArr[i], inst2);
//		}
//		addRelation(inst1, inst2, model, prop);
//	}
	
	// --------  --------
	
	// ========  ========
	
	
	
	
	// ======== ADDITIONAL CLACULATIONS. STATIC ========
	
	// -------------------------------------
	// --------- Interval Relations --------
	// -------------------------------------
	
	// parameters to compute interval relations
	private static final BitSet irBefore = new BitSet(12);
	private static final BitSet irAfter = new BitSet(12);
	private static final BitSet irTouch = new BitSet(12);
	private static final BitSet irTouchi = new BitSet(12);
	private static final BitSet irOverlaps = new BitSet(12);
	private static final BitSet irOverlapsi = new BitSet(12);
	private static final BitSet irStarts = new BitSet(12);
	private static final BitSet irStartsi = new BitSet(12);
	private static final BitSet irIn = new BitSet(12);
	private static final BitSet irContains = new BitSet(12);
	private static final BitSet irFinishes = new BitSet(12);
	private static final BitSet irFinishesi = new BitSet(12);
	private static final BitSet irEquals = new BitSet(12);
	
	// initialization of the parameters to compute interval relations
	static {
		irBefore.set(6);
		irAfter.set(5);
		irTouch.set(0); irTouch.set(7);
		irTouchi.set(4); irTouchi.set(11);
		irOverlaps.set(0); irOverlaps.set(8); irOverlaps.set(9);
		irOverlapsi.set(2); irOverlapsi.set(3); irOverlapsi.set(11);
		irStarts.set(1); irStarts.set(6);
		irStartsi.set(1); irStartsi.set(11);
		irIn.set(2); irIn.set(6);
		irContains.set(0); irContains.set(11);
		irFinishes.set(2); irFinishes.set(7);
		irFinishesi.set(0); irFinishesi.set(10);
		irEquals.set(1); irEquals.set(7);
	}
	
	
	/**
	 * @param a representative number for the fuzzy number A.
	 * @param muA membership function for the fuzzy number A.
	 * @param b representative number for the fuzzy number B.
	 * @param muB membership function for the fuzzy number B.
	 * @param nu true-function.
	 * @return <code>true</code>, if fuzzy number A less than B.
	 */
	public final static boolean less(double a, final Mu muA, double b, final Mu muB, final Nu nu) {
		return ( a<b && !equal(a, muA, b, muB, nu))?true:false;
//		return (a<b && nu.exe(muA.exe(b-a))==0 && nu.exe(muB.exe(a-b))==0)?true:false;
	}

	private final static boolean lessOrEq(double a, final Mu muA, double b, final Mu muB, final Nu nu) {
		return !more(a, muA, b, muB, nu);
	}

	/**
	 * @param a representative number for the fuzzy number A.
	 * @param muA membership function for the fuzzy number A.
	 * @param b representative number for the fuzzy number B.
	 * @param muB membership function for the fuzzy number B.
	 * @param nu true-function.
	 * @return <code>true</code>, if fuzzy number A equals B.
	 */
	public final static boolean equal(double a, final Mu muA, double b, final Mu muB, final Nu nu) {
		return (nu.exe(muA.exe(b-a))>0 || nu.exe(muB.exe(a-b))>0)?true:false;
	}

	/**
	 * @param a representative number for the fuzzy number A.
	 * @param muA membership function for the fuzzy number A.
	 * @param b representative number for the fuzzy number B.
	 * @param muB membership function for the fuzzy number B.
	 * @param nu true-function.
	 * @return <code>true</code>, if fuzzy number A more than B.
	 */
	public final static boolean more(double a, final Mu muA, double b, final Mu muB, final Nu nu) {
		return (a>b && !equal(a, muA, b, muB, nu))?true:false;
//		return (a>b && nu.exe(muA.exe(b-a))==0 && nu.exe(muB.exe(a-b))==0)?true:false;
	}

	public final static boolean moreOrEq(double a, final Mu muA, double b, final Mu muB, final Nu nu) {
		return !less(a, muA, b, muB, nu);
	}
	
	/**
	 * Class for the function {@link QltBlockLibOld#calcIntervalRelations(double, double, double, double, Mu, Mu, Nu, int)}.
	 * To make code more readable.
	 * @created: Jul 26, 2011 2:32:27 AM
	 * @author Ruslan (ruslanrf@gmail.com)
	 */
	private static final class _Comparator {
		final double a; final Mu muA; double b; final Mu muB; final Nu nu;
		public _Comparator(double a, final Mu muA, double b, final Mu muB, final Nu nu) {
			this.a = a;	this.muA = muA;	this.b = b;	this.muB = muB;	this.nu = nu;
		}
		public boolean less() {
			return QltBlockLibOld.less(a, muA, b, muB, nu);
		}
		public boolean lessOrEq() {
			return QltBlockLibOld.lessOrEq(a, muA, b, muB, nu);
		}
		public boolean equal() {
			return QltBlockLibOld.equal(a, muA, b, muB, nu);
		}
		public boolean more() {
			return QltBlockLibOld.more(a, muA, b, muB, nu);
		}
		public boolean moreOrEq() {
			return QltBlockLibOld.moreOrEq(a, muA, b, muB, nu);
		}
	}

	/**
	 * Matrix which is used to calculate interval relations.
	 *<pre>
	 * a1    a2     b1    b2
	 * |-----|      |-----|
	 * 
	 * --------------------------
	 * a1?b1 a1?b2 a2?b1 a2?b2
	 * < = > < = > < = > < = >
	 * --------------------------
	 * 0 1 2 3 4 5 6 7 8 9 10 11
	 * --------------------------
	 *             1                before
	 * 1             1              touches
	 * 1               1 1          overlaps
	 *   1         1                starts
	 *     1       1                inside
	 *     1         1              finishes
	 *   1           1              equals
	 *           1                  after
	 *         1               1    touched-by
	 *     1 1                 1    overlapped-by
	 *   1                     1    started-by
	 * 1                       1    contains
	 * 1                     1      finished-by
	 * --------------------------
	 *</pre>
	 * 
	 * @param primPtMin left point of the primary interval.
	 * @param primPtMax right point of the primary interval.
	 * @param refPtMin left point of the reference interval.
	 * @param refPtMax right point of the reference interval.
	 * @param muPtMin membership function for the left point of an interval.
	 * @param muPtMax membership function for the right point of an interval.
	 * @param nu true function for membership functions. 
	 * @param mode 0 --- interval relations for axis <i>x</i>, 1 --- interval relations for axis <i>y</i>.
	 * @return array of detected interval relations.
	 */
	public static final Collection<EBlockQltRelationType> _calcIntervalRelations(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu, int mode) {
		if (mode != 0 && mode != 1)
			throw new UnappropriateValueOfArgument(log, Integer.toString(mode));
		
		final _Comparator ca1b1 = new _Comparator(primPtMin, muPtMin, refPtMin, muPtMin, nu);
		final _Comparator ca1b2 = new _Comparator(primPtMin, muPtMin, refPtMax, muPtMax, nu);
		final _Comparator ca2b1 = new _Comparator(primPtMax, muPtMax, refPtMin, muPtMin, nu);
		final _Comparator ca2b2 = new _Comparator(primPtMax, muPtMax, refPtMax, muPtMax, nu);
		
		final List<EBlockQltRelationType> resArr = new ArrayList<EBlockQltRelationType>();
		final BitSet bs = new BitSet(12);
		
		// set bit map for the provided intervals
		if (ca1b1.less()) bs.set(0);
		if (ca1b1.equal()) bs.set(1);
		if (ca1b1.more()) bs.set(2);
		if (ca1b2.less()) bs.set(3);
		if (ca1b2.equal()) bs.set(4);
		if (ca1b2.more()) bs.set(5);
		if (ca2b1.less()) bs.set(6);
		if (ca2b1.equal()) bs.set(7);
		if (ca2b1.more()) bs.set(8);
		if (ca2b2.less()) bs.set(9);
		if (ca2b2.equal()) bs.set(10);
		if (ca2b2.more()) bs.set(11);
		
		// calculate interval relations
		if(bs.intersects(irBefore))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.BEFORE_X); break; case 1: resArr.add(EBlockQltRelationType.BEFORE_Y);}
		if(bs.intersects(irAfter))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.AFTER_X); break; case 1: resArr.add(EBlockQltRelationType.AFTER_Y);}
		if(bs.intersects(irTouch))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.TOUCHES_X); break; case 1: resArr.add(EBlockQltRelationType.TOUCHES_Y);}
		if(bs.intersects(irTouchi))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.TOUCHED_BY_X); break; case 1: resArr.add(EBlockQltRelationType.TOUCHED_BY_Y);}
		if(bs.intersects(irOverlaps))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.OVERLAPS_X); break; case 1: resArr.add(EBlockQltRelationType.OVERLAPS_Y);}
		if(bs.intersects(irOverlapsi))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.OVERLAPED_BY_X); break; case 1: resArr.add(EBlockQltRelationType.OVERLAPED_BY_Y);}
		if(bs.intersects(irStarts))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.STARTS_X); break; case 1: resArr.add(EBlockQltRelationType.STARTS_Y);}
		if(bs.intersects(irStartsi))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.STARTED_BY_X); break; case 1: resArr.add(EBlockQltRelationType.STARTED_BY_Y);}
		if(bs.intersects(irIn))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.INSIDE_X); break; case 1: resArr.add(EBlockQltRelationType.INSIDE_Y);}
		if(bs.intersects(irContains))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.CONTAINS_X); break; case 1: resArr.add(EBlockQltRelationType.CONTAINS_Y);}
		if(bs.intersects(irFinishes))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.FINISHES_X); break; case 1: resArr.add(EBlockQltRelationType.FINISHES_Y);}
		if(bs.intersects(irFinishesi))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.FINISHED_BY_X); break; case 1: resArr.add(EBlockQltRelationType.FINISHED_BY_Y);}
		if(bs.intersects(irEquals))
			switch (mode) { case 0: resArr.add(EBlockQltRelationType.EQUALS_X); break; case 1: resArr.add(EBlockQltRelationType.EQUALS_Y);}
		
		return resArr;
	}
	
	/**
	 * Check existence of the interval relation "before" between two intervals.<br/>
	 * <code>primPtMax &lt; refPtMin.</code>
	 * @param primPtMax right limit point of the primary interval.
	 * @param refPtMin left limit point of the reference interval.
	 * @param muPtMin membership function for the left point.
	 * @param muPtMax membership function for the right point.
	 * @param nu true-function.
	 * @return <code>true</code>, if the specified relation exists, <code>false</code> otherwise.
	 */
	public static final boolean hasIntervalRelationBeforeCalc(double primPtMax
			, double refPtMin, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return less(primPtMax, muPtMax, refPtMin, muPtMin, nu);
	}
	
	/**
	 * Check existence of the interval relation "after" between two intervals.
	 * It is a symmetric relation for the "before"
	 * @param primPtMin left limit point of the primary interval.
	 * @param refPtMax right limit point of the reference interval.
	 * @param muPtMin membership function for the left point.
	 * @param muPtMax membership function for the right point.
	 * @param nu true-function.
	 * @return <code>true</code>, if the specified relation exists, <code>false</code> otherwise.
	 */
	public static final boolean hasIntervalRelationAfterCalc(double primPtMin
			, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return hasIRBeforeComp(refPtMax, primPtMin, muPtMin, muPtMax, nu);
	}
	
	/**
	 * Check existence of the interval relation "touches" between two intervals.<br/>
	 * <code>primPtMin &lt; refPtMin & primPtMax = refPtMin & primPtMax &lt; refPtMax.</code>
	 * 
	 * @param primPtMin left limit point of the primary interval.
	 * @param primPtMax right limit point of the primary interval.
	 * @param refPtMin left limit point of the reference interval.
	 * @param refPtMax right limit point of the reference interval.
	 * @param muPtMin membership function for the left point.
	 * @param muPtMax membership function for the right point.
	 * @param nu true-function.
	 * @return <code>true</code>, if the specified relation exists, <code>false</code> otherwise.
	 */
	public static final boolean hasIntervalRelationTouchesCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return less(primPtMin, muPtMin, refPtMin, muPtMin, nu)
				&& equal(primPtMax, muPtMax, refPtMin, muPtMin, nu)
				&& less(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationTouchedByCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return hasIRTouchesComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationOverlapsCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return less(primPtMin, muPtMin, refPtMin, muPtMin, nu)
				&& less(refPtMin, muPtMin, primPtMax, muPtMax, nu)
				&& less(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationOverlappedByCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return hasIROverlapsComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationStartsCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return equal(primPtMin, muPtMin, refPtMin, muPtMin, nu)
				&& less(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationStartedByCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return hasIRStartsComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationInsideCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return less(refPtMin, muPtMin, primPtMin, muPtMin, nu)
				&& less(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationContainsCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return hasIRInsideComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationFinishesCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return less(refPtMin, muPtMin, primPtMin, muPtMin, nu)
				&& equal(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationFinishedByCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return hasIRFinishesComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIntervalRelationEqualsCalc(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu) {
		return equal(primPtMin, muPtMin, refPtMin, muPtMin, nu)
				&& equal(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	// -------------------------------------
	// ---------                    --------
	// -------------------------------------
	

	
	// -------------------------------------
	// --------------- RCC8 ----------------
	// -------------------------------------
	
	/**
	 * Additional class for the functions {@linkplain QltBlockLibOld#hasJEPDRCC8Relation(Resource, IAbstractBlock, Model, EBlockQltRelationType, QltBlockImpl)},
	 * {@linkplain QltBlockLibOld#hasODirecitionRelation(Resource, IAbstractBlock, Model, EBlockQltRelationType, QltBlockImpl)},
	 * and {@linkplain QltBlockLibOld#hasPDirecitionRelation(Resource, IAbstractBlock, Model, EBlockQltRelationType, QltBlockImpl)}.
	 */
	private static final class _RelationsTester {
		final private Resource primInst; final private Resource refInst;
		final private Model model; final private QltBlockImpl qltAF;
		public _RelationsTester(final Resource primInst, final Resource refInst
				, final Model model, final QltBlockImpl qltAF) {
			this.primInst = primInst; this.refInst = refInst;
			this.model=model; this.qltAF = qltAF;
		}
		public final boolean ir(EBlockQltRelationType rel) {
			return qltAF.hasIntervalRelation2D(primInst, refInst, model, rel);
		}
		public final boolean basicRel(EBlockQltRelationType rel) {
			return qltAF.hasBasicADRRelation(primInst, refInst, model, rel);
		}
		
	}
	
	/**
	 * Check an existence of the specified relation based on the interval relations.
	 * @param primInst primary instance.
	 * @param refInst reference instance.
	 * @param model model where relations are defined.
	 * @param rel specified RCC8 relation.
	 * @param qltAF object which has function {@link QltBlockImpl#hasIntervalRelation2D(Resource, IAbstractBlock, Model, IQltRelation)},
	 * and which is used to check an existence of particular interval relations.
	 * @return
	 */
	public static final boolean hasRCC8RelationIR(final Resource primInst, final Resource refInst
			, final Model model, final EBlockQltRelationType rel, final QltBlockImpl qltAF) {
		final _RelationsTester c = new _RelationsTester(primInst, refInst, model, qltAF);

		// --- JEPD RCC8 ---
		if (rel.equals(EBlockQltRelationType.DC))
			return c.ir(EBlockQltRelationType.BEFORE_X) || c.ir(EBlockQltRelationType.AFTER_X)
					|| c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.AFTER_Y);
		if (rel.equals(EBlockQltRelationType.EC))
			return ( c.ir(EBlockQltRelationType.TOUCHES_X) || c.ir(EBlockQltRelationType.TOUCHED_BY_X) )
					&& !c.ir(EBlockQltRelationType.BEFORE_Y) && !c.ir(EBlockQltRelationType.AFTER_Y)
					||
					( c.ir(EBlockQltRelationType.TOUCHES_Y) || c.ir(EBlockQltRelationType.TOUCHED_BY_Y) )
					&& !c.ir(EBlockQltRelationType.BEFORE_X) && !c.ir(EBlockQltRelationType.AFTER_X);
		if (rel.equals(EBlockQltRelationType.PO))
			return ( c.ir(EBlockQltRelationType.OVERLAPS_X) || c.ir(EBlockQltRelationType.OVERLAPED_BY_X) )
					&& !c.ir(EBlockQltRelationType.BEFORE_Y) && !c.ir(EBlockQltRelationType.TOUCHES_Y)
					&& !c.ir(EBlockQltRelationType.TOUCHED_BY_Y) && !c.ir(EBlockQltRelationType.AFTER_Y)
					||
					( c.ir(EBlockQltRelationType.OVERLAPS_Y) || c.ir(EBlockQltRelationType.OVERLAPED_BY_Y) )
					&& !c.ir(EBlockQltRelationType.BEFORE_X) && !c.ir(EBlockQltRelationType.TOUCHES_X)
					&& !c.ir(EBlockQltRelationType.TOUCHED_BY_X) && !c.ir(EBlockQltRelationType.AFTER_X);
		if (rel.equals(EBlockQltRelationType.TPP))
			return ( c.ir(EBlockQltRelationType.STARTS_X) || c.ir(EBlockQltRelationType.FINISHES_X)
							|| c.ir(EBlockQltRelationType.EQUALS_X) )
						&& (c.ir(EBlockQltRelationType.STARTS_Y) || c.ir(EBlockQltRelationType.INSIDE_Y)
									|| c.ir(EBlockQltRelationType.FINISHES_Y))
					|| c.ir(EBlockQltRelationType.EQUALS_Y)
							&& (c.ir(EBlockQltRelationType.STARTS_X) || c.ir(EBlockQltRelationType.INSIDE_X)
										|| c.ir(EBlockQltRelationType.FINISHES_X));
		if (rel.equals(EBlockQltRelationType.NTPP))
			return c.ir(EBlockQltRelationType.INSIDE_X) && c.ir(EBlockQltRelationType.INSIDE_Y);
		if (rel.equals(EBlockQltRelationType.EQUAL))
			return c.ir(EBlockQltRelationType.EQUALS_X) && c.ir(EBlockQltRelationType.EQUALS_Y);
		if (rel.equals(EBlockQltRelationType.NTPPi))
			return c.ir(EBlockQltRelationType.CONTAINS_X) && c.ir(EBlockQltRelationType.CONTAINS_Y);
		if (rel.equals(EBlockQltRelationType.TPPi))
			return ( c.ir(EBlockQltRelationType.STARTED_BY_X) || c.ir(EBlockQltRelationType.FINISHED_BY_X)
							|| c.ir(EBlockQltRelationType.EQUALS_X))
						&& (c.ir(EBlockQltRelationType.STARTED_BY_Y) || c.ir(EBlockQltRelationType.CONTAINS_Y)
								|| c.ir(EBlockQltRelationType.FINISHED_BY_Y))
					||
					( c.ir(EBlockQltRelationType.STARTED_BY_Y) || c.ir(EBlockQltRelationType.FINISHED_BY_Y)
							|| c.ir(EBlockQltRelationType.EQUALS_Y))
						&& (c.ir(EBlockQltRelationType.STARTED_BY_X) || c.ir(EBlockQltRelationType.CONTAINS_X)
								|| c.ir(EBlockQltRelationType.FINISHED_BY_X));
		// ---  ---
		// --- additionl relations ---
		
		if (rel.equals(EBlockQltRelationType.PP))
			return ( c.ir(EBlockQltRelationType.STARTS_X) || c.ir(EBlockQltRelationType.INSIDE_X)
					|| c.ir(EBlockQltRelationType.FINISHES_X) || c.ir(EBlockQltRelationType.EQUALS_X) )
				&& (c.ir(EBlockQltRelationType.STARTS_Y) || c.ir(EBlockQltRelationType.INSIDE_Y)
							|| c.ir(EBlockQltRelationType.FINISHES_Y))
			|| c.ir(EBlockQltRelationType.EQUALS_Y)
					&& (c.ir(EBlockQltRelationType.STARTS_X) || c.ir(EBlockQltRelationType.INSIDE_X)
								|| c.ir(EBlockQltRelationType.FINISHES_X));
		if (rel.equals(EBlockQltRelationType.PPi))
			return ( c.ir(EBlockQltRelationType.STARTED_BY_X) || c.ir(EBlockQltRelationType.CONTAINS_X)
					|| c.ir(EBlockQltRelationType.FINISHED_BY_X) || c.ir(EBlockQltRelationType.EQUALS_X) )
				&& (c.ir(EBlockQltRelationType.STARTED_BY_Y) || c.ir(EBlockQltRelationType.CONTAINS_Y)
							|| c.ir(EBlockQltRelationType.FINISHED_BY_Y))
			|| c.ir(EBlockQltRelationType.EQUALS_Y)
					&& (c.ir(EBlockQltRelationType.STARTED_BY_X) || c.ir(EBlockQltRelationType.CONTAINS_X)
								|| c.ir(EBlockQltRelationType.FINISHED_BY_X));
		if (rel.equals(EBlockQltRelationType.P))
			return ( c.ir(EBlockQltRelationType.STARTS_X) || c.ir(EBlockQltRelationType.INSIDE_X)
					|| c.ir(EBlockQltRelationType.FINISHES_X) || c.ir(EBlockQltRelationType.EQUALS_X) )
				&& ( c.ir(EBlockQltRelationType.STARTS_Y) || c.ir(EBlockQltRelationType.INSIDE_Y)
						|| c.ir(EBlockQltRelationType.FINISHES_Y) || c.ir(EBlockQltRelationType.EQUALS_Y) );
		if (rel.equals(EBlockQltRelationType.Pi))
			return ( c.ir(EBlockQltRelationType.STARTED_BY_X) || c.ir(EBlockQltRelationType.CONTAINS_X)
					|| c.ir(EBlockQltRelationType.FINISHED_BY_X) || c.ir(EBlockQltRelationType.EQUALS_X) )
				&& ( c.ir(EBlockQltRelationType.STARTED_BY_Y) || c.ir(EBlockQltRelationType.CONTAINS_Y)
						|| c.ir(EBlockQltRelationType.FINISHED_BY_Y) || c.ir(EBlockQltRelationType.EQUALS_Y) );
		if (rel.equals(EBlockQltRelationType.O))
			return !c.ir(EBlockQltRelationType.BEFORE_X) && !c.ir(EBlockQltRelationType.TOUCHES_X) && !c.ir(EBlockQltRelationType.AFTER_X)
			 && !c.ir(EBlockQltRelationType.TOUCHED_BY_X)
			 && !c.ir(EBlockQltRelationType.BEFORE_Y) && !c.ir(EBlockQltRelationType.TOUCHES_Y) && !c.ir(EBlockQltRelationType.AFTER_Y)
			 && !c.ir(EBlockQltRelationType.TOUCHED_BY_Y);
		if (rel.equals(EBlockQltRelationType.C))
			return !c.ir(EBlockQltRelationType.BEFORE_X) && !c.ir(EBlockQltRelationType.AFTER_X)
					&& !c.ir(EBlockQltRelationType.BEFORE_Y) && !c.ir(EBlockQltRelationType.AFTER_Y);
		if (rel.equals(EBlockQltRelationType.DR))
			return c.ir(EBlockQltRelationType.BEFORE_X) || c.ir(EBlockQltRelationType.TOUCHES_X) || c.ir(EBlockQltRelationType.AFTER_X)
					|| c.ir(EBlockQltRelationType.TOUCHED_BY_X)
			 || c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y) || c.ir(EBlockQltRelationType.AFTER_Y)
					 || c.ir(EBlockQltRelationType.TOUCHED_BY_Y);
			// --- ---
		
		throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	
	/**
	 * Check an existence of the specified composite RCC8 relation based on the JEPD RCC8 relations (Basic relations).
	 */
	public static final boolean hasCompositeRCC8RelationCalcBasedOnJEPD(final Resource primInst, final Resource refInst
			, final Model model, final EBlockQltRelationType rel, final QltBlockImpl qltAF) {
		final _RelationsTester r = new _RelationsTester(primInst, refInst, model, qltAF);
		if (rel.equals(EBlockQltRelationType.PP))
			return r.basicRel(EBlockQltRelationType.TPP) || r.basicRel(EBlockQltRelationType.NTPP);
		if (rel.equals(EBlockQltRelationType.PPi))
			return r.basicRel(EBlockQltRelationType.TPPi) || r.basicRel(EBlockQltRelationType.NTPPi);
		if (rel.equals(EBlockQltRelationType.P))
			return r.basicRel(EBlockQltRelationType.TPP) || r.basicRel(EBlockQltRelationType.NTPP)
					|| r.basicRel(EBlockQltRelationType.EQUAL);
		if (rel.equals(EBlockQltRelationType.Pi))
			return r.basicRel(EBlockQltRelationType.TPPi) || r.basicRel(EBlockQltRelationType.NTPPi)
					|| r.basicRel(EBlockQltRelationType.EQUAL);
		if (rel.equals(EBlockQltRelationType.O))
			return r.basicRel(EBlockQltRelationType.TPP) || r.basicRel(EBlockQltRelationType.NTPP)
					|| r.basicRel(EBlockQltRelationType.EQUAL)
					|| r.basicRel(EBlockQltRelationType.TPPi) || r.basicRel(EBlockQltRelationType.NTPPi)
					|| r.basicRel(EBlockQltRelationType.PO);
		if (rel.equals(EBlockQltRelationType.C))
			return r.basicRel(EBlockQltRelationType.TPP) || r.basicRel(EBlockQltRelationType.NTPP)
					|| r.basicRel(EBlockQltRelationType.EQUAL)
					|| r.basicRel(EBlockQltRelationType.TPPi) || r.basicRel(EBlockQltRelationType.NTPPi)
					|| r.basicRel(EBlockQltRelationType.PO) || r.basicRel(EBlockQltRelationType.EC);
		if (rel.equals(EBlockQltRelationType.DR))
			return r.basicRel(EBlockQltRelationType.EC) || r.basicRel(EBlockQltRelationType.DC);
		
		throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	
	// -------------------------------------
	// ---------------     -----------------
	// -------------------------------------
	
	
	
	// ------------------------------------------------------
	// --------------- O-Direcition Relation ----------------
	// ------------------------------------------------------
	
	/**
	 * Check an existence of o-direction relation based on the interval relations.
	 * @param primInst primary instance.
	 * @param refInst reference instance.
	 * @param model model where relations are defined.
	 * @param rel specified o-direction relation.
	 * @param qltAF object which has function {@link QltBlockImpl#hasIntervalRelation2D(Resource, IAbstractBlock, Model, IQltRelation)},
	 * and which is used to check an existence of particular interval relations.
	 * @return
	 */
	public static final boolean hasODirecitionRelationIR(final Resource primInst, final Resource refInst
			, final Model model, final EBlockQltRelationType rel, final QltBlockImpl qltAF) {
		final _RelationsTester c = new _RelationsTester(primInst, refInst, model, qltAF);
		
		if (rel.equals(EBlockQltRelationType.NORTH_OF_O))
			return (c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y))
				&& !c.ir(EBlockQltRelationType.BEFORE_X) && !c.ir(EBlockQltRelationType.TOUCHES_X)
				&& !c.ir(EBlockQltRelationType.TOUCHED_BY_X) && !c.ir(EBlockQltRelationType.AFTER_X);
		if (rel.equals(EBlockQltRelationType.NORTH_EAST_OF_O))
			return (c.ir(EBlockQltRelationType.TOUCHED_BY_X) || c.ir(EBlockQltRelationType.AFTER_X))
					&& (c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y)
							|| c.ir(EBlockQltRelationType.OVERLAPS_Y) || c.ir(EBlockQltRelationType.FINISHED_BY_Y)
							|| c.ir(EBlockQltRelationType.CONTAINS_Y))
					|| ( c.ir(EBlockQltRelationType.OVERLAPED_BY_X) || c.ir(EBlockQltRelationType.STARTED_BY_X)
							|| c.ir(EBlockQltRelationType.CONTAINS_X) )
							&& (c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y));
		if (rel.equals(EBlockQltRelationType.EAST_OF_O))
			return (c.ir(EBlockQltRelationType.AFTER_X) || c.ir(EBlockQltRelationType.TOUCHED_BY_X))
					&& !c.ir(EBlockQltRelationType.BEFORE_Y) && !c.ir(EBlockQltRelationType.TOUCHES_Y)
					&& !c.ir(EBlockQltRelationType.TOUCHED_BY_Y) && !c.ir(EBlockQltRelationType.AFTER_Y);
		if (rel.equals(EBlockQltRelationType.SOUTH_EAST_OF_O))
			return (c.ir(EBlockQltRelationType.TOUCHED_BY_X) || c.ir(EBlockQltRelationType.AFTER_X))
					&& (c.ir(EBlockQltRelationType.AFTER_Y) || c.ir(EBlockQltRelationType.TOUCHED_BY_Y)
							|| c.ir(EBlockQltRelationType.OVERLAPED_BY_Y) || c.ir(EBlockQltRelationType.STARTED_BY_Y)
							|| c.ir(EBlockQltRelationType.CONTAINS_Y))
					|| ( c.ir(EBlockQltRelationType.OVERLAPED_BY_X) || c.ir(EBlockQltRelationType.STARTED_BY_X)
							|| c.ir(EBlockQltRelationType.CONTAINS_X) )
							&& (c.ir(EBlockQltRelationType.AFTER_Y) || c.ir(EBlockQltRelationType.TOUCHED_BY_Y));
		if (rel.equals(EBlockQltRelationType.SOUTH_OF_O))
			return (c.ir(EBlockQltRelationType.TOUCHED_BY_Y) || c.ir(EBlockQltRelationType.AFTER_Y))
					&& !c.ir(EBlockQltRelationType.BEFORE_X) && !c.ir(EBlockQltRelationType.TOUCHES_X)
					&& !c.ir(EBlockQltRelationType.TOUCHED_BY_X) && !c.ir(EBlockQltRelationType.AFTER_X);
		if (rel.equals(EBlockQltRelationType.SOUTH_WEST_OF_O))
			return (c.ir(EBlockQltRelationType.BEFORE_X) || c.ir(EBlockQltRelationType.TOUCHES_X))
					&& (c.ir(EBlockQltRelationType.AFTER_Y) || c.ir(EBlockQltRelationType.TOUCHED_BY_Y)
							|| c.ir(EBlockQltRelationType.OVERLAPED_BY_Y) || c.ir(EBlockQltRelationType.STARTED_BY_Y)
							|| c.ir(EBlockQltRelationType.CONTAINS_Y))
					|| ( c.ir(EBlockQltRelationType.OVERLAPS_X) || c.ir(EBlockQltRelationType.FINISHED_BY_X)
							|| c.ir(EBlockQltRelationType.CONTAINS_X) )
							&& (c.ir(EBlockQltRelationType.AFTER_Y) || c.ir(EBlockQltRelationType.TOUCHED_BY_Y));
		if (rel.equals(EBlockQltRelationType.WEST_OF_O))
			return (c.ir(EBlockQltRelationType.BEFORE_X) || c.ir(EBlockQltRelationType.TOUCHES_X))
					&& !c.ir(EBlockQltRelationType.BEFORE_Y) && !c.ir(EBlockQltRelationType.TOUCHES_Y)
					&& !c.ir(EBlockQltRelationType.TOUCHED_BY_Y) && !c.ir(EBlockQltRelationType.AFTER_Y);
		if (rel.equals(EBlockQltRelationType.NORTH_WEST_OF_O))
			return (c.ir(EBlockQltRelationType.BEFORE_X) || c.ir(EBlockQltRelationType.TOUCHES_X))
					&& (c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y)
							|| c.ir(EBlockQltRelationType.OVERLAPS_Y) || c.ir(EBlockQltRelationType.FINISHED_BY_Y)
							|| c.ir(EBlockQltRelationType.CONTAINS_Y))
					|| ( c.ir(EBlockQltRelationType.OVERLAPS_X) || c.ir(EBlockQltRelationType.FINISHED_BY_X)
							|| c.ir(EBlockQltRelationType.CONTAINS_X) )
							&& (c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y));
		
		throw new UnknownValueFromPredefinedList(log, rel.getName());
		
	}
	
	// ------------------------------------------------------
	// ---------------                       ----------------
	// ------------------------------------------------------
	
	
	// ------------------------------------------------------
	// --------------- P-Direcition Relation ----------------
	// ------------------------------------------------------
	
	/**
	 * Check an existence of p-direction relation based on the interval relations.
	 * @param primInst primary instance.
	 * @param refInst reference instance.
	 * @param model model where relations are defined.
	 * @param rel specified p-direction relation.
	 * @param qltAF object which has function {@link QltBlockImpl#hasIntervalRelation2D(Resource, IAbstractBlock, Model, IQltRelation)},
	 * and which is used to check an existence of particular interval relations.
	 * @return
	 */
	public static final boolean hasPDirecitionRelationIR(final Resource primInst, final Resource refInst
			, final Model model, final EBlockQltRelationType rel, final QltBlockImpl qltAF) {
		final _RelationsTester c = new _RelationsTester(primInst, refInst, model, qltAF);
		
		if (rel.equals(EBlockQltRelationType.NORTH_OF_P))
			return ( c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y) )
					&& ( c.ir(EBlockQltRelationType.STARTS_X) || c.ir(EBlockQltRelationType.INSIDE_X)
							|| c.ir(EBlockQltRelationType.FINISHES_X) || c.ir(EBlockQltRelationType.EQUALS_X));
		if (rel.equals(EBlockQltRelationType.NORTH_EAST_OF_P))
			return (c.ir(EBlockQltRelationType.TOUCHED_BY_X) || c.ir(EBlockQltRelationType.AFTER_X))
					&& (c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y));
		if (rel.equals(EBlockQltRelationType.EAST_OF_P))
			return ( c.ir(EBlockQltRelationType.AFTER_X) || c.ir(EBlockQltRelationType.TOUCHED_BY_X) )
					&& ( c.ir(EBlockQltRelationType.STARTS_Y) || c.ir(EBlockQltRelationType.INSIDE_Y)
							|| c.ir(EBlockQltRelationType.FINISHES_Y) || c.ir(EBlockQltRelationType.EQUALS_Y));
		if (rel.equals(EBlockQltRelationType.SOUTH_EAST_OF_P))
			return (c.ir(EBlockQltRelationType.TOUCHED_BY_X) || c.ir(EBlockQltRelationType.AFTER_X))
					&& (c.ir(EBlockQltRelationType.AFTER_Y) || c.ir(EBlockQltRelationType.TOUCHED_BY_Y));
		if (rel.equals(EBlockQltRelationType.SOUTH_OF_P))
			return ( c.ir(EBlockQltRelationType.AFTER_Y) || c.ir(EBlockQltRelationType.TOUCHED_BY_Y) )
					&& ( c.ir(EBlockQltRelationType.STARTS_X) || c.ir(EBlockQltRelationType.INSIDE_X)
							|| c.ir(EBlockQltRelationType.FINISHES_X) || c.ir(EBlockQltRelationType.EQUALS_X));
		if (rel.equals(EBlockQltRelationType.SOUTH_WEST_OF_P))
			return (c.ir(EBlockQltRelationType.BEFORE_X) || c.ir(EBlockQltRelationType.TOUCHES_X))
					&& (c.ir(EBlockQltRelationType.AFTER_Y) || c.ir(EBlockQltRelationType.TOUCHED_BY_Y));
		if (rel.equals(EBlockQltRelationType.WEST_OF_P))
			return ( c.ir(EBlockQltRelationType.BEFORE_X) || c.ir(EBlockQltRelationType.TOUCHES_X) )
					&& ( c.ir(EBlockQltRelationType.STARTS_Y) || c.ir(EBlockQltRelationType.INSIDE_Y)
							|| c.ir(EBlockQltRelationType.FINISHES_Y) || c.ir(EBlockQltRelationType.EQUALS_Y));
		if (rel.equals(EBlockQltRelationType.NORTH_WEST_OF_P))
			return (c.ir(EBlockQltRelationType.BEFORE_X) || c.ir(EBlockQltRelationType.TOUCHES_X))
					&& (c.ir(EBlockQltRelationType.BEFORE_Y) || c.ir(EBlockQltRelationType.TOUCHES_Y));
		
		throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	
	// ------------------------------------------------------
	// ---------------                       ----------------
	// ------------------------------------------------------
	
	
	// ------------------------------------------------------
	// --------------- Alignment Relation ----------------
	// ------------------------------------------------------
	
	/**
	 * Check an existence of alignment relations based on the interval relations.
	 * Such alignment relations as {@linkplain EBlockQltRelationType#NO_HORIZONTALLY_ALIGNED_WITH},
	 * {@linkplain EBlockQltRelationType#NO_VERTICALLY_ALIGNED_WITH}, {@linkplain EBlockQltRelationType#HAS_NOT_ALIGNMENT_RELATION}
	 * are not computed by this function.
	 * 
	 * @param primInst
	 * @param refInst
	 * @param model
	 * @param rel
	 * @param qltAF
	 * @param muCenterPt membership function for the centering.
	 * @param nu true-function.
	 * @return
	 */
	public static final boolean hasAlignmentRelationIR(final Resource primInst, final Resource refInst
			, final Model model, final EBlockQltRelationType rel, final QltBlockImpl qltAF
			, final Mu muCenterPt, final Nu nu) {
		final _RelationsTester r = new _RelationsTester(primInst, refInst, model, qltAF);
		// --- main relations ---
		if (rel.equals(EBlockQltRelationType.LEFT_ALIGNED_WITH))
			return r.ir(EBlockQltRelationType.FINISHES_X) || r.ir(EBlockQltRelationType.FINISHED_BY_X) || r.ir(EBlockQltRelationType.EQUALS_X);
		if (rel.equals(EBlockQltRelationType.RIGHT_ALIGNED_WITH))
			return r.ir(EBlockQltRelationType.STARTS_X) || r.ir(EBlockQltRelationType.STARTED_BY_X) || r.ir(EBlockQltRelationType.EQUALS_X);
		if (rel.equals(EBlockQltRelationType.CENTERED_VERTICALLY_WITH))
			return hasHorCenteringRelationCalc(primInst, refInst, model, muCenterPt, nu);
		if (rel.equals(EBlockQltRelationType.TOP_ALIGNED_WITH))
			return r.ir(EBlockQltRelationType.STARTS_Y) || r.ir(EBlockQltRelationType.STARTED_BY_Y) || r.ir(EBlockQltRelationType.EQUALS_Y);
		if (rel.equals(EBlockQltRelationType.BOTTOM_ALIGNED_WITH))
			return r.ir(EBlockQltRelationType.FINISHES_Y) || r.ir(EBlockQltRelationType.FINISHED_BY_Y) || r.ir(EBlockQltRelationType.EQUALS_Y);
		if (rel.equals(EBlockQltRelationType.CENTERED_HORIZONTALLY_WITH))
			return hasVertCenteringRelationCalc(primInst, refInst, model, muCenterPt, nu);
		// ---  ---
		// --- additional relations ---
		
		if (rel.equals(EBlockQltRelationType.HORIZONTALLY_ALIGNED_WITH))
			return r.ir(EBlockQltRelationType.STARTS_X) || r.ir(EBlockQltRelationType.STARTED_BY_X) || r.ir(EBlockQltRelationType.EQUALS_X)
				|| rel.equals(EBlockQltRelationType.FINISHES_X) || r.ir(EBlockQltRelationType.FINISHED_BY_X)
				|| hasHorCenteringRelationCalc(primInst, refInst, model, muCenterPt, nu);
		if (rel.equals(EBlockQltRelationType.VERTICALLY_ALIGNED_WITH))
			return r.ir(EBlockQltRelationType.STARTS_Y) || r.ir(EBlockQltRelationType.STARTED_BY_Y) || r.ir(EBlockQltRelationType.EQUALS_Y)
				|| rel.equals(EBlockQltRelationType.FINISHES_Y) || r.ir(EBlockQltRelationType.FINISHED_BY_Y)
				|| hasVertCenteringRelationCalc(primInst, refInst, model, muCenterPt, nu);
		if (rel.equals(EBlockQltRelationType.HAS_ALIGNMENT_RELATION))
			return r.ir(EBlockQltRelationType.STARTS_X) || r.ir(EBlockQltRelationType.STARTED_BY_X) || r.ir(EBlockQltRelationType.EQUALS_X)
				|| rel.equals(EBlockQltRelationType.FINISHES_X) || r.ir(EBlockQltRelationType.FINISHED_BY_X)
				|| hasHorCenteringRelationCalc(primInst, refInst, model, muCenterPt, nu)
				|| r.ir(EBlockQltRelationType.STARTS_Y) || r.ir(EBlockQltRelationType.STARTED_BY_Y) || r.ir(EBlockQltRelationType.EQUALS_Y)
				|| rel.equals(EBlockQltRelationType.FINISHES_Y) || r.ir(EBlockQltRelationType.FINISHED_BY_Y)
				|| hasVertCenteringRelationCalc(primInst, refInst, model, muCenterPt, nu);
		
		// ---  ---
		throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	
	
	/**
	 * Check an existence of the specified composite alignment relation based on the basic relations.
	 * Such alignment relations as {@linkplain EBlockQltRelationType#NO_HORIZONTALLY_ALIGNED_WITH},
	 * {@linkplain EBlockQltRelationType#NO_VERTICALLY_ALIGNED_WITH}, {@linkplain EBlockQltRelationType#HAS_NOT_ALIGNMENT_RELATION}
	 * are not computed by this function.
	 */
	public static final boolean hasCompositeAlignmentRelationCalcBasedOnBasic(final Resource primInst, final Resource refInst
			, final Model model, final EBlockQltRelationType rel, final QltBlockImpl qltAF) {
		final _RelationsTester r = new _RelationsTester(primInst, refInst, model, qltAF);
		if (rel.equals(EBlockQltRelationType.HORIZONTALLY_ALIGNED_WITH))
			return r.basicRel(EBlockQltRelationType.LEFT_ALIGNED_WITH) || r.basicRel(EBlockQltRelationType.RIGHT_ALIGNED_WITH)
					|| r.basicRel(EBlockQltRelationType.CENTERED_VERTICALLY_WITH);
		if (rel.equals(EBlockQltRelationType.VERTICALLY_ALIGNED_WITH))
			return r.basicRel(EBlockQltRelationType.TOP_ALIGNED_WITH) || r.basicRel(EBlockQltRelationType.BOTTOM_ALIGNED_WITH)
					|| r.basicRel(EBlockQltRelationType.CENTERED_HORIZONTALLY_WITH);
		if (rel.equals(EBlockQltRelationType.HAS_ALIGNMENT_RELATION))
			return r.basicRel(EBlockQltRelationType.LEFT_ALIGNED_WITH) || r.basicRel(EBlockQltRelationType.RIGHT_ALIGNED_WITH)
					|| r.basicRel(EBlockQltRelationType.CENTERED_VERTICALLY_WITH)
					|| r.basicRel(EBlockQltRelationType.TOP_ALIGNED_WITH) || r.basicRel(EBlockQltRelationType.BOTTOM_ALIGNED_WITH)
					|| r.basicRel(EBlockQltRelationType.CENTERED_HORIZONTALLY_WITH);
		
		throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	// ------------------------------------------------------
	// ---------------                       ----------------
	// ------------------------------------------------------
	
	/**
	 * Check an existence of the specified relation using membership function.
	 */
	public static final boolean hasRCC8RelationCalc(final double primPtMinX, final double primPtMinY
			, final double primPtMaxX, final double primPtMaxY
			, final double refPtMinX, final double refPtMinY
			, final double refPtMaxX, final double refPtMaxY
			, final Mu muPtMin, final Mu muPtMax, final Nu nu,
			final EBlockQltRelationType rel) {
		final _Comparator xa1xb1 = new _Comparator(primPtMinX, muPtMin, refPtMinX, muPtMin, nu);
		final _Comparator xa1xb2 = new _Comparator(primPtMinX, muPtMin, refPtMaxX, muPtMax, nu);
		final _Comparator xa2xb1 = new _Comparator(primPtMaxX, muPtMax, refPtMinX, muPtMin, nu);
		final _Comparator xa2xb2 = new _Comparator(primPtMaxX, muPtMax, refPtMaxX, muPtMax, nu);
		final _Comparator ya1yb1 = new _Comparator(primPtMinY, muPtMin, refPtMinY, muPtMin, nu);
		final _Comparator ya1yb2 = new _Comparator(primPtMinY, muPtMin, refPtMaxY, muPtMax, nu);
		final _Comparator ya2yb1 = new _Comparator(primPtMaxY, muPtMax, refPtMinY, muPtMin, nu);
		final _Comparator ya2yb2 = new _Comparator(primPtMaxY, muPtMax, refPtMaxY, muPtMax, nu);
		
		// --- JEPD RCC8 ---
				if (rel.equals(EBlockQltRelationType.DC))
					return xa2xb1.less() || xa1xb2.more() || ya2yb1.less() || ya1yb2.more();
				if (rel.equals(EBlockQltRelationType.EC))
					return (xa1xb1.less() && xa2xb1.equal() || xa2xb2.more() && xa1xb2.equal())
							&& ya2yb1.moreOrEq() && ya1yb2.lessOrEq()
							|| (ya1yb1.less() && ya2yb1.equal() || ya2yb2.more() && ya1yb2.equal())
							&& xa2xb1.moreOrEq() && xa1xb2.lessOrEq();
				if (rel.equals(EBlockQltRelationType.PO))
					return (xa1xb1.less() || ya1yb1.less() || xa2xb2.more() || ya2yb2.more()) // primary block should have sets outside the reference block
							&& (xa1xb1.more() || ya1yb1.more() || xa2xb2.less() || ya2yb2.less()); // reference block should have sets outside the primary block
				if (rel.equals(EBlockQltRelationType.TPP))
					return (xa1xb1.moreOrEq() && ya1yb1.moreOrEq() && xa2xb2.lessOrEq() && ya2yb2.lessOrEq()) // primary block inside reference block or equal
							&& (xa1xb1.equal() || ya1yb1.equal() || xa2xb2.equal() || ya2yb2.equal()) // one of the border should coincide
							&& (xa1xb1.more() || ya1yb1.more() || xa2xb2.less() || ya2yb2.less()); // reference block should have sets outside the primary block
				if (rel.equals(EBlockQltRelationType.NTPP))
					return xa1xb1.more() && ya1yb1.more() && xa2xb2.less() && ya2yb2.less(); // primary block inside reference block
				if (rel.equals(EBlockQltRelationType.EQUAL))
					return xa1xb1.equal() && ya1yb1.equal() && xa2xb2.equal() && ya2yb2.equal(); // primary block equals reference block
				if (rel.equals(EBlockQltRelationType.NTPPi))
					return xa1xb1.less() && ya1yb1.less() && xa2xb2.more() && ya2yb2.more(); // reference block inside primary block
				if (rel.equals(EBlockQltRelationType.TPPi))
					return (xa1xb1.lessOrEq() && ya1yb1.lessOrEq() && xa2xb2.moreOrEq() && ya2yb2.moreOrEq()) // reference block inside primary block or equal
							&& (xa1xb1.equal() || ya1yb1.equal() || xa2xb2.equal() || ya2yb2.equal()) // one of the border should coincide
							&& (xa1xb1.less() || ya1yb1.less() || xa2xb2.more() || ya2yb2.more()); // primary block should have sets outside the reference block
				// ---  ---
				// --- additionl relations ---
				
				if (rel.equals(EBlockQltRelationType.PP))
					return xa1xb1.moreOrEq() && ya1yb1.moreOrEq() && xa2xb2.lessOrEq() && ya2yb2.lessOrEq()
							&& (xa1xb1.more() || ya1yb1.more() || xa2xb2.less() || ya2yb2.less());
				if (rel.equals(EBlockQltRelationType.PPi))
					return xa1xb1.lessOrEq() && ya1yb1.lessOrEq() && xa2xb2.moreOrEq() && ya2yb2.moreOrEq()
							&& (xa1xb1.less() || ya1yb1.less() || xa2xb2.more() || ya2yb2.more());
				if (rel.equals(EBlockQltRelationType.P))
					return xa1xb1.moreOrEq() && ya1yb1.moreOrEq() && xa2xb2.lessOrEq() && ya2yb2.lessOrEq();
				if (rel.equals(EBlockQltRelationType.Pi))
					return xa1xb1.lessOrEq() && ya1yb1.lessOrEq() && xa2xb2.moreOrEq() && ya2yb2.moreOrEq();	
				if (rel.equals(EBlockQltRelationType.O))
					return xa2xb1.more() && xa1xb2.less() && ya2yb1.more() && ya1yb2.less();
				if (rel.equals(EBlockQltRelationType.C))
					return xa2xb1.moreOrEq() && xa1xb2.lessOrEq() && ya2yb1.moreOrEq() && ya1yb2.lessOrEq();
				if (rel.equals(EBlockQltRelationType.DR))
					return xa2xb1.lessOrEq() || xa1xb2.moreOrEq() || ya2yb1.lessOrEq() || ya1yb2.moreOrEq();
					// --- ---
				
				throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	
	/**
	 * Check an existence of the specified O-Direction (approximate direction) relation using membership function.
	 */
	public static final boolean hasODirecitionRelationCalc(final Point primPtMin, final Point primPtMax
			, final Point refPtMin, final Point refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu,
			final EBlockQltRelationType rel) {
		final _Comparator xa1xb1 = new _Comparator(primPtMin.x, muPtMin, refPtMin.x, muPtMin, nu);
		final _Comparator xa1xb2 = new _Comparator(primPtMin.x, muPtMin, refPtMax.x, muPtMax, nu);
		final _Comparator xa2xb1 = new _Comparator(primPtMax.x, muPtMax, refPtMin.x, muPtMin, nu);
		final _Comparator xa2xb2 = new _Comparator(primPtMax.x, muPtMax, refPtMax.x, muPtMax, nu);
		final _Comparator ya1yb1 = new _Comparator(primPtMin.y, muPtMin, refPtMin.y, muPtMin, nu);
		final _Comparator ya1yb2 = new _Comparator(primPtMin.y, muPtMin, refPtMax.y, muPtMax, nu);
		final _Comparator ya2yb1 = new _Comparator(primPtMax.y, muPtMax, refPtMin.y, muPtMin, nu);
		final _Comparator ya2yb2 = new _Comparator(primPtMax.y, muPtMax, refPtMax.y, muPtMax, nu);
		
		if (rel.equals(EBlockQltRelationType.NORTH_OF_O))
			return (xa2xb1.more() && xa1xb2.less() || xa1xb1.equal() || xa2xb2.equal())
					&& ya2yb1.lessOrEq() && ya1yb1.less();
		if (rel.equals(EBlockQltRelationType.NORTH_EAST_OF_O))
			return xa2xb2.more() && ya1yb1.less() && ( ya2yb1.lessOrEq() || xa1xb2.moreOrEq() );
		if (rel.equals(EBlockQltRelationType.EAST_OF_O))
			return (ya2yb1.more() && ya1yb2.less() || ya1yb1.equal() || ya2yb2.equal())
					&& xa1xb2.moreOrEq() && xa2xb2.more();
		if (rel.equals(EBlockQltRelationType.SOUTH_EAST_OF_O))
			return xa2xb2.more() && ya2yb2.more() && ( ya1yb2.moreOrEq() || xa1xb2.moreOrEq() );
		if (rel.equals(EBlockQltRelationType.SOUTH_OF_O))
			return (xa2xb1.more() && xa1xb2.less() || xa1xb1.equal() || xa2xb2.equal())
					&& ya1yb2.moreOrEq() && ya2yb2.more();
		if (rel.equals(EBlockQltRelationType.SOUTH_WEST_OF_O))
			return ya2yb2.more() && xa1xb1.less() && ( ya1yb2.moreOrEq() || xa2xb1.lessOrEq());
		if (rel.equals(EBlockQltRelationType.WEST_OF_O))
			return (ya2yb1.more() && ya1yb2.less() || ya1yb1.equal() || ya2yb2.equal())
					&& xa2xb1.lessOrEq() && xa1xb1.less();
		if (rel.equals(EBlockQltRelationType.NORTH_WEST_OF_O))
			return xa1xb1.less() && ya1yb1.less() && ( ya2yb1.lessOrEq() || xa2xb1.lessOrEq() );
		
		throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	
	/**
	 * Check an existence of the specified P-Direction (exact direction) relation using membership function.
	 */
	public static final boolean hasPDirecitionRelationCalc(final Point primPtMin, final Point primPtMax
			, final Point refPtMin, final Point refPtMax, final Mu muPtMin, final Mu muPtMax, final Nu nu,
			final EBlockQltRelationType rel) {
		final _Comparator xa1xb1 = new _Comparator(primPtMin.x, muPtMin, refPtMin.x, muPtMin, nu);
		final _Comparator xa1xb2 = new _Comparator(primPtMin.x, muPtMin, refPtMax.x, muPtMax, nu);
		final _Comparator xa2xb1 = new _Comparator(primPtMax.x, muPtMax, refPtMin.x, muPtMin, nu);
		final _Comparator xa2xb2 = new _Comparator(primPtMax.x, muPtMax, refPtMax.x, muPtMax, nu);
		final _Comparator ya1yb1 = new _Comparator(primPtMin.y, muPtMin, refPtMin.y, muPtMin, nu);
		final _Comparator ya1yb2 = new _Comparator(primPtMin.y, muPtMin, refPtMax.y, muPtMax, nu);
		final _Comparator ya2yb1 = new _Comparator(primPtMax.y, muPtMax, refPtMin.y, muPtMin, nu);
		final _Comparator ya2yb2 = new _Comparator(primPtMax.y, muPtMax, refPtMax.y, muPtMax, nu);
		
		if (rel.equals(EBlockQltRelationType.NORTH_OF_P))
			return xa1xb1.moreOrEq() && xa2xb2.lessOrEq() && ya2yb1.lessOrEq() && ya1yb1.less();
		if (rel.equals(EBlockQltRelationType.NORTH_EAST_OF_P))
			return xa1xb2.moreOrEq() && xa2xb2.more() && ya2yb1.lessOrEq() && ya1yb1.less();
		if (rel.equals(EBlockQltRelationType.EAST_OF_P))
			return ya1yb1.moreOrEq() && ya2yb2.lessOrEq() && xa1xb2.moreOrEq() && xa2xb2.more();
		if (rel.equals(EBlockQltRelationType.SOUTH_EAST_OF_P))
			return xa1xb2.moreOrEq() && xa2xb2.more() && ya1yb2.moreOrEq() && ya2yb2.more();
		if (rel.equals(EBlockQltRelationType.SOUTH_OF_P))
			return xa1xb1.moreOrEq() && xa2xb2.lessOrEq() && ya1yb2.moreOrEq() && ya2yb2.more();
		if (rel.equals(EBlockQltRelationType.SOUTH_WEST_OF_P))
			return xa2xb1.lessOrEq() && xa1xb1.less() && ya1yb2.moreOrEq() && ya2yb2.more();
		if (rel.equals(EBlockQltRelationType.WEST_OF_P))
			return ya1yb1.moreOrEq() && ya2yb2.lessOrEq() && xa2xb1.lessOrEq() && xa1xb1.less();
		if (rel.equals(EBlockQltRelationType.NORTH_WEST_OF_P))
			return xa2xb1.lessOrEq() && xa1xb1.less() && ya2yb1.lessOrEq() && ya1yb1.less();
		
		throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	
	/**
	 * Check an existence of the specified P-Direction (exact direction) relation using membership function.
	 */
	public static final boolean hasAlignmentRelationCalc(final Point primPtMin, final Point primPtMax
			, final Point refPtMin, final Point refPtMax, final Mu muPtMin, final Mu muPtMax, final Mu muCenterPt, final Nu nu,
			final EBlockQltRelationType rel) {
		final _Comparator xa1xb1 = new _Comparator(primPtMin.x, muPtMin, refPtMin.x, muPtMin, nu);
		final _Comparator xa2xb2 = new _Comparator(primPtMax.x, muPtMax, refPtMax.x, muPtMax, nu);
		final _Comparator ya1yb1 = new _Comparator(primPtMin.y, muPtMin, refPtMin.y, muPtMin, nu);
		final _Comparator ya2yb2 = new _Comparator(primPtMax.y, muPtMax, refPtMax.y, muPtMax, nu);
		
		// --- main relations ---
		if (rel.equals(EBlockQltRelationType.LEFT_ALIGNED_WITH))
			return xa1xb1.equal();
		if (rel.equals(EBlockQltRelationType.RIGHT_ALIGNED_WITH))
			return xa2xb2.equal();
		if (rel.equals(EBlockQltRelationType.CENTERED_VERTICALLY_WITH))
			return equal((primPtMax.x+primPtMin.x)/2, muCenterPt, (refPtMax.x+refPtMin.x)/2, muCenterPt, nu);
		if (rel.equals(EBlockQltRelationType.TOP_ALIGNED_WITH))
			return ya1yb1.equal();
		if (rel.equals(EBlockQltRelationType.BOTTOM_ALIGNED_WITH))
			return ya2yb2.equal();
		if (rel.equals(EBlockQltRelationType.CENTERED_HORIZONTALLY_WITH))
			return equal((primPtMax.y+primPtMin.y)/2, muCenterPt, (refPtMax.y+refPtMin.y)/2, muCenterPt, nu);
		// ---  ---
		
		// --- additional relations ---
		if (rel.equals(EBlockQltRelationType.HORIZONTALLY_ALIGNED_WITH))
			return xa1xb1.equal() || xa2xb2.equal()
					|| equal((primPtMax.x+primPtMin.x)/2, muCenterPt, (refPtMax.x+refPtMin.x)/2, muCenterPt, nu);
		if (rel.equals(EBlockQltRelationType.VERTICALLY_ALIGNED_WITH))
			return ya1yb1.equal() || ya2yb2.equal()
					|| equal((primPtMax.y+primPtMin.y)/2, muCenterPt, (refPtMax.y+refPtMin.y)/2, muCenterPt, nu);
		if (rel.equals(EBlockQltRelationType.HAS_ALIGNMENT_RELATION))
			return xa1xb1.equal() || xa2xb2.equal()
					|| equal((primPtMax.x+primPtMin.x)/2, muCenterPt, (refPtMax.x+refPtMin.x)/2, muCenterPt, nu)
					|| ya1yb1.equal() || ya2yb2.equal()
					|| equal((primPtMax.y+primPtMin.y)/2, muCenterPt, (refPtMax.y+refPtMin.y)/2, muCenterPt, nu);
		// ---  ---
		
		throw new UnknownValueFromPredefinedList(log, rel.getName());
	}
	
	/**
	 * Check an existence of the horizontally centering.
	 */
	public static final boolean hasHorCenteringRelationCalc(final Resource primInst, final Resource refInst
			, final Model qntModel, final Mu muCenterPt, final Nu nu) {
		double a = (QntBlockLib.getXMin(primInst, qntModel) + QntBlockLib.getXMax(primInst, qntModel))/2;
		double b = (QntBlockLib.getXMin(refInst, qntModel) + QntBlockLib.getXMax(refInst, qntModel))/2;
		return isCenteredCalc(a, b, muCenterPt, nu);
	}
	
	/**
	 * Check an existence of the vertical centering.
	 */
	public static final boolean hasVertCenteringRelationCalc(final Resource primInst, final Resource refInst
			, final Model qntModel, final Mu muCenterPt, final Nu nu) {
		double a = (QntBlockLib.getYMin(primInst, qntModel) + QntBlockLib.getYMax(primInst, qntModel))/2;
		double b = (QntBlockLib.getYMin(refInst, qntModel) + QntBlockLib.getYMax(refInst, qntModel))/2;
		return isCenteredCalc(a, b, muCenterPt, nu);
	}
	
	/**
	 * @param primCenterPt center point of the primary interval.
	 * @param refCenterPt center point of the secondary interval.
	 * @param muCenterPt membership function for the center points.
	 * @param nu true function
	 * @return <code>true</code>, if the center relation is difined.
	 */
	public static final boolean isCenteredCalc(double primCenterPt, double refCenterPt
			,final Mu muCenterPt, final Nu nu) {
		return equal(primCenterPt, muCenterPt, refCenterPt, muCenterPt, nu);
	}
	
	
	
	
//	@Deprecated
//	public static final double EPS = Float.MIN_NORMAL;
//	@Deprecated
//	public static final EBlockQltRelation calcRelation(final Resource primInst, final Resource refInst
//			, final EBlockQltRelationType relationType, final Model model) {
//		switch (relationType) {
//		case RCC8: {
//			final float xMinPrim = QntBlockAdapter2.getXMin(primInst, model);
//			final float yMinPrim = QntBlockAdapter2.getYMin(primInst, model);
//			final float xMaxPrim = QntBlockAdapter2.getXMax(primInst, model);
//			final float yMaxPrim = QntBlockAdapter2.getYMax(primInst, model);
//			
//			final float xMinRef = QntBlockAdapter2.getXMin(refInst, model);
//			final float yMinRef = QntBlockAdapter2.getYMin(refInst, model);
//			final float xMaxRef = QntBlockAdapter2.getXMax(refInst, model);
//			final float yMaxRef = QntBlockAdapter2.getYMax(refInst, model);
//			
//			if (
//					(xMinPrim < xMinRef && xMaxPrim == xMinRef && yMaxPrim >= yMinRef && yMinPrim <= yMaxRef)
//					|| ( xMaxPrim > xMaxRef && xMinPrim == xMaxRef && yMaxPrim >= yMinRef && yMinPrim <= yMaxRef)
//					|| (yMinPrim < yMinRef && yMaxPrim == yMinRef && xMaxPrim >= xMinRef && xMinPrim <= xMaxRef)
//					|| ( yMaxPrim > yMaxRef && yMinPrim == yMaxRef && xMaxPrim >= xMinRef && xMinPrim <= xMaxRef)
//			)
//				return EBlockQltRelation.EC;
//			else if (xMaxPrim < xMinRef || xMinPrim > xMaxRef || yMaxPrim < yMinRef || yMinPrim > yMaxRef)
//				return EBlockQltRelation.DC;
//			else if (
//					// primary block inside reference block or equal
//					xMinPrim >= xMinRef && yMinPrim >= yMinRef && xMaxPrim <= xMaxRef && yMaxPrim <= yMaxRef
//					// one of the border should coincide
//					&& (xMinPrim == xMinRef || yMinPrim == yMinRef || xMaxPrim == xMaxRef || yMaxPrim == yMaxRef)
//					// reference block should have sets outside the primary block
//					&& ( xMinPrim > xMinRef || yMinPrim > yMinRef || xMaxPrim < xMaxRef || yMaxPrim < yMaxRef )
//					)
//				return EBlockQltRelation.TPP;
//			else if (// primary block inside reference block
//					xMinPrim > xMinRef && yMinPrim > yMinRef && xMaxPrim < xMaxRef && yMaxPrim < yMaxRef
//					)
//				return EBlockQltRelation.NTPP;
//			else if (// primary block equals reference block
//					xMinPrim == xMinRef && yMinPrim == yMinRef && xMaxPrim == xMaxRef && yMaxPrim == yMaxRef
//					)
//				return EBlockQltRelation.EQUAL;
//			else if (// reference block inside primary block
//					xMinPrim < xMinRef && yMinPrim < yMinRef && xMaxPrim > xMaxRef && yMaxPrim > yMaxRef
//			)
//				return EBlockQltRelation.NTPPi;
//			else if (
//					// primary block inside reference block or equal
//					xMinPrim <= xMinRef && yMinPrim <= yMinRef && xMaxPrim >= xMaxRef && yMaxPrim >= yMaxRef
//					// one of the border should coincide
//					&& (xMinPrim == xMinRef || yMinPrim == yMinRef || xMaxPrim == xMaxRef || yMaxPrim == yMaxRef)
//					// primary block should have sets outside the reference block
//					&& ( xMinPrim < xMinRef || yMinPrim < yMinRef || xMaxPrim > xMaxRef || yMaxPrim > yMaxRef )
//					)
//				return EBlockQltRelation.TPPi;
//			else if (
//					// primary block should have sets outside the reference block
//					( xMinPrim < xMinRef || yMinPrim < yMinRef || xMaxPrim > xMaxRef || yMaxPrim > yMaxRef )
//					// reference block should have sets outside the primary block
//				&& ( xMinPrim > xMinRef || yMinPrim > yMinRef || xMaxPrim < xMaxRef || yMaxPrim < yMaxRef )
//				)
//				
//				return EBlockQltRelation.PO;
//		}
//		}
//		return null;
//	}
	
	
	
	// ========  ========
	
	
	
	// ======== ADDITIONAL COMPARATIONS. STATIC ========
	
	
	// ========  ========
}
