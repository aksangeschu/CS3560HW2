import javax.swing.tree.DefaultMutableTreeNode;

/**
 * UserTreeNode is used for the UserTree in the admin panel. It is the class that constructs the tree itself.
 * A UserTreeNode can accept a User or a UserGroup. If the object accepted is a User, it represents a leaf node of the tree.
 * This logic helps in constructing and displaying the tree structure in the UI.
 */
public class UserTreeNode extends DefaultMutableTreeNode {

    /** Constructs a UserTreeNode with the specified user object. */
    public UserTreeNode(UserInterface userObject) {
        super(userObject);
    }

    /** Returns the name of the user object stored in this node. */
    @Override
    public String toString() {
        UserInterface userObject = (UserInterface) getUserObject();
        return userObject.getName();
    }
}
