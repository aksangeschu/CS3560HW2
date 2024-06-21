/**
 * Interface class for Users and UserGroups, requiring a getName() function, and an accept() function, taking in an AnalysisVisitor.
 * The AnalysisVisitor is required for implementing the Visitor pattern among Users and UserGroups,
 * and getting refreshed, recursive updates on the statistics.
 */
public interface UserInterface {
    String getName();
    void accept(Visitor visitor);
}
