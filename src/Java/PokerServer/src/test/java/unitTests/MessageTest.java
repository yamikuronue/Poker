/**
 * 
 */
package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import pokerServer.Message;
import pokerServer.interfaces.Client;

/**
 * Basic message functionality testing. Really boring. 
 * @author bgreen
 *
 */
public class MessageTest {

	/**
	 * Tests adding and fetching a parameter
	 */
	@Test
	public void parameterCanBeAdded() {
		Message oot = new testMessage(null);
		oot.addParameter("Test", 5);
		assertEquals(5, oot.getParameter("Test"));
		
	}
	
	/**
	 * Tests adding a parameter using the Set method
	 */
	@Test
	public void parameterCanBeAddedUsingSet() {
		Message oot = new testMessage(null);
		oot.setParameter("Test", 5);
		assertEquals(5, oot.getParameter("Test"));
	}
	
	/**
	 * Tests overriding a parameter using the Set method
	 */
	@Test
	public void parameterCanBeRedefinedUsingSet() {
		Message oot = new testMessage(null);
		oot.addParameter("Test", 5);
		oot.setParameter("Test", 6);
		assertEquals(6, oot.getParameter("Test"));
	}
	
	/**
	 * Tests that an invalid parameter cannot be retrieved
	 */
	@Test(expected=IllegalArgumentException.class)
	public void invalidParamCannotBeFetched() {
		Message oot = new testMessage(null);
		oot.getParameter("Test");
	}

	/**
	 * Tests that a parameter cannot be retrieved after removing it
	 */
	@Test(expected=IllegalArgumentException.class)
	public void parameterCanBeRemoved() {
		Message oot = new testMessage(null);
		oot.addParameter("Test", 5);
		oot.removeParameter("Test");
		oot.getParameter("Test");
	}

	/**
	 * Exists solely so we can instantiate a Message aside from implementation
	 * @author bgreen
	 */
	private class testMessage extends Message {
		public testMessage(Client clientInvolved) {
			super(clientInvolved);
		}

		@Override
		public boolean isValid() {
			return true;
		}
	}
}
