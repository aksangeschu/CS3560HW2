/** 
 * Interface class for Users and UserGroups, requiring a getName() function, and an accept() function, taking in an AnalysisVisitor.
 * The AnalysisVisitor is required for implementing the Visitor pattern among Users and UserGroups, 
 * and getting refreshed, recursive updates on the statistics.
*/
public interface UserInterface {
    
    /** Getter function for the String name of a User or UserGroup */
    String getName();

    /** 
     * Visitor function that is used recursively starting from the root, 
	 * in order to access the total users, total user groups, total messages,
	 * and positive message percentage.
	 */
    void accept(AnalysisVisitor visitor);
}
