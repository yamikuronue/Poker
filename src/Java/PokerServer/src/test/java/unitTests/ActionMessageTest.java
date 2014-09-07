package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import pokerServer.ActionMessage;
import pokerServer.ActionMessage.Action;

/**
 * Tests for the ActionMessage class
 * @author bgreen
 *
 */
public class ActionMessageTest {

	/**
	 * Verifies that an invalid state type makes a state message invalid.
	 * Rule: State type must be one of "Join", "Bet", "Fold", or "Quit"
	 */
	@Test
	public void invalidStateFailsValidation() {
		ActionMessage oot = new ActionMessage(null, null);
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a lack of GameID makes a Join message invalid
	 * Rule: Join message must contain a GameID
	 */
	@Test
	public void joinWithoutGameIDFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.JOIN, null);
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that extra parameters make a Join message invalid
	 * Rule: Join message cannot contain extra parameters
	 */
	@Test
	public void joinWithExtraParamsFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.JOIN, null);
		oot.addParameter("GameID", 12345);
		oot.addParameter("Ooga", "Chaka");
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that wrong parameters make a Join message invalid
	 * Rule: Join message must contain only GameID
	 */
	@Test
	public void joinWithWrongParamsFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.JOIN, null);
		oot.addParameter("Ooga", "Chaka");
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that an invalid GameID makes a Join message invalid
	 * Rule: GameID must be numeric
	 */
	@Test
	public void joinWithInvalidGameIDFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.JOIN, null);
		oot.addParameter("GameID", "ABC123");
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a GameID and only a GameID make a Join message valid
	 * Rule: Join message must contain a GameID
	 */
	@Test
	public void joinWithGameIDPassesValidation() {
		ActionMessage oot = new ActionMessage(Action.JOIN, null);
		oot.addParameter("GameID", 12345);
		assertTrue(oot.isValid());
	}
	
	/**
	 * Verifies that a lack of Quit parameter makes a Fold message invalid
	 * Rule: Fold message must contain a Quit parameter
	 */
	@Test
	public void foldWithoutQuitFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.FOLD, null);
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that extra parameters make a Fold message invalid
	 * Rule: Fold message cannot contain extra parameters
	 */
	@Test
	public void foldWithExtraParamsFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.FOLD, null);
		oot.addParameter("Quit", true);
		oot.addParameter("Ooga", "Chaka");
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that wrong parameters make a Fold message invalid
	 * Rule: Fold message must contain only Quit
	 */
	@Test
	public void foldWithWrongParamsFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.FOLD, null);
		oot.addParameter("Ooga", "Chaka");
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that an invalid Quit parameter makes a Fold message invalid
	 * Rule: Quit parameter must be a boolean
	 */
	@Test
	public void foldWithInvalidQuitFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.FOLD, null);
		oot.addParameter("Quit", "true");
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a Quit parameter makes a Fold message valid
	 * Rule: Fold message must contain a Quit parameter
	 */
	@Test
	public void foldWithQuitPassesValidation() {
		ActionMessage oot = new ActionMessage(Action.FOLD, null);
		oot.addParameter("Quit", true);
		assertTrue(oot.isValid());
	}
	
	/**
	 * Verifies that a Bet message with no parameters is invalid
	 * Rule: Bet must contain two parameters, "Amount" and "all-in"
	 */
	@Test
	public void betWithNoParametersFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.BET, null);
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a Bet message with irrelevant parameters is invalid
	 * Rule: Bet must contain two parameters, "Amount" and "all-in"
	 */
	@Test
	public void betWithBadParametersFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.BET, null);
		oot.addParameter("Ooga", "Chaka");
		oot.addParameter("Hooked", "On A Feeling");
		assertFalse(oot.isValid());
		
		oot = new ActionMessage(Action.BET, null);
		oot.addParameter("Amount", 500);
		oot.addParameter("Hooked", "On A Feeling");
		assertFalse(oot.isValid());
		
		oot = new ActionMessage(Action.BET, null);
		oot.addParameter("All-in", true);
		oot.addParameter("Hooked", "On A Feeling");
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a Bet message with an invalid All-in parameter fails validation
	 * Rule: All-in must be a boolean
	 */
	@Test
	public void betWithInvalidAllInFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.BET, null);
		oot.addParameter("Amount", 500);
		oot.addParameter("All-in", "yes");
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a Bet message with an invalid Amount parameter fails validation
	 * Rule: Amount must be numeric
	 */
	@Test
	public void betWithInvalidAmountFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.BET, null);
		oot.addParameter("Amount", "500");
		oot.addParameter("All-in", true);
		assertFalse(oot.isValid());
	}
	
	
	/**
	 * Verifies that a Bet message with no All-in parameter fails validation
	 * Rule: Bet must contain two parameters, "Amount" and "all-in"
	 */
	@Test
	public void betWithOnlyAmountFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.BET, null);
		oot.addParameter("Amount", 500);
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a Bet message with no Amount parameter fails validation
	 * Rule: Bet must contain two parameters, "Amount" and "all-in"
	 */
	@Test
	public void betWithOnlyAllInFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.BET, null);
		oot.addParameter("All-in", true);
		assertFalse(oot.isValid());
	}
	
	/**
	 * Verifies that a Bet message with both an Amount and an All-in passes validation
	 * Rule: Bet must contain two parameters, "Amount" and "all-in"
	 */
	@Test
	public void betWithCorrectParamsPassesValidation() {
		ActionMessage oot = new ActionMessage(Action.BET, null);
		oot.addParameter("Amount", 500);
		oot.addParameter("All-in", true);
		assertTrue(oot.isValid());
	}
	
	/**
	 * Verifies that a Quit message with no parameters passes validation
	 * Rule: Quit message must contain no parameters
	 */
	@Test
	public void QuitWithNoParamsPassesValidation() {
		ActionMessage oot = new ActionMessage(Action.QUIT, null);
		assertTrue(oot.isValid());
	}
	
	/**
	 * Verifies that extra parameters make a Quit message invalid
	 * Rule: Quit message cannot contain extra parameters
	 */
	@Test
	public void QuitWithParamsFailsValidation() {
		ActionMessage oot = new ActionMessage(Action.QUIT, null);
		oot.addParameter("Quit", true);
		assertFalse(oot.isValid());
	}

}
