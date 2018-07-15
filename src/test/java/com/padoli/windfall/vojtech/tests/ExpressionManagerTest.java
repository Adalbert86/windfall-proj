package com.padoli.windfall.vojtech.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.padoli.windfall.vojtech.calculator.ExpressionManager;
import com.padoli.windfall.vojtech.exceptions.DivisionByZeroException;
import com.padoli.windfall.vojtech.exceptions.ExprUnresolvedVariablesException;
import com.padoli.windfall.vojtech.exceptions.MismatchedBracketsException;

/**
 * 
 * @author vojtech
 *
 */
public class ExpressionManagerTest {

	private ExpressionManager em;

	@Before
	public void prepareTests() {

		em = ExpressionManager.getInstance();

		if (em == null)
			fail("Could not create an instance of ExpressionManager");
	}

	@Test
	public void testBasics() {

		assertTrue(em.evaluate("2+5").equals(7f));
		assertEquals(15f, (float) em.evaluate("5+10"), 0);
		assertEquals(5f, (float) em.evaluate("10-5"), 0);
		assertEquals(-5f, (float) em.evaluate("5-10"), 0);

		assertEquals(20f, (float) em.evaluate("(2*10)"), 0);
		assertEquals(20f, (float) em.evaluate("( 2* 10  )"), 0);

		assertEquals(101f, (float) em.evaluate("1+50*2"), 0);
		assertEquals(102f, (float) em.evaluate("(1+50)*2"), 0);

		assertEquals(2f, (float) em.evaluate("2*6-(23+7)/(1+2)"), 0);
		assertEquals(-8.66f, (float) em.evaluate("2*6-23+7/(1+2)"), 0.01);

		assertEquals(1f, (float) em.evaluate("1"), 0);

		// does not support unary operator at this time!
		assertEquals(-1f, (float) em.evaluate("0-1"), 0);
	}

	@Test
	public void testBasics2() {

		assertEquals(212f, (float) em.evaluate("100 * 2 + 12"), 0);
		assertEquals(1400f, (float) em.evaluate("100 * ( 2 + 12 )"), 0);
		assertEquals(100f, (float) em.evaluate("100 * ( 2 + 12 ) / 14"), 0);
		assertEquals(12f, (float) em.evaluate("1+1+10"), 0);
	}

	@Test
	public void testPresenceUnresolvedVariables() {

		try {

			assertEquals(212f, (float) em.evaluate("100 * B2 + 12"), 0);
			fail("ExprUnresolvedVariablesException is expected here!");

		} catch (ExprUnresolvedVariablesException e) {
			// do nothing this is expected
		}

	}

	@Test
	public void testDivisionByZero() {

		try {
			assertEquals(102f, (float) em.evaluate("2*6-(23+7)/(1-1)"), 0);
			fail("Exception is expected here!");

		} catch (DivisionByZeroException e) {
			// do nothing this is expected
		}

	}

	@Test
	public void testEmptyExpr() {

		assertEquals(0f, (float) em.evaluate(""), 0);

		assertEquals(0f, (float) em.evaluate("()"), 0);

	}

	@Test
	public void testMismatchedExpr() {

		try {
			assertEquals(20f, (float) em.evaluate("(2*10"), 0);
			fail("Exception is expected here!");

		} catch (MismatchedBracketsException e) {
			// do nothing this is expected
		}

		// 2

		try {
			assertEquals(0f, (float) em.evaluate("())"), 0);
			fail("Exception is expected here!");

		} catch (MismatchedBracketsException e) {
			// do nothing this is expected
		}

		// 3

		try {
			assertEquals(0f, (float) em.evaluate("())))("), 0);
			fail("Exception is expected here!");

		} catch (MismatchedBracketsException e) {
			// do nothing this is expected
		}

	}

}
