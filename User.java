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
    private long creationTime;
    private long lastUpdateTime;

    /**
     * Generates a unique ID and sets the user's name.
     */
    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.creationTime = System.currentTimeMillis();
        this.lastUpdateTime = this.creationTime;
    }

    @Override
    public String getName() {
        return name;
    }

    public void accept(AnalysisVisitor visitor) {
        visitor.visit(this);
    }

    public void follow(User user) {
        if (!followings.contains(user)) {
            followings.add(user);
            user.addFollower(this);
        }
    }

    private void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    public void postTweet(String message) {
        newsFeed.add(message);
        notifyFollowers(message);
        updateLastUpdateTime();
    }

    private void notifyFollowers(String message) {
        for (User follower : followers) {
            follower.update(message);
            follower.updateLastUpdateTime();
        }
    }

    private void update(String tweet) {
        newsFeed.add(tweet);
        updateLastUpdateTime();
    }

    private void updateLastUpdateTime() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public List<String> getTweets() {
        return newsFeed;
    }

    public String getId() {
        return id;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public List<User> getFollowings() {
        return followings;
    }
}
