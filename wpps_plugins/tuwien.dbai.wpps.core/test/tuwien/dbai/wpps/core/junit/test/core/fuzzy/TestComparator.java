/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test.core.fuzzy;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMBorderNu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMLeftBorderMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMRightBorderMu;
import tuwien.dbai.wpps.core.fuzzy.FuzzyComparator;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.IntervalMuDouble;
import tuwien.dbai.wpps.core.fuzzy.Nu;
import tuwien.dbai.wpps.core.module.InstanceAdaptersModule;
import tuwien.dbai.wpps.core.module.OntologyModule;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * 
 * 	<qltbm-border-mu-type>INTERVAL</qltbm-border-mu-type>
	<qltbm-left-border-mu>
		<left-point>-0.1</left-point>
		<right-point>0.2</right-point>
	</qltbm-left-border-mu>
	<qltbm-right-border-mu>
		<left-point>-0.3</left-point>
		<right-point>0.1</right-point>
	</qltbm-right-border-mu>
	<qltbm-center-mu>
		<left-point>0.1</left-point>
		<right-point>0.1</right-point>
	</qltbm-center-mu>

	<qltbm-border-nu>
		<center>0.5</center>
		<delta>0</delta>
	</qltbm-border-nu>
	
	
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 5, 2012 4:51:02 PM
 */
public class TestComparator {

	FuzzyComparator c = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Injector inj = Guice.createInjector(new InstanceAdaptersModule(), new OntologyModule(), new WPPSConfigModule());
		
		final IMuZeroDouble muPtMin = inj.getInstance(Key.get(IMuZeroDouble.class, AnnotQltBMLeftBorderMu.class));
		final IMuZeroDouble muPtMax = inj.getInstance(Key.get(IMuZeroDouble.class, AnnotQltBMRightBorderMu.class));
		final Nu nu = inj.getInstance(Key.get(Nu.class, AnnotQltBMBorderNu.class));
		this.c = new FuzzyComparator(new IMuZeroDouble[][]{{muPtMin, muPtMax}, {muPtMin, muPtMax}}, nu);
	
		MatcherAssert.assertThat(((IntervalMuDouble)muPtMin).getInterval(), Matchers.equalTo(new Point2D(-0.1, 0.2)));
		MatcherAssert.assertThat(((IntervalMuDouble)muPtMax).getInterval(), Matchers.equalTo(new Point2D(-0.3, 0.1)));
		MatcherAssert.assertThat(nu.getCentre(), Matchers.equalTo(0.5));
		MatcherAssert.assertThat(nu.getDelta(), Matchers.equalTo(0d));
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.fuzzy.FuzzyComparator#less(double, double, int, int, int)}.
	 */
	@Test
	public void testLess() {
		MatcherAssert.assertThat(c.less(9, 10, 0, 0, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.less(8.1, 8.39, 0, 1, 1), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.less(8.29, 8, 0, 1, 1), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.less(8, 8.19, 0, 0, 0), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.less(8, 8.29, 0, 0, 1), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.less(8, 7.71, 1, 1, 0), Matchers.equalTo(false));
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.fuzzy.FuzzyComparator#lessOrEq(double, double, int, int, int)}.
	 */
	@Test
	public void testLessOrEq() {
		MatcherAssert.assertThat(c.lessOrEq(9, 10, 0, 0, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.lessOrEq(20, 20, 1, 0, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.lessOrEq(30, 30, 0, 1, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.lessOrEq(50, 50, 1, 1, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.lessOrEq(50, 50.1, 1, 1, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.lessOrEq(50.29, 50, 1, 1, 1), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.lessOrEq(1, 2, 1, 1, 1), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.lessOrEq(50.7, 50.3, 1, 1, 1), Matchers.equalTo(false));
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.fuzzy.FuzzyComparator#equal(double, double, int, int, int)}.
	 */
	@Test
	public void testEqual() {
		MatcherAssert.assertThat(c.equal(9, 10, 0, 0, 0), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.equal(20, 20, 1, 0, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.equal(30, 30, 0, 1, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.equal(50, 50, 1, 1, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.equal(50, 50.1, 1, 1, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.equal(50.29, 50, 1, 1, 1), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.equal(50.7, 50.3, 1, 1, 1), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.equal(1, 2, 1, 1, 1), Matchers.equalTo(false));
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.fuzzy.FuzzyComparator#more(double, double, int, int, int)}.
	 */
	@Test
	public void testMore() {
		MatcherAssert.assertThat(c.more(10, 9, 0, 0, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.more(8.39, 8.1, 1, 0, 1), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.more(8, 8.29, 1, 1, 1), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.more(8.19, 8, 0, 0, 0), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.more(8.29, 8, 0, 1, 1), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.more(8, 7.71, 1, 1, 0), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.more(1, 2, 1, 1, 0), Matchers.equalTo(false));
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.fuzzy.FuzzyComparator#moreOrEq(double, double, int, int, int)}.
	 */
	@Test
	public void testMoreOrEq() {
		MatcherAssert.assertThat(c.moreOrEq(10, 9, 0, 0, 0), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.moreOrEq(2, 1, 1, 0, 1), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.moreOrEq(1, 2, 1, 1, 1), Matchers.equalTo(false));
		MatcherAssert.assertThat(c.moreOrEq(10, 10, 0, 1, 1), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.moreOrEq(10, 10.29, 0, 1, 1), Matchers.equalTo(true));
		MatcherAssert.assertThat(c.moreOrEq(10, 10.29, 1, 0, 0), Matchers.equalTo(false));
	}

}
