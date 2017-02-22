/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test.physmodel.bgm.adp.instadp.rdfimpllib;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMBorderNu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMLeftBorderMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMRightBorderMu;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.IntervalMuDouble;
import tuwien.dbai.wpps.core.fuzzy.Nu;
import tuwien.dbai.wpps.core.module.InstanceAdaptersModule;
import tuwien.dbai.wpps.core.module.OntologyModule;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BlockQltRelationsLib;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * 
 * <qltbm-border-mu-type>INTERVAL</qltbm-border-mu-type>
	<qltbm-left-border-mu>
		<left-point>-0.1</left-point>
		<right-point>0.2</right-point>
	</qltbm-left-border-mu>
	<qltbm-right-border-mu>
		<left-point>-0.3</left-point>
		<right-point>0.1</right-point>
	</qltbm-right-border-mu>
	<qltbm-center-mu>
		<left-point>-0.1</left-point>
		<right-point>0.1</right-point>
	</qltbm-center-mu>

	<qltbm-border-nu>
		<center>0.5</center>
		<delta>0</delta>
	</qltbm-border-nu>
	
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 5, 2012 7:53:00 PM
 */
public class TestQltBlockRelationsLib {

	IMuZeroDouble muPtMin;
	IMuZeroDouble muPtMax;
	Nu nu;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Injector inj = Guice.createInjector(new InstanceAdaptersModule(), new OntologyModule(), new WPPSConfigModule());
		
		muPtMin = inj.getInstance(Key.get(IMuZeroDouble.class, AnnotQltBMLeftBorderMu.class));
		muPtMax = inj.getInstance(Key.get(IMuZeroDouble.class, AnnotQltBMRightBorderMu.class));
		nu = inj.getInstance(Key.get(Nu.class, AnnotQltBMBorderNu.class));
//		this.c = new Comparator(new IMuZeroDouble[][]{{muPtMin, muPtMax}, {muPtMin, muPtMax}}, nu);
	
		MatcherAssert.assertThat(((IntervalMuDouble)muPtMin).getInterval(), Matchers.equalTo(new Point2D(-0.1, 0.2)));
		MatcherAssert.assertThat(((IntervalMuDouble)muPtMax).getInterval(), Matchers.equalTo(new Point2D(-0.3, 0.1)));
		MatcherAssert.assertThat(nu.getCentre(), Matchers.equalTo(0.5));
		MatcherAssert.assertThat(nu.getDelta(), Matchers.equalTo(0d));
	}

//	@Test
//	public void test() {
//		
//	}
	
	
	// ==============================
	// ========  O-Direction ========
	// ==============================
	@Test
	public void hasODirectionNorthOfComp() {
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasODirectionNorthOfComp(
				5, 10, 50, 60, 0, 6, 59.71, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasODirectionNorthOfComp(
				5, 10, 50, 60, 0, 6, 59.5, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
		MatcherAssert.assertThat(
				BlockQltRelationsLib.hasODirectionNorthOfComp(
					5, 10, 50, 60, 5, 6, 61, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
		MatcherAssert.assertThat(
				BlockQltRelationsLib.hasODirectionNorthOfComp(
					5, 10, 50, 60, 10, 11, 61, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
		
	}
	
	
	// ==============================
	// =====                    =====
	// ==============================
	
	// ==============================
	// ========  P-Direction ========
	// ==============================
	@Test
	public void hasPDirectionSouthEastOfComp() {
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasPDirectionSouthEastOfComp(
				5, 10, 50, 58.1, 5, 50, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasPDirectionSouthEastOfComp(
				5, 10, 45, 60, 5, 50, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasPDirectionSouthEastOfComp(
				5, 10, 50, 60, 1, 1, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasPDirectionSouthEastOfComp(
				5, 10, 50, 60, 100, 100, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
		
	}
	
	// ==============================
	// =====                    =====
	// ==============================
	
	
	// ==============================
	// ======       RCC8      =======
	// ==============================
	@Test
	public void hasRCC8_P() {
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_P(
				5, 10, 48.9, 60, 5, 10.1, 49, 60.1, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_P(
					1, 10, 48.9, 60, 5, 10.1, 49, 60.1, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_P(
				5, 10, 50, 60, 4, 11, 50, 60, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_P(
				5, 10, 50, 60, 11, 12, 51, 52, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
	}
	@Test
	public void hasRCC8_O() {
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_O(
				5, 10, 48.1, 60, 5, 10.1, 49, 60.1, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_O(
					5, 10, 50, 60, 9.8, 11, 60, 70, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_O(
					1, 10, 48.1, 60, 5, 10.1, 49, 60.1, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_O(
					5, 10, 50, 60, 11, 12, 51, 52, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
	}
	
	@Test
	public void hasRCC8_DR() {
		
//		* (98.5, 1807.58), (107.5, 1824.58)
//		** 98.5, 1824.25), (112.5, 1841.25
				
		MatcherAssert.assertThat(
			BlockQltRelationsLib.hasRCC8_DR(
					98.5, 107.5, 1807.58, 1824.51
					, 98.5, 112.5, 1824.25, 1841.25, muPtMin, muPtMax, nu)
					, Matchers.equalTo(true));
		
		MatcherAssert.assertThat(
				BlockQltRelationsLib.hasRCC8_DR(
				98.5, 112.5, 1824.25, 1841.25
				, 98.5, 107.5, 1807.58, 1824.51, muPtMin, muPtMax, nu)
				, Matchers.equalTo(true));
		
//		MatcherAssert.assertThat(
//			QltBlockRelationsLib.hasRCC8_O(
//					5, 10, 50, 60, 9.8, 11, 60, 70, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
//		MatcherAssert.assertThat(
//			QltBlockRelationsLib.hasRCC8_O(
//					1, 10, 48.1, 60, 5, 10.1, 49, 60.1, muPtMin, muPtMax, nu) , Matchers.equalTo(true));
//		MatcherAssert.assertThat(
//			QltBlockRelationsLib.hasRCC8_O(
//					5, 10, 50, 60, 11, 12, 51, 52, muPtMin, muPtMax, nu) , Matchers.equalTo(false));
	}
	
	// ==============================
	// =====                    =====
	// ==============================
	
	
	

}
