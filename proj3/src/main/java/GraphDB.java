import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /**
     * Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc.
     */
    //存储节点
    Map<Long,Node> nodes;
    //存储边
    Map<Long,Edge> edges;

    //定义一个节点类
    static class Node {
        private long id;
        private double lon, lat;
        //用来存储Node的标签
        private Map<String, String> tags;
        //用来存储与此节点相邻的节点
        private Set<Long> adjacentNode;

        public Node(long id, double lon, double lat) {
            this.id = id;
            this.lon = lon;
            this.lat = lat;
            tags = new HashMap<>();//初始化
            adjacentNode=new HashSet<>();

        }
        public long getId(){
            return id;
        }
        public double getLon(){
            return lon;
        }
        public double getLat(){
            return lat;
        }
        //添加标签
        public void addTag(String k,String v){
            tags.put(k,v);
        }
        //添加相邻节点
        public void addAdjacentNode(long id){
            adjacentNode.add(id);
        }

        //返回相邻节点的数量
        public int getNumAdjNode(){
            return adjacentNode.size();
        }
        //返回相邻的节点
        public Set<Long> getAdjNode(){
            return adjacentNode;
        }
    }
    //定义一个边类
    static class Edge{
        private long id;
        //此列表用来存储这个边上的节点，只存储节点的id
        private List<Long> refNode;
        private Map<String,String> tags;

        public Edge(long id){
            this.id=id;
            refNode=new ArrayList<>();
            tags=new HashMap<>();
        }
        //添加这条边上的节点
        public void addRefNode(long id){
            refNode.add(id);
        }
        //添加tag
        public void addTag(String k,String v){
            tags.put(k,v);
        }
        //获取这条edge上的所有节点
        public List<Long> getRefNode(){
            return refNode;
        }

    }

    //添加节点
    public void addNode(Node node){
        nodes.put(node.getId(),node);
    }
    //删除节点
    public void deleteNode(Node node){
        nodes.remove(node.getId(),node);
    }

    //添加edge
    public void addEdge(long id){
        Edge edge=new Edge(id);
        edges.put(id,edge);
    }
    //连接两个节点
    public void connectTwoNode(long id1,long id2){
        Node node1=nodes.get(id1);
        Node node2=nodes.get(id2);
        if (node1 != null && node2 != null) {
        node1.addAdjacentNode(id2);
        node2.addAdjacentNode(id1);
        }
    }
    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            nodes=new HashMap<>();
            edges=new HashMap<>();

            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        /*
        for (Long id: nodes.keySet()){
            Node node=nodes.get(id);
            if (node.getAdjNode().size()==0){
                nodes.remove(id,node);
            }
        }
        这个逻辑不对，在遍历的时候修改了nodes
         */
        List<Long> keysToRemove=new ArrayList<>();
        Set<Long> setId=nodes.keySet();
        for (Long id: setId){
            Node node=nodes.get(id);
            if (node.getAdjNode().size()==0){
                keysToRemove.add(node.getId());
            }
        }

        for (int i=0;i< keysToRemove.size();i++){
            nodes.remove(keysToRemove.get(i));
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     *
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return new ArrayList<>(nodes.keySet());
    }

    /**
     * Returns ids of all vertices adjacent to v.
     *
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return nodes.get(v).getAdjNode();
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     *
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double minDistance = Double.MAX_VALUE;
        long closestId = -1;
        for (Node node : nodes.values()) {
            double dist = distance(lon, lat, node.getLon(), node.getLat());
            if (dist < minDistance) {
                minDistance = dist;
                closestId = node.getId();
            }
        }
        return closestId;
    }

    /**
     * Gets the longitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodes.get(v).getLon();
    }

    /**
     * Gets the latitude of a vertex.
     *
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodes.get(v).getLat();
    }
}
