import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The AdminControlPanel class is the main part of the UI and frontend, as well as the connection for using the backend classes
 * for users, groups, and the visitor. The admin panel is implemented with a Singleton Pattern, since it only needs to be 
 * instantiated once in the program. AdminControlPanel includes three more panels that include the User tree, buttons for adding
 * users, groups and opening a user view, as well as buttons for showing the analysis.
 */
public class AdminControlPanel extends JFrame {
    private static AdminControlPanel instance; // Singleton instance

    private JTree userTree; // Tree to display users and groups
    private UserTreeModel treeModel; // Model for the user tree
    private UserTreeNode rootNode; // Root node of the user tree

    private JTextField userIdField;
    private JTextField groupIdField; 

    /**
     * Private constructor to implement Singleton pattern.
     */
    private AdminControlPanel() {
        setTitle("Admin Control Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeComponents();
    }

    /**
     * Gets the single instance of AdminControlPanel.
     *
     * @return single instance of AdminControlPanel
     */
    public static AdminControlPanel getInstance() {
        if (instance == null) {
            instance = new AdminControlPanel();
        }
        return instance;
    }
    
    //Initializes UI components.
    private void initializeComponents() {
        // Initialize root group and tree model
        UserGroup rootGroup = new UserGroup("Root");
        rootNode = new UserTreeNode(rootGroup);
        treeModel = new UserTreeModel(rootNode);
        userTree = new JTree(treeModel);
        JScrollPane treeView = new JScrollPane(userTree);

        // Add mouse listener to open user view on double-click
        userTree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    openUserView();
                }
            }
        });

        JPanel controlPanel = new JPanel(new GridLayout(3, 1));

        // Panel for adding users
        JPanel userPanel = new JPanel(new FlowLayout());
        userIdField = new JTextField(10);
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        userPanel.add(new JLabel("User Name:"));
        userPanel.add(userIdField);
        userPanel.add(addUserButton);

        // Panel for adding groups
        JPanel groupPanel = new JPanel(new FlowLayout());
        groupIdField = new JTextField(10);
        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGroup();
            }
        });
        groupPanel.add(new JLabel("Group Name:"));
        groupPanel.add(groupIdField);
        groupPanel.add(addGroupButton);

        // Panel for showing analysis
        JPanel analysisPanel = new JPanel(new FlowLayout());
        JButton userCountButton = new JButton("Total Users");
        userCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTotalUsers();
            }
        });
        JButton groupCountButton = new JButton("Total Groups");
        groupCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTotalGroups();
            }
        });
        JButton tweetCountButton = new JButton("Total Tweets");
        tweetCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTotalTweets();
            }
        });
        JButton positivePercentageButton = new JButton("Positive Tweets");
        positivePercentageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPositiveTweetPercentage();
            }
        });

        // Add buttons to analysis panel
        analysisPanel.add(userCountButton);
        analysisPanel.add(groupCountButton);
        analysisPanel.add(tweetCountButton);
        analysisPanel.add(positivePercentageButton);

        // Add sub-panels to control panel
        controlPanel.add(userPanel);
        controlPanel.add(groupPanel);
        controlPanel.add(analysisPanel);

        // Add main components to the frame
        add(treeView, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Adds a new user to the selected group or root if no group is selected.
     */
    private void addUser() {
        String userName = userIdField.getText();
        if (!userName.isEmpty()) {
            User user = new User(userName);
            UserTreeNode selectedNode = (UserTreeNode) userTree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                UserInterface selectedObject = (UserInterface) selectedNode.getUserObject();
                if (selectedObject instanceof UserGroup) {
                    UserGroup selectedGroup = (UserGroup) selectedObject;
                    treeModel.addUser(user, selectedGroup);
                    UserTreeNode userNode = new UserTreeNode(user);
                    selectedNode.add(userNode);
                    treeModel.reload(selectedNode);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a group to add the user to.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                treeModel.addUser(user, (UserGroup) rootNode.getUserObject());
                UserTreeNode userNode = new UserTreeNode(user);
                rootNode.add(userNode);
                treeModel.reload(rootNode);
            }
            userIdField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "User Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new group to the selected group or root if no group is selected.
     */
    private void addGroup() {
        String groupName = groupIdField.getText();
        if (!groupName.isEmpty()) {
            UserGroup group = new UserGroup(groupName);
            UserTreeNode selectedNode = (UserTreeNode) userTree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                UserInterface selectedObject = (UserInterface) selectedNode.getUserObject();
                if (selectedObject instanceof UserGroup) {
                    UserGroup selectedGroup = (UserGroup) selectedObject;
                    treeModel.addUserGroup(group, selectedGroup);
                    UserTreeNode groupNode = new UserTreeNode(group);
                    selectedNode.add(groupNode);
                    treeModel.reload(selectedNode);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a group to add the new group to.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                treeModel.addUserGroup(group, (UserGroup) rootNode.getUserObject());
                UserTreeNode groupNode = new UserTreeNode(group);
                rootNode.add(groupNode);
                treeModel.reload(rootNode);
            }
            groupIdField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Group Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the user view for the selected user.
     */
    private void openUserView() {
        UserTreeNode selectedNode = (UserTreeNode) userTree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getUserObject() instanceof User) {
            User user = (User) selectedNode.getUserObject();
            new UserView(user, treeModel);
        }
    }

    /**
     * Shows the total number of users in the system.
     */
    private void showTotalUsers() {
        AnalysisVisitor visitor = new AnalysisVisitor();
        UserGroup rootGroup = (UserGroup) rootNode.getUserObject();
        rootGroup.accept(visitor);
        JOptionPane.showMessageDialog(this, "Total Users: " + visitor.getUserCount(), "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the total number of groups in the system.
     */
    private void showTotalGroups() {
        AnalysisVisitor visitor = new AnalysisVisitor();
        UserGroup rootGroup = (UserGroup) rootNode.getUserObject();
        rootGroup.accept(visitor);
        JOptionPane.showMessageDialog(this, "Total Groups: " + visitor.getUserGroupCount(), "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the total number of tweets in the system.
     */
    private void showTotalTweets() {
        AnalysisVisitor visitor = new AnalysisVisitor();
        UserGroup rootGroup = (UserGroup) rootNode.getUserObject();
        rootGroup.accept(visitor);
        JOptionPane.showMessageDialog(this, "Total Tweets: " + visitor.getNewsFeedCount(), "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the percentage of positive tweets in the system.
     */
    private void showPositiveTweetPercentage() {
        AnalysisVisitor visitor = new AnalysisVisitor();
        UserGroup rootGroup = (UserGroup) rootNode.getUserObject();
        rootGroup.accept(visitor);
        JOptionPane.showMessageDialog(this, "Positive Tweets: " + visitor.getPositivePercentage() + "%", "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
