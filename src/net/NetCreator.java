package net;

import analyse.Analyse;
import org.graphstream.algorithm.APSP;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.NodeFactory;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class NetCreator {

    static APSP apsp = new APSP();
    public static String flows_attribute_name = "flows";
    public static String distance_attribute_name = "distance";
    public static String capacity_attribute_name = "capacity";
    public static String total_rate_name = "total_rate";

    public static Graph example_graph(){

        //create graph
        Graph graph = new SingleGraph("example");
        NetCreator.add_brunch(graph,"A", 15, "-");
        NetCreator.add_brunch(graph,"B", 7, "A5");
        NetCreator.add_brunch(graph,"C", 8, "A10");
        set_attributes(graph);

        //
        apsp.init(graph);
        apsp.setDirected(false);
        apsp.setWeightAttributeName(NetCreator.distance_attribute_name);
        apsp.compute();

        //add flows
        NetCreator.add_flow(graph, "A0", "B3", 1.58);
        NetCreator.add_flow(graph, "A0", "C5", 3.22);

        //analyse
        Analyse.calculate_total_flows(graph);

        return graph;
    }

    public static void set_attributes(Graph graph){

        HashMap<String,Object> edge_attributes = new HashMap<>();
        edge_attributes.put(NetCreator.capacity_attribute_name, 3.0);
        edge_attributes.put(NetCreator.distance_attribute_name, 1.0);
        edge_attributes.put(NetCreator.total_rate_name, 0.0);

        HashMap<String,Object> node_attributes = new HashMap<>();
        node_attributes.put(NetCreator.flows_attribute_name, null);

        for (Node node : graph.getEachNode()){
            for (Map.Entry<String,Object> entry : node_attributes.entrySet()){
                node.addAttribute(entry.getKey(), new ArrayList<Flow>());
            }
        }

        for (Edge edge : graph.getEachEdge()){
            for (Map.Entry<String,Object> entry : edge_attributes.entrySet()){
                edge.addAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    public static Graph add_brunch(Graph graph, String suffix, int len, String attachment_station_name){
        for(int i = 0; i < len; i++){
            graph.addNode(suffix + Integer.toString(i));
            if(i > 0){
                graph.addEdge(suffix + Integer.toString(i-1) + "-" + suffix + Integer.toString(i), suffix + Integer.toString(i-1), suffix + Integer.toString(i));
            }
        }
        if(!attachment_station_name.equals("-")){
            graph.addEdge(attachment_station_name + "-" + suffix + "0", attachment_station_name, suffix + "0");
        }
        return graph;
    }

    public static void add_flow(Graph graph, String from, String to, double rate){
        Flow flow = new Flow(rate, (Stack<Edge>) NetCreator.shortest_route(graph, from, to));
        ArrayList<Flow> flows = (ArrayList<Flow>)graph.getNode(from).getAttribute(NetCreator.flows_attribute_name);
        flows.add(flow);
    }

    public static Collection<Edge> shortest_route(Graph graph, String from, String to) {
        APSP.APSPInfo info = graph.getNode(from).getAttribute(APSP.APSPInfo.ATTRIBUTE_NAME);
        return info.getShortestPathTo(to).getEdgeSet();
    }

    public static void set_capacity(Graph graph, String node_name, double capacity){
        graph.getNode(node_name).setAttribute(NetCreator.capacity_attribute_name, capacity);
    }
}
