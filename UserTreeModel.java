import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * The UserTreeModel takes in a root TreeNode, in this project a UserTreeNode.
 * The Model helps construct the entire user tree, where we initialize the Model and tree from
 * our root in the admin panel.
 */
public class UserTreeModel extends DefaultTreeModel {
    public UserTreeModel(TreeNode root) {
        super(root);
    }

    /**
     * Adds a new user to the specified group.
     *
     * @param user The user to add.
     * @param group The group to which the user should be added.
     */
    public void addUser(User user, UserGroup group) {
        UserTreeNode userNode = new UserTreeNode(user);
        UserTreeNode groupNode = findNode(group);
        if (groupNode != null) {
            insertNodeInto(userNode, groupNode, groupNode.getChildCount());
        }
    }

    /**
     * Adds a new user group to the specified parent group.
     *
     * @param newGroup The new user group to add.
     * @param parentGroup The parent group to which the new group should be added.
     */
    public void addUserGroup(UserGroup newGroup, UserGroup parentGroup) {
        UserTreeNode newGroupNode = new UserTreeNode(newGroup);
        UserTreeNode parentGroupNode = findNode(parentGroup);
        if (parentGroupNode != null) {
            insertNodeInto(newGroupNode, parentGroupNode, parentGroupNode.getChildCount());
        }
    }

    /**
     * Finds the tree node corresponding to the given user or user group.
     *
     * @param userInterface The user or user group to find.
     * @return The tree node corresponding to the given user or user group, or null if not found.
     */
    private UserTreeNode findNode(UserInterface userInterface) {
        return findNode((UserTreeNode) getRoot(), userInterface);
    }

    private UserTreeNode findNode(UserTreeNode root, UserInterface userInterface) {
        if (root.getUserObject().equals(userInterface)) {
            return root;
        }
        for (int i = 0; i < root.getChildCount(); i++) {
            UserTreeNode child = (UserTreeNode) root.getChildAt(i);
            UserTreeNode result = findNode(child, userInterface);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
