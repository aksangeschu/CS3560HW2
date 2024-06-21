import java.util.List;

/**
 * The AnalysisVisitor class is an object that collects the statistical counts for the analysis features of the
 * admin panel. It also acts as the visitor, that recursively visits each group and user starting from the root,
 * thus being the implementation for the Visitor pattern.
 */
public class AnalysisVisitor implements Visitor {
    private int userCount;
    private int userGroupCount;
    private int newsFeedCount;
    private int positiveCount;

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
    }

    /** Returns User count */
    public int getUserCount() {
        return userCount;
    }

    /** Returns UserGroup count */
    public int getUserGroupCount() {
        return userGroupCount;
    }

    /** Returns News Feed count */
    public int getNewsFeedCount() {
        return newsFeedCount;
    }

    /** Adds 1 to user count when visited */
    public void visit(User user) {
        userCount++;
        visitNewsFeed(user.getTweets());
    }

    /** Adds 1 to group count when visited */
    public void visit(UserGroup userGroup) {
        userGroupCount++;
        for (UserInterface member : userGroup.getMembers()) {
            member.accept(this);
        }
    }

    /** Returns percentage of positive messages over total messages */
    public float getPositivePercentage() {
        if (newsFeedCount == 0) {
            return 0;
        }

        return ((float) positiveCount / newsFeedCount) * 100;
    }

    /** Adds the number of messages for a user to the count, and gets the number of positive messages
     * within those.
     */
    public void visitNewsFeed(List<String> newsFeed) {
        newsFeedCount += newsFeed.size();
        for (String post : newsFeed) {
            for (String positiveWord : POSITIVE_WORDS) {
                if (post.toLowerCase().contains(positiveWord.toLowerCase())) {
                    positiveCount++;
                    break;
                }
            }
        }
    }
}
