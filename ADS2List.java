import java.util.List;

public class ADS2List {

    private List<String> userNames;

    public ADS2List(List<String> userNames) {
        this.userNames = userNames;
    }

    //Generated getters and setters
    public List<String> getUserNames() {
        //TODO Note: change index of the name in the list is UserID - 1 not ZERO index! (offset by 1)
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    //Return list of names for user IDs
    public String[] retrieveNames(List<Integer> userIDs) {
        String[] names = new String[userIDs.size()];
        int index = 0;

        for (int userID: userIDs) {
            //Minus 1 from id when accessing userNames list
            String name = userNames.get(userID - 1);
            names[index] = name;
            index++;
        }

        return names;
    }
}







//TODO Notes:
/*
 * NameList data structure:
 *   Map? Implemented as a BST. Better for bigger data
 *   Vector? Implemented as an Array. Better for smaller data
 * SocialNetworkData data structure -> Graph
 * */


//Useful links:
//https://stackabuse.com/graphs-in-java-representing-graphs-in-code/ -> Read conclusion



