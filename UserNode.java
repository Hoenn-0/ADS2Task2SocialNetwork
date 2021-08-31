import java.util.ArrayList;
import java.util.List;

public class UserNode implements Comparable<UserNode> {
    private int nodeIndex;
    private List<Edge> edges;
    private double distanceFromSource = Double.MAX_VALUE;
    private List<Integer> blockedFriends; //TODO Should be similar to GetFriends list**

    public UserNode(int nodeIndex) {
        this.nodeIndex = nodeIndex;
        this.edges = new ArrayList<>();
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public double retrieveEdgeWeight(UserNode destination) {
        for (Edge edge: edges) {
            if (edge.getDestination().equals(destination)) {
                return edge.getWeight();
            }
        }
        return 0.0;
    }

    public double getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(double distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    @Override
    public int compareTo(UserNode otherNode) {
        return Double.compare(this.distanceFromSource, otherNode.getDistanceFromSource());
        //TODO Note: Below works too (alternative)
//        if (this.distanceFromSource > otherNode.getDistanceFromSource()) {
//            return 1;
//        } else {
//            return -1;
//        }
    }
}
