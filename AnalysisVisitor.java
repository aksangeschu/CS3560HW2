import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The AnalysisVisitor class is an object that collects the statistical counts for the analysis features of the
 * admin panel. It also acts as the visitor, that recursively visits each group and user starting from the root,
 * thus being the implementation for the Visitor pattern.
 */
public class AnalysisVisitor {
    private int userCount;
    private int userGroupCount;
    private int newsFeedCount;
    private int positiveCount;
    private User lastUpdatedUser;
    private long lastUpdateTime;
    private Set<String> uniqueTweets;

    private final String[] POSITIVE_WORDS = {"good", "nice", "awesome", "happy", "great", "enjoy"};

    /** Constructor sets the initial counts all at 0 */
    public AnalysisVisitor() {
        setCounts();
    }

    public void setCounts() {
        userCount = 0;
        userGroupCount = 0;
        newsFeedCount = 0;
        positiveCount = 0;
        lastUpdatedUser = null;
        lastUpdateTime = 0;
        uniqueTweets = new HashSet<>();
    }

    public int getUserCount() {
        return userCount;
    }

    public int getUserGroupCount() {
        return userGroupCount;
    }

    public int getNewsFeedCount() {
        return uniqueTweets.size();
    }

    public User getLastUpdatedUser() {
        return lastUpdatedUser;
    }

    public void visit(User user) {
        userCount++;
        visitNewsFeed(user.getTweets());
        if (user.getLastUpdateTime() > lastUpdateTime) {
            lastUpdateTime = user.getLastUpdateTime();
            lastUpdatedUser = user;
        }
    }

    public void visit(UserGroup userGroup) {
        userGroupCount++;
        for (UserInterface member : userGroup.getMembers()) {
            member.accept(this);
        }
    }

    public float getPositivePercentage() {
        if (uniqueTweets.size() == 0) {
            return 0;
        }

        return ((float) positiveCount / uniqueTweets.size()) * 100;
    }

    private void visitNewsFeed(List<String> newsFeed) {
        for (String post : newsFeed) {
            if (uniqueTweets.add(post)) {
                newsFeedCount++;
                for (String positiveWord : POSITIVE_WORDS) {
                    if (post.toLowerCase().contains(positiveWord.toLowerCase())) {
                        positiveCount++;
                        break;
                    }
                }
            }
        }
    }
}
