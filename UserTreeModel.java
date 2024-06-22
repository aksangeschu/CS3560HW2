import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class UserTreeModel extends DefaultTreeModel {

    public UserTreeModel(TreeNode root) {
        super(root);
    }

    public void addUser(User user, UserGroup group) {
        group.add(user);
    }

    public void addUserGroup(UserGroup newGroup, UserGroup parentGroup) {
        parentGroup.add(newGroup);
    }
}
