package edu.towson.cis.cosc603.project4.vendingmachine;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VendingMachineItemTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests when the item price is negative
	 */
	@Test(expected = VendingMachineException.class)
	public void testVendingMachineNegativePrice() {
		new VendingMachineItem("candy", -1);
	}

	/**
	 * Tests the name of the item
	 */
	@Test
	public void testGetName() {
		String name  = "candy";
		VendingMachineItem item = new VendingMachineItem(name, 1);
		assertEquals(name, item.getName());
	}

	/**
	 * Tests the price of the item
	 */
	@Test
	public void testGetPrice() {
		VendingMachineItem item = new VendingMachineItem("candy", 1);
		assertEquals(1, item.getPrice(), 0.001);
	}

}
