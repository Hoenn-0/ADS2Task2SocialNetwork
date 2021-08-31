public class Edge {
    private UserNode source;
    private UserNode destination;
    private double weight;

    public UserNode getSource() {
        return source;
    }

    public void setSource(UserNode source) {
        this.source = source;
    }

    public Edge(UserNode source, UserNode destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public UserNode getDestination() {
        return destination;
    }

    public void setDestination(UserNode destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getIndexOfNeighbour(int userNodeIndex) {
        if (this.source.getNodeIndex() == userNodeIndex) {
            return this.destination.getNodeIndex();
        } else {
            return this.source.getNodeIndex();
        }
    }

    public String toString() {
        return String.format("(%s -> %s, %f)", source.getNodeIndex(), destination.getNodeIndex(), weight);
    }

}
