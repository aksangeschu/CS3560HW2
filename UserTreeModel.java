import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * UserTreeModel class represents the model for the user tree structure.
 * It extends the DefaultTreeModel to manage the addition of users and user groups.
 */
public class UserTreeModel extends DefaultTreeModel {

    /** Constructs a UserTreeModel with the specified root node. */
    public UserTreeModel(TreeNode root) {
        super(root);
    }

    /** Adds a user to the specified user group. */
    public void addUser(User user, UserGroup group) {
        group.add(user);
    }

    /** Adds a user group to the specified parent group. */
    public void addUserGroup(UserGroup newGroup, UserGroup parentGroup) {
        parentGroup.add(newGroup);
    }
}
