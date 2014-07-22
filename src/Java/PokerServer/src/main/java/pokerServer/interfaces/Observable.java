package pokerServer.interfaces;

/**
 * This represents something that can be observed.
 * @author bgreen
 *
 */
public interface Observable {
	/**
	 * Method to add an observer
	 * @param observer The observer to add to the observation list
	 * @return True if the add was successful, false if not.
	 */
	public boolean addObserver(Observer observer);
	
	/**
	 * Method to remove an observer
	 * @param observer The observer to remove from the observation list
	 * @return True if the removal was successful, false if not.
	 */
	public boolean removeObserver(Observer observer);
}
