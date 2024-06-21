/**
 * AnalysisVisitor interface for the Visitor pattern.
 * Defines methods for visiting Users and UserGroups.
 */
public interface Visitor {
    void visit(User user);
    void visit(UserGroup userGroup);
}
