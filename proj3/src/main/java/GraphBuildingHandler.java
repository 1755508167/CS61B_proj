import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Parses OSM XML files using an XML SAX parser. Used to construct the graph of roads for
 * pathfinding, under some constraints.
 * See OSM documentation on
 * <a href="http://wiki.openstreetmap.org/wiki/Key:highway">the highway tag</a>,
 * <a href="http://wiki.openstreetmap.org/wiki/Way">the way XML element</a>,
 * <a href="http://wiki.openstreetmap.org/wiki/Node">the node XML element</a>,
 * and the java
 * <a href="https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html">SAX parser tutorial</a>.
 * <p>
 * You may find the CSCourseGraphDB and CSCourseGraphDBHandler examples useful.
 * <p>
 * The idea here is that some external library is going to walk through the XML
 * file, and your override method tells Java what to do every time it gets to the next
 * element in the file. This is a very common but strange-when-you-first-see it pattern.
 * It is similar to the Visitor pattern we discussed for graphs.
 *
 * @author Alan Yao, Maurice Lee
 */
public class GraphBuildingHandler extends DefaultHandler {
    /**
     * Only allow for non-service roads; this prevents going on pedestrian streets as much as
     * possible. Note that in Berkeley, many of the campus roads are tagged as motor vehicle
     * roads, but in practice we walk all over them with such impunity that we forget cars can
     * actually drive on them.
     */
    private static final Set<String> ALLOWED_HIGHWAY_TYPES = new HashSet<>(Arrays.asList
            ("motorway", "trunk", "primary", "secondary", "tertiary", "unclassified",
                    "residential", "living_street", "motorway_link", "trunk_link", "primary_link",
                    "secondary_link", "tertiary_link"));
    private String activeState = "";
    //在解析标签<way>时，用来记录这个way的highway类型合不合法
    private boolean flag;
    //用来记录上一条访问的way的id
    private long lastWayId;
    private long lastNodeId;
    //记录当前读取的这个way中的最后一个node的id
    private long lastNodeInWay;
    private final GraphDB g;

    /**
     * Create a new GraphBuildingHandler.
     *
     * @param g The graph to populate with the XML data.
     */
    public GraphBuildingHandler(GraphDB g) {
        this.g = g;
    }

    /**
     * Called at the beginning of an element. Typically, you will want to handle each element in
     * here, and you may want to track the parent element.
     *
     * @param uri        The Namespace URI, or the empty string if the element has no Namespace URI or
     *                   if Namespace processing is not being performed.
     * @param localName  The local name (without prefix), or the empty string if Namespace
     *                   processing is not being performed.
     * @param qName      The qualified name (with prefix), or the empty string if qualified names are
     *                   not available. This tells us which element we're looking at.
     * @param attributes The attributes attached to the element. If there are no attributes, it
     *                   shall be an empty Attributes object.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see Attributes
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        /* Some example code on how you might begin to parse XML files. */
        if (qName.equals("node")) {
            /* We encountered a new <node...> tag. */
            activeState = "node";
            long id = Long.parseLong(attributes.getValue("id"));
            double lon = Double.parseDouble(attributes.getValue("lon"));
            double lat = Double.parseDouble(attributes.getValue("lat"));
            GraphDB.Node node = new GraphDB.Node(id,lon,lat);
            lastNodeId=id;
            //添加节点
            g.addNode(node);
//            System.out.println("Node id: " + attributes.getValue("id"));
//            System.out.println("Node lon: " + attributes.getValue("lon"));
//            System.out.println("Node lat: " + attributes.getValue("lat"));

            /* TODO Use the above information to save a "node" to somewhere. */
            /* Hint: A graph-like structure would be nice. */

        } else if (qName.equals("way")) {
            /* We encountered a new <way...> tag. */
            activeState = "way";
            long id=Long.parseLong(attributes.getValue("id"));
            lastWayId=id;
            //添加边
            g.addEdge(id);
//            System.out.println("Beginning a way...");
        } else if (activeState.equals("way") && qName.equals("nd")) {
            /* While looking at a way, we found a <nd...> tag. */
            //System.out.println("Id of a node in this way: " + attributes.getValue("ref"));

            /* TODO Use the above id to make "possible" connections between the nodes in this way */
            /* Hint1: It would be useful to remember what was the last node in this way. */
            /* Hint2: Not all ways are valid. So, directly connecting the nodes here would be
            cumbersome since you might have to remove the connections if you later see a tag that
            makes this way invalid. Instead, think of keeping a list of possible connections and
            remember whether this way is valid or not. */
            //获取way里的node的id
            long id=Long.parseLong(attributes.getValue("ref"));
            //往edge中添加node
            g.edges.get(lastWayId).addRefNode(id);
            //记录这条way中最后一个节点的id
            lastNodeInWay=id;


        } else if (activeState.equals("way") && qName.equals("tag")) {
            /* While looking at a way, we found a <tag...> tag. */
            String k = attributes.getValue("k");
            String v = attributes.getValue("v");
            if (k.equals("maxspeed")) {
                //System.out.println("Max Speed: " + v);
                /* TODO set the max speed of the "current way" here. */
                //添加标签
                g.edges.get(lastWayId).addTag(k,v);
            } else if (k.equals("highway")) {
                //System.out.println("Highway type: " + v);
                /* TODO Figure out whether this way and its connections are valid. */
                /* Hint: Setting a "flag" is good enough! */
                //判断highway的类型是否有效
                if (ALLOWED_HIGHWAY_TYPES.contains(v)){
                    flag=true;
                    g.edges.get(lastWayId).addTag(k,v);
                }else {
                    flag=false;
                }
            } else if (k.equals("name")) {
                //System.out.println("Way Name: " + v);
                g.edges.get(lastWayId).addTag(k,v);
            }
//            System.out.println("Tag with k=" + k + ", v=" + v + ".");
        } else if (activeState.equals("node") && qName.equals("tag") && attributes.getValue("k")
                .equals("name")) {
            /* While looking at a node, we found a <tag...> with k="name". */
            /* TODO Create a location. */
            /* Hint: Since we found this <tag...> INSIDE a node, we should probably remember which
            node this tag belongs to. Remember XML is parsed top-to-bottom, so probably it's the
            last node that you looked at (check the first if-case). */
//            System.out.println("Node's name: " + attributes.getValue("v"));
            //添加节点的标签
            g.nodes.get(lastNodeId).addTag(attributes.getValue("k"),attributes.getValue("v"));
        }
    }

    /**
     * Receive notification of the end of an element. You may want to take specific terminating
     * actions here, like finalizing vertices or edges found.
     *
     * @param uri       The Namespace URI, or the empty string if the element has no Namespace URI or
     *                  if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName     The qualified name (with prefix), or the empty string if qualified names are
     *                  not available.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //在way是合法的前提下
        if (qName.equals("way")&& flag) {
            /* We are done looking at a way. (We finished looking at the nodes, speeds, etc...)*/
            /* Hint1: If you have stored the possible connections for this way, here's your
            chance to actually connect the nodes together if the way is valid. */
//            System.out.println("Finishing a way...");
        //读取完这条way的所有节点之后，连接这些节
            flag=false;
            GraphDB.Edge edge=g.edges.get(lastWayId);
            //获取edge中的所有节点
            List<Long> nodeInEdge=edge.getRefNode();
            //前一个节点与后一个节点两两连接
            for (int i=0;i<nodeInEdge.size()-1;i++){
                g.connectTwoNode(nodeInEdge.get(i),nodeInEdge.get(i+1));
            }

        }
    }

}
