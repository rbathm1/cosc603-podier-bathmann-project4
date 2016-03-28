package edu.towson.cis.cosc603.project4.vendingmachine;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
	
	private VendingMachine vendingMachine;

	@Before
	public void setUp() throws Exception {
		vendingMachine = new VendingMachine();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests adding an item in an unoccupied slot
	 */
	@Test
	public void testAddItemUnoccupied() {
		VendingMachineItem chocolateBar = new VendingMachineItem("Chocolate bar", 1.5);
		vendingMachine.addItem(chocolateBar, VendingMachine.A_CODE);
		
		assertEquals(chocolateBar, vendingMachine.getItem(VendingMachine.A_CODE));
	}
	
	/**
	 * Tests adding an item in an occupied slot
	 */
	@Test(expected = VendingMachineException.class)
	public void testAddItemOccupied() {
		VendingMachineItem chocolateBar = new VendingMachineItem("Chocolate bar", 1.5);
		VendingMachineItem chips = new VendingMachineItem("Chips", 1.0);
		vendingMachine.addItem(chocolateBar, VendingMachine.A_CODE);
		vendingMachine.addItem(chips, VendingMachine.A_CODE);
	}
	
	/**
	 * Tests adding an item using a bad slot code
	 */
	@Test(expected = VendingMachineException.class)
	public void testAddItemBadCode() {
		vendingMachine.addItem(null, "rubbish");
	}

	/**
	 * Tests removing an occupied slot by ensuring slot is unoccupied after removal
	 */
	@Test
	public void testRemoveItemOccupied() {
		VendingMachineItem chocolateBar = new VendingMachineItem("Chocolate bar", 1.5);
		vendingMachine.addItem(chocolateBar, VendingMachine.B_CODE);
		vendingMachine.removeItem(VendingMachine.B_CODE);
		
		assertNull(vendingMachine.getItem(VendingMachine.B_CODE));
	}
	
	/**
	 * Tests the method return value from removing an item
	 */
	@Test
	public void testRemoveItemReturnValue() {
		VendingMachineItem chocolateBar = new VendingMachineItem("Chocolate bar", 1.5);
		vendingMachine.addItem(chocolateBar, VendingMachine.D_CODE);
		VendingMachineItem removedItem = vendingMachine.removeItem(VendingMachine.D_CODE);
		
		assertEquals(chocolateBar, removedItem);
	}
	
	/**
	 * Tests removing the item from an unoccupied slot
	 */
	@Test(expected = VendingMachineException.class)
	public void testRemoveItemUnoccupied() {
		vendingMachine.removeItem(VendingMachine.C_CODE);
	}
	
	/**
	 * Tests removing the item using a bad slot code
	 */
	@Test(expected = VendingMachineException.class)
	public void testRemoveItemBadCode() {
		vendingMachine.removeItem("garbage");
	}

	/**
	 * Test inserting an amount zero or greater
	 */
	@Test
	public void testInsertMoneyPositive() {
		vendingMachine.insertMoney(1);
		
		assertEquals(1, vendingMachine.getBalance(), 0.001);
	}
	
	/**
	 * Test adding an amount less than zero
	 */
	@Test(expected = VendingMachineException.class)
	public void testInsertMoneyNegative() {
		vendingMachine.insertMoney(-1);
	}

	/**
	 * Tests getting the balance
	 */
	@Test
	public void testGetBalance() {
		vendingMachine.insertMoney(1);
		
		assertEquals(1, vendingMachine.getBalance(), 0.001);
	}
	
	/**
	 * Add an item to the vending machine and insert money
	 * @param code the slot code to add the item
	 * @param amount the amount of money to insert
	 */
	private void setupPurchase(String code, double amount){
		VendingMachineItem chocolateBar = new VendingMachineItem("Chocolate bar", 1.5);
		vendingMachine.addItem(chocolateBar, code);
		vendingMachine.insertMoney(amount);
	}

	/**
	 * Test to ensure an item is removed when purchased
	 */
	@Test
	public void testMakePurchaseItemRemoved() {
		setupPurchase(VendingMachine.A_CODE, 1.5);
		vendingMachine.makePurchase(VendingMachine.A_CODE);
		assertNull(vendingMachine.getItem(VendingMachine.A_CODE));
	}
	
	/**
	 * Test to ensure the balance is updated after purchase
	 */
	@Test
	public void testMakePurchaseBalance() {
		setupPurchase(VendingMachine.B_CODE, 1.5);
		vendingMachine.makePurchase(VendingMachine.B_CODE);
		assertEquals(0, vendingMachine.getBalance(), 0.001);
	}
	
	/**
	 * Test to ensure the balance is updated after purchase when
	 * the remaining balance is nonzero
	 */
	@Test
	public void testMakePurchaseBalanceNonzero() {
		setupPurchase(VendingMachine.B_CODE, 2);
		vendingMachine.makePurchase(VendingMachine.B_CODE);
		assertEquals(2-1.5, vendingMachine.getBalance(), 0.001);
	}
	
	/**
	 * Test to ensure the return value is true after purchase
	 */
	@Test
	public void testMakePurchaseReturnValue() {
		setupPurchase(VendingMachine.C_CODE, 1.5);
		boolean returnValue = vendingMachine.makePurchase(VendingMachine.C_CODE);
		assertTrue(returnValue);
	}
	
	/**
	 * Test to ensure the return value is false if the balance is insufficient
	 * to purchase the item
	 */
	@Test
	public void testMakePurchaseInsufficient() {
		setupPurchase(VendingMachine.D_CODE, 1);
		boolean returnValue = vendingMachine.makePurchase(VendingMachine.D_CODE);
		assertFalse(returnValue);
	}
	
	/**
	 * Test to ensure the return value is false if the slot is unoccupied
	 */
	@Test
	public void testMakePurchaseUnoccupied() {
		boolean returnValue = vendingMachine.makePurchase(VendingMachine.A_CODE);
		assertFalse(returnValue);
	}
	
	/**
	 * Test purchasing an item with a bad slot code
	 */
	@Test(expected = VendingMachineException.class)
	public void testMakePurchaseBadCode() {
		vendingMachine.makePurchase("trash");
	}

	/**
	 * Test to ensure the change returned is equal to the balance
	 */
	@Test
	public void testReturnChangeEqualsBalance() {
		vendingMachine.insertMoney(1);
		double change = vendingMachine.returnChange();
		
		assertEquals(1, change, 0.001);
	}
	
	/**
	 * Test to ensure the balance is reset to zero
	 */
	@Test
	public void testReturnChangeBalanceZero() {
		vendingMachine.insertMoney(1);
		vendingMachine.returnChange();
		
		assertEquals(0, vendingMachine.getBalance(), 0.001);
	}

}
