import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User class that structures an Observer pattern amongst other Users.
 * Users can follow (observe) another user, and be followed by a User or that same User back (observed),
 * so Users are both Observers and Observable within the design pattern.
 */
public class User implements UserInterface {
    private String id;
    private String name;
    private List<User> followers;
    private List<User> followings;
    private List<String> newsFeed;

    /**
     * Generates a unique ID and sets the user's name. 
     * */
    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public void accept(AnalysisVisitor visitor) {
        visitor.visit(this);
    }

    // Method for following users
    public void follow(User user) {
        if (!followings.contains(user)) {
            followings.add(user);
            user.addFollower(this);
        }
    }

    /** 
     * Adds a follower to the user. 
     */
    private void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    /** 
     * Posts a new tweet. 
     */
    public void postTweet(String message) {
        newsFeed.add(message);
        notifyFollowers(message);
    }

    /**
     * Notifies followers about a new tweet.
     */
    private void notifyFollowers(String message) {
        for (User follower : followers) {
            follower.update(message);
        }
    }

    /**
     * Updates the news feed with a new tweet.
    */
    private void update(String tweet) {
        newsFeed.add(tweet);
    }

    /**
     * Gets the news feed of the user.
     */
    public List<String> getTweets() {
        return newsFeed;
    }

    /**
     *  Gets Id for users
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the list of users this user is following.
     */
    public List<User> getFollowings() {
        return followings;
    }
}
