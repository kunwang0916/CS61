package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;
import bearmaps.proj2ab.WeirdPointSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private PointSet pointSet;
    private Map<String, Node> nodeMap;
    private MyTrieSet nodeNameTrie;
    private Map<String, String> cleanToFullNameMap;
    private Map<String, List<Map<String, Object>>> cleanNameToLocationMapListMap;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>();
        nodeMap = new HashMap<>();
        cleanToFullNameMap = new HashMap<>();
        cleanNameToLocationMapListMap = new HashMap<>();
        nodeNameTrie = new MyTrieSet();
        for (Node node: nodes) {
            String fullName = node.name();
            if (fullName != null) {
                String cleanName = cleanString(node.name());
                nodeNameTrie.add(cleanName);
                cleanToFullNameMap.put(cleanName, node.name());
                addLocationToCleanNameToLocationMapListMap(cleanName, node);
            }

            if (this.neighbors(node.id()).size() == 0) {
                continue;
            }
            Point p = new Point(node.lon(), node.lat());
            nodeMap.put(pointToString(p), node);
            points.add(p);
        }
        pointSet = new WeirdPointSet(points);
    }

    private void addLocationToCleanNameToLocationMapListMap(String cleanName, Node node) {
        List<Map<String, Object>> list = cleanNameToLocationMapListMap.getOrDefault(cleanName, new ArrayList<>());
        list.add(nodeToMap(node));
        cleanNameToLocationMapListMap.put(cleanName, list);
    }

    private Map<String, Object> nodeToMap(Node node) {
        Map<String, Object> map = new HashMap<>();
        map.put("lat", node.lat());
        map.put("lon", node.lon());
        map.put("name", node.name());
        map.put("id", node.id());
        return map;
    }

    private String pointToString(Point p) {
        return String.format("%s-%s", p.getX(), p.getY());
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point p = pointSet.nearest(lon, lat);
        return nodeMap.get(pointToString(p)).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> cleanNames = nodeNameTrie.keysWithPrefix(cleanString(prefix));
        List<String> fullNames = new ArrayList<>();
        for (String cn: cleanNames) {
            fullNames.add(cleanToFullNameMap.get(cn));
        }
        return fullNames;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanName = cleanString(locationName);
        return cleanNameToLocationMapListMap.getOrDefault(cleanName, new LinkedList<>());
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
