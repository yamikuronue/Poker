package pokerServer;

import java.util.HashMap;

import pokerServer.interfaces.Client;

/**
 * Instances of the class “Message” represent a message coming from or going to a client. 
 * @author bgreen
 */
public abstract class Message {
	Client clientInvolved;
	HashMap<String, Object> parameters;

	public Message(Client clientInvolved) {
		this.clientInvolved = clientInvolved;
		this.parameters = new HashMap<String, Object>();
	}
	
	
	/**
	 * Add a parameter to the message state. This is safe and will not overwrite existing parameters
	 * @param name The name of the parameter to add
	 * @param value The value to set
	 * @return True if it succeeded, false if not. It will return false if the parameter already existed.
	 */
	public boolean addParameter(String name, Object value) {
		if (parameters.containsKey(name)) return false;
		parameters.put(name, value);
		return true;
	}
	
	/**
	 * Remove a parameter from the message
	 * @param name The parameter to remove
	 * @return True for a success. 
	 */
	public boolean removeParameter(String name) {
		parameters.remove(name);
		return true;
	}
	
	/**
	 * Set a parameter to a value, whether it previously existed or not. 
	 * @param name The name of the parameter to set
	 * @param value The value to set
	 * @return True if it succeeded, false if not
	 */
	public boolean setParameter(String name, Object value) {
		if (parameters.containsKey(name)) parameters.replace(name, value);
		else parameters.put(name, value);
		return true;
	}
	
	/**
	 * Get a parameter
	 * @param name The name of the parameter to get
	 * @return True if it succeeded, false if not
	 */
	public Object getParameter(String name) {
		if (parameters.containsKey(name)) return parameters.get(name);
		throw new IllegalArgumentException("Invalid parameter");
	}
	
	/**
	 * Computes if the parameters given to the message are complete and valid according to the spec
	 * @return True if so, false if it is not valid. 
	 */
	public boolean isValid() {
		return true;
	}
}
