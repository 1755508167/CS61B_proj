//import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     *
     * @param g       The graph to use.
     * @param stlon   The longitude of the start location.
     * @param stlat   The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        //离起点最近的节点的ID
        long startId = g.closest(stlon, stlat);
        //离终点最近的节点的ID
        long endId = g.closest(destlon, destlat);
        //Vertex作为参数,按照Vertex.fScore进行排序，值小的在最上面
        PriorityQueue<Vertex> openSet = new PriorityQueue<>(Comparator.comparingDouble(Vertex -> Vertex.fScore));
        Map<Long, Double> gScores = new HashMap<>();//从starNode到当前节点的距离
        Map<Long, Double> fScores = new HashMap<>();//gScore 加上预估的“到终点的直线距离”（启发函数）
        Map<Long, Long> cameFrom = new HashMap<>();//用来记录路径
        //初始化scores
        gScores.put(startId, 0.0);
        fScores.put(startId, g.distance(startId, endId));
        //把起始节点放入openSet中
        openSet.add(new Vertex(startId, fScores.get(startId)));

        while (!openSet.isEmpty()) {
            //返回并移除头部元素
            //去除fscore最小的点
            Vertex current = openSet.poll();

            if (current.id == endId) {
                return reconstructPath(cameFrom,endId);
            }
            for (long neighbor:g.adjacent(current.id)){
                //从起始节点到neighbor的距离
                double tentativeGScore= gScores.getOrDefault(current.id,Double.MAX_VALUE)+g.distance(current.id,neighbor);
                if (tentativeGScore < gScores.getOrDefault(neighbor,Double.MAX_VALUE)){
                    cameFrom.put(neighbor, current.id);
                    gScores.put(neighbor, tentativeGScore);
                    fScores.put(neighbor,tentativeGScore + g.distance(neighbor, endId));
                    openSet.add(new Vertex(neighbor, fScores.get(neighbor)));
                }
            }
        }
        return new ArrayList<>();
    }
    private static List<Long> reconstructPath(Map<Long,Long> cameFrom,long current){
        List<Long> path=new ArrayList<>();
        while (current != 0 && cameFrom.containsKey(current)){
            path.add(current);
             current=cameFrom.get(current);
        }
        path.add(current);
        Collections.reverse(path);
        return path;
    }

    private static class Vertex {
        long id;
        //此节点到起始节点和终点节点的距离之和
        double fScore;

        public Vertex(long id, double fScore) {
            this.id = id;
            this.fScore = fScore;
        }
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     *
     * @param g     The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null;
    }
    /*
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> directions = new ArrayList<>();
        if (route == null || route.size() < 2) {
            return directions; // 无法生成有效导航
        }

        Iterator<Long> iterator = route.iterator();
        long currentNode = iterator.next();
        long nextNode = iterator.next();

        String currentWay = g.getWayName(currentNode, nextNode);
        double segmentDistance = 0.0;
        double previousBearing = g.bearing(currentNode, nextNode);

        // 初始化第一个导航方向
        NavigationDirection direction = new NavigationDirection();
        direction.direction = NavigationDirection.START;
        direction.way = (currentWay == null || currentWay.isEmpty()) ? NavigationDirection.UNKNOWN_ROAD : currentWay;

        while (iterator.hasNext()) {
            long nextNextNode = iterator.next();
            String nextWay = g.getWayName(nextNode, nextNextNode); // 获取下一段路径名称
            double currentBearing = g.bearing(nextNode, nextNextNode);

            // 累加距离
            segmentDistance += g.distance(currentNode, nextNode);

            // 如果路径名称发生变化，则生成一个新的导航方向
            if (!Objects.equals(currentWay, nextWay)) {
                direction.distance = segmentDistance; // 记录当前段的总距离
                directions.add(direction); // 添加到导航方向列表

                // 初始化新段导航方向
                direction = new NavigationDirection();
                direction.direction = getTurnDirection(previousBearing, currentBearing);
                direction.way = (nextWay == null || nextWay.isEmpty()) ? NavigationDirection.UNKNOWN_ROAD : nextWay;
                segmentDistance = 0.0; // 重置段距离
            }

            // 更新状态
            currentNode = nextNode;
            nextNode = nextNextNode;
            currentWay = nextWay;
            previousBearing = currentBearing;
        }

        // 处理最后一段路径
        segmentDistance += g.distance(currentNode, nextNode);
        direction.distance = segmentDistance;
        directions.add(direction);

        return directions;
    }

     */
    private static int getTurnDirection(double previousBearing, double currentBearing) {
        double angle = (currentBearing - previousBearing + 360) % 360;

        if (angle < 15 || angle > 345) {
            return NavigationDirection.STRAIGHT;
        } else if (angle >= 15 && angle < 45) {
            return NavigationDirection.SLIGHT_RIGHT;
        } else if (angle >= 45 && angle < 135) {
            return NavigationDirection.RIGHT;
        } else if (angle >= 135 && angle < 225) {
            return NavigationDirection.SHARP_RIGHT;
        } else if (angle >= 225 && angle < 315) {
            return NavigationDirection.LEFT;
        } else {
            return NavigationDirection.SLIGHT_LEFT;
        }
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /**
         * Integer constants representing directions.
         */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /**
         * Number of directions supported.
         */
        public static final int NUM_DIRECTIONS = 8;

        /**
         * A mapping of integer values to directions.
         */
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /**
         * Default name for an unknown way.
         */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /**
         * The direction a given NavigationDirection represents.
         */
        int direction;
        /**
         * The name of the way I represent.
         */
        String way;
        /**
         * The distance along this way I represent.
         */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         *
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                        && way.equals(((NavigationDirection) o).way)
                        && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
