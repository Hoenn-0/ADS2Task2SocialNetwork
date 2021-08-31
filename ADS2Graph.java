import java.util.*;

public class ADS2Graph {

    //HashMap to represent the nodes that are friends with the user
    //Note: Key is the userID. Matches index for user name in NameList. Check connectUserNodes method below
    private HashMap<Integer, ArrayList<UserNode>> adjacentNodesMap; //Size should be all users
    private HashMap<Integer, UserNode> userNodesMap; //Size should be all users. Contains all userNodes

    //Shortest path
    private List<Integer> visited = new ArrayList<>();

    public List<Integer> getVisited() {
        return visited;
    }

    //TODO NOTE: Google search adjacency list using HashMap **
    //TODO NOTE: Adjacency list was used over adjacency matrix as it's easier to implement/read... **

    public ADS2Graph() {
        adjacentNodesMap = new HashMap<>();
        userNodesMap = new HashMap<>();
    }

    //Generated getters and setters below
    public HashMap<Integer, ArrayList<UserNode>> getAdjacentNodesMap() {
        return adjacentNodesMap;
    }

    public void setAdjacentNodesMap(HashMap<Integer, ArrayList<UserNode>> adjacentNodesMap) {
        this.adjacentNodesMap = adjacentNodesMap;
    }

    public HashMap<Integer, UserNode> getUserNodesMap() {
        return userNodesMap;
    }

    public void setUserNodesMap(HashMap<Integer, UserNode> userNodesMap) {
        this.userNodesMap = userNodesMap;
    }

    //Retrieve user node if it exists else create a new UserNode
    public UserNode createOrRetrieveUserNode(int userNodeID) {
        if (userNodesMap.containsKey(userNodeID)) {
            return userNodesMap.get(userNodeID);
        } else {
            return new UserNode(userNodeID);
        }
    }

    public void connectUserNodes(int userAID, int userBID, double weight) {
        UserNode userNodeA = createOrRetrieveUserNode(userAID);
        UserNode userNodeB = createOrRetrieveUserNode(userBID);
        Edge edge = new Edge(userNodeA, userNodeB, weight);

        //Add userA to map
        userNodesMap.putIfAbsent(userAID, userNodeA);

        if (adjacentNodesMap.containsKey(userAID)) {
            //Add to userA's neighbour nodes
            adjacentNodesMap.get(userAID).add(userNodeB);
            userNodeA.addEdge(edge);
        } else {
            //Add new node to HashMap and add userB as Friend
            adjacentNodesMap.putIfAbsent(userAID, new ArrayList<>());
            adjacentNodesMap.get(userAID).add(userNodeB);
            userNodeA.addEdge(edge);
        }
    }

    public void addFriend(int userAID, int userBID, double weight) {
        //TODO NOTE: All users and their ID should already be in the network
        //Users can't add friends if user hasn't been instantiated as a UserNode

        UserNode userNodeA = createOrRetrieveUserNode(userAID);
        UserNode userNodeB = createOrRetrieveUserNode(userBID);
        Edge edgeA = new Edge(userNodeA, userNodeB, weight);
        Edge edgeB = new Edge(userNodeB, userNodeA, weight);

        //Create both connections
        adjacentNodesMap.get(userAID).add(userNodeB);
        userNodeA.addEdge(edgeA);

        //Create other connection
        adjacentNodesMap.get(userBID).add(userNodeA);
        userNodeB.addEdge(edgeB);
    }
//Todo <-----------? arraylist search algorithm replace

    public List<Integer> getFriendIDs(int userID) {
//
//        System.out.println("The node " + userID + " is connected to: ");

        List<Integer> friendIDs = new ArrayList<>();

        if (adjacentNodesMap.containsKey(userID)) {
            for (UserNode userB: adjacentNodesMap.get(userID)) {
              //  System.out.println(userB.getNodeIndex());

                friendIDs.add(userB.getNodeIndex());
            }
        }

        return friendIDs;
    }

    public UserNode getUserNode(int userID) {
        if (userNodesMap.containsKey(userID)) {
            return userNodesMap.get(userID);
        } else {
            return new UserNode(0); //TODO Throw an exception?? debug
        }
    }

    //TODO Add documentation to everything**
    //Calculates shortest path to all user nodes using Dijkstra's Algorithm
    public List<Integer> shortestPath(int userID) {
        System.out.println("\nStart of shortestPath:" + java.time.LocalDateTime.now());

        PriorityQueue<UserNode> pQueue = new PriorityQueue<>();
        UserNode sourceNode = getUserNode(userID);

        //Add source node to queue and set distance from source to 0
        pQueue.add(sourceNode);
        sourceNode.setDistanceFromSource(0.0);
        visited.add(sourceNode.getNodeIndex());

        while(!pQueue.isEmpty()) {
            UserNode currentUserNode = pQueue.poll();

            for (Edge edge: currentUserNode.getEdges()) {
                UserNode neighbourNode = edge.getDestination();

                //Check node hasn't been visited yet
                if (!visited.contains(neighbourNode.getNodeIndex())) {
                    //Calculate distance from source node to neighbour node
                    double newDistance = currentUserNode.getDistanceFromSource() + edge.getWeight();

                    if (newDistance < neighbourNode.getDistanceFromSource()) {
                        pQueue.remove(neighbourNode);
                        neighbourNode.setDistanceFromSource(newDistance);
                        pQueue.add(neighbourNode);
                    }
                }
            }

            //Add this node to visited nodes
            visited.add(currentUserNode.getNodeIndex());
        }

       // System.out.println("\n\nVisited: " + visited);

        //Shortest path to connect all nodes in order
        System.out.println("End of shortestPath:" + java.time.LocalDateTime.now());

        return visited;
    }


    //TODO For printing the graph during Test/debug** (REMOVE)
    public void printGraph() {
        for (int userA: adjacentNodesMap.keySet()) {

       //     System.out.println("The node " + userA + " is connected to: ");

            if (adjacentNodesMap.get(userA) != null) {
                //Print nodes connected to userA
                for (UserNode userB: adjacentNodesMap.get(userA)) {
                    System.out.println(userB.getNodeIndex());
                }
            } else {
                System.out.println("NONE");
            }
        }
    }

}
