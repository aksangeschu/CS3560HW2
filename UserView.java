import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The User View includes a frame and panel that opens from the admin panel when selecting a user.
 * In a User View, one is able to follow other users created from the admin, post messages to their followers,
 * and get message feed from users they follow.
 */
public class UserView extends JFrame {
    private User user;
    private UserTreeModel treeModel;
    private JList<String> messageFeed;
    private DefaultListModel<String> feedModel;
    private JList<String> followingList;
    private DefaultListModel<String> followingModel;

    private JTextField followUserField;
    private JTextField tweetField;
    private JLabel creationTimeLabel;
    private JLabel lastUpdateTimeLabel;

    public UserView(User user, UserTreeModel treeModel) {
        this.user = user;
        this.treeModel = treeModel;

        setTitle("User View: " + user.getName());
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Initialize components
        initializeComponents();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Schedule feed updates
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFeed();
            }
        });
        timer.start();
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        feedModel = new DefaultListModel<>();
        messageFeed = new JList<>(feedModel);
        JScrollPane feedScrollPane = new JScrollPane(messageFeed);

        followingModel = new DefaultListModel<>();
        followingList = new JList<>(followingModel);
        JScrollPane followingScrollPane = new JScrollPane(followingList);

        // Create a panel for the top section with labels
        JPanel topPanel = new JPanel(new GridLayout(3, 2));
        JPanel followingPanel = new JPanel(new BorderLayout());
        followingPanel.add(new JLabel("Following:"), BorderLayout.NORTH);
        followingPanel.add(followingScrollPane, BorderLayout.CENTER);
        topPanel.add(followingPanel);

        JPanel tweetsPanel = new JPanel(new BorderLayout());
        tweetsPanel.add(new JLabel("Tweets:"), BorderLayout.NORTH);
        tweetsPanel.add(feedScrollPane, BorderLayout.CENTER);
        topPanel.add(tweetsPanel);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        creationTimeLabel = new JLabel("Creation Time: " + sdf.format(new Date(user.getCreationTime())));
        lastUpdateTimeLabel = new JLabel("Last Update Time: " + sdf.format(new Date(user.getLastUpdateTime())));

        topPanel.add(creationTimeLabel);
        topPanel.add(lastUpdateTimeLabel);

        JPanel controlPanel = new JPanel(new GridLayout(2, 1));

        JPanel followPanel = new JPanel(new FlowLayout());
        followUserField = new JTextField(15);
        JButton followButton = new JButton("Follow User");
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                followUser();
            }
        });
        followPanel.add(new JLabel("User to follow:"));
        followPanel.add(followUserField);
        followPanel.add(followButton);

        JPanel tweetPanel = new JPanel(new FlowLayout());
        tweetField = new JTextField(20);
        JButton tweetButton = new JButton("Post Tweet");
        tweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postTweet();
            }
        });
        tweetPanel.add(new JLabel("Tweet:"));
        tweetPanel.add(tweetField);
        tweetPanel.add(tweetButton);

        controlPanel.add(followPanel);
        controlPanel.add(tweetPanel);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);

        updateFollowingList();
        updateFeed();
    }

    private void followUser() {
        String userNameToFollow = followUserField.getText();
        if (!userNameToFollow.isEmpty()) {
            User userToFollow = findUserByName(userNameToFollow);
            if (userToFollow != null) {
                user.follow(userToFollow);
                JOptionPane.showMessageDialog(this, "Now following " + userNameToFollow, "Success", JOptionPane.INFORMATION_MESSAGE);
                updateFollowingList();
            } else {
                JOptionPane.showMessageDialog(this, "User not found: " + userNameToFollow, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "User Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        followUserField.setText("");
    }

    private User findUserByName(String userName) {
        return findUserByName((UserTreeNode) treeModel.getRoot(), userName);
    }

    private User findUserByName(UserTreeNode node, String userName) {
        if (node.getUserObject() instanceof User) {
            User user = (User) node.getUserObject();
            if (user.getName().equals(userName)) {
                return user;
            }
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            UserTreeNode child = (UserTreeNode) node.getChildAt(i);
            User result = findUserByName(child, userName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private void postTweet() {
        String tweet = tweetField.getText();
        if (!tweet.isEmpty()) {
            String formattedTweet = "Tweet by " + user.getName() + ": " + tweet;
            user.postTweet(formattedTweet);
            updateFeed();
            JOptionPane.showMessageDialog(this, "Tweet posted", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateLastUpdateTime();
        } else {
            JOptionPane.showMessageDialog(this, "Tweet cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        tweetField.setText("");
    }

    private void updateFeed() {
        feedModel.clear();
        for (String tweet : user.getTweets()) {
            feedModel.addElement(tweet);
        }
    }

    private void updateFollowingList() {
        followingModel.clear();
        for (User followedUser : user.getFollowings()) {
            followingModel.addElement(followedUser.getName());
        }
    }

    private void updateLastUpdateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lastUpdateTimeLabel.setText("Last Update Time: " + sdf.format(new Date(user.getLastUpdateTime())));
    }
}
