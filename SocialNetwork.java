import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * You can also develop help functions and new classes for your system. You
 * can change the skeleton code if you need but you do not allow to remove the
 * methods provided in this class.
 */
public class SocialNetwork {
    private ADS2Graph userNetwork;//You may use different container for the social network data
    private ADS2List userNames;//You may use different container for the user name list

    public SocialNetwork(){

    }

//TODO research which is better SCANNER VS BUFFEREDREADER**
// Check week 8 - Hallam Local Map with Grid- help visualize**
    /**
     * Loading social network data from files.
     * The dataset contains two separate files for user names (NameList.csv) and
     * the network distributions (SocialNetworkData.csv).
     * Use file I/O functions to load the data.You need to choose suitable data
     * structure and algorithms for an effective loading function
     */
    public void Load(){
        this.userNames = new ADS2List(readNameListCSV());
        this.userNetwork = readSocialNetworkCSV();
    }

    private static List<String> readNameListCSV() {
        List<String> userNameList = new ArrayList<>();
        Path pathToNamesFile = Paths.get("SocialNetwork_ADS2/NameList.csv");

        try(BufferedReader br = Files.newBufferedReader(pathToNamesFile)) {
            String name = br.readLine();

            while(name != null) {
                //Create a new object and add to userNameList
                userNameList.add(name);
                name = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userNameList;
    }

    private ADS2Graph readSocialNetworkCSV() {
        ADS2Graph socialNetworkGraph = new ADS2Graph();
        Path pathToNamesFile = Paths.get("SocialNetwork_ADS2/SocialNetworkData.csv");

        try(BufferedReader br = Files.newBufferedReader(pathToNamesFile)) {
            String snData = br.readLine();

            while(snData != null) {
                //Create a new object and add to userNameList
                String[] data = snData.split(",");

                //Build a ADS2Graph object
                int userAIndex = Integer.parseInt(data[0]);
                int userBIndex = Integer.parseInt(data[1]);
                double weight = Double.parseDouble(data[2]); //Distance from each user

//                System.out.println("\n\nUserAIndex: " + userAIndex);

                //Give userNodeA an index and add to HashMap
                socialNetworkGraph.connectUserNodes(userAIndex, userBIndex, weight);

                snData = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socialNetworkGraph;
    }

    /**
     * Locating a user from the network
     * @param fullName users full name as a String
     * @return return the ID based on the user list. return -1 if the user do not exist
     * based on your algorithm, you may also need to locate the reference
     * of the node from the graph.
     */
    public int FindUserID(String fullName){
        if(userNames.getUserNames().contains(fullName)) {
            //Add 1 to index to get the correct userID because when userNames was created, first user was at index 0.
            return userNames.getUserNames().indexOf(fullName) + 1;
        } else {
            return -1;
        }
    }

    /**
     * Listing ALL the friends belongs to the user
     * You need to retrieval all the directly linked nodes and return their full names.
     * Current Skeleton only have some dummy data. You need to replace the output
     * by using your own algorithms.
     * @param currentUserName use FindUserID or other help functions to locate
     * the user in the graph first.
     * @return You need to return all the user names in a String Array directly
     * linked to the users node.
     */
    public String[] GetMyFriends(String currentUserName){
        System.out.println("\nStart of GetMyFriends:" + java.time.LocalDateTime.now());
        List<Integer> friendIDs = userNetwork.getFriendIDs(FindUserID(currentUserName));

        return userNames.retrieveNames(friendIDs);
    }

    public void addFriend(String currentUserName, int friendID) {
        List<Integer> friendIDs = userNetwork.getFriendIDs(FindUserID(currentUserName));

        if (!friendIDs.contains(friendID)) {
            //TODO NOTE: After running the shortest path, all users must have a distance from source node which is the current user
            double weight = userNetwork.getUserNode(friendID).getDistanceFromSource();


            userNetwork.addFriend(FindUserID(currentUserName), friendID, weight);
        }
    }

    private List<Integer> getShortestPath(int userID) {
        if (userNetwork.getVisited().isEmpty()) {
            return userNetwork.shortestPath(userID);
        } else {
            return userNetwork.getVisited();
        }
    }

    /**
     * Listing the top 10 recommended friends for the user
     * In the task, you need to calculate the shortest distance between the
     * current user and all other non-directly linked users. Pick up the top three
     * closest candidates and return their full names.
     * Use some help functions for sorting and shortest distance algorithms
     * @param currentUserName use FindUserID or other help functions to locate
     * the user in the graph first.
     * @return You need to return all the user names in a String Array containing
     * top 3 closest candidates.
     */
    public String[] GetRecommended (String currentUserName){
        //Using Dijkstra's algorithm to calculate the shortest path in weighted graphs
        System.out.println("\nStart of GetRecommended:" + java.time.LocalDateTime.now());

        int userID = FindUserID(currentUserName);

        //Remove UserID and friend IDs from shortestPath list
        List<Integer> shortestPath = getShortestPath(userID);
        List<Integer> userAndFriendsIDs = userNetwork.getFriendIDs(userID);
        userAndFriendsIDs.add(userID);

        //Remove friends and user from shortest path to get IDs of non-friends
        List<Integer> union = new ArrayList<>(shortestPath);
        union.addAll(userAndFriendsIDs);

        List<Integer> intersection = new ArrayList<>(shortestPath);
        intersection.retainAll(userAndFriendsIDs);

        union.removeAll(intersection);

        List<Integer> closestNonFriends = union.stream().limit(10).collect(Collectors.toList()); //limit 10

        //System.out.println("closestNonFriends: " + closestNonFriends);
        System.out.println("\nEnd of GetRecommended:" + java.time.LocalDateTime.now());

        return userNames.retrieveNames(closestNonFriends);
    }
}
