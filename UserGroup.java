import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * UserGroup class that structures the Composite design pattern, by containing lists of
 * both Users (leaves), and UserGroups (composites), thus enabling the creation of the recursive,
 * tree-like structure of the Composite pattern.
 */
public class UserGroup implements UserInterface {
    private String id;
    private String name;
    private List<UserInterface> members;

    /**
     * Constructor for UserGroup.
     * Generates a unique ID and sets the group's name.
     *
     * @param name the name of the group
     */
    public UserGroup(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.members = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public void accept(AnalysisVisitor visitor) {
        visitor.visit(this);
    }

    public void add(UserInterface userInterface) {
        members.add(userInterface);
    }

    public void remove(UserInterface userInterface) {
        members.remove(userInterface);
    }

    public List<UserInterface> getMembers() {
        return members;
    }
}
