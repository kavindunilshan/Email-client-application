// Factory
public class RecipientFactory {
    public static Person createRecipient(String relation, String details []) {
        if (relation.equals("Official")) {
            return new OfficialRecipient(details);

        } else if (relation.equals("Official_friend")) {
            return new OfficialFriendRecipient(details);

        } else if (relation.equals("Friend")) {
            return new FriendRecipient(details);
        } else {
            throw new IllegalStateException();
        }
    }

}