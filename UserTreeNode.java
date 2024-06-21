import javax.swing.tree.DefaultMutableTreeNode;

/**
 * UserTreeNode is used for the UserTree in the admin panel. It is the class that constructs the tree itself,
 * but within a node can be accepted a User, or a UserGroup. If the object accepted is a User, it is a leaf node of the tree,
 * where this logic helps construct the tree's display.
 */
public class UserTreeNode extends DefaultMutableTreeNode {
    public UserTreeNode(UserInterface userInterface) {
        super(userInterface);
    }

    @Override
    public boolean isLeaf() {
        return getUserObject() instanceof User;
    }

    @Override
    public String toString() {
        return ((UserInterface) getUserObject()).getName();
    }
}
