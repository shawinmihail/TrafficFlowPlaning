package net;

import org.graphstream.algorithm.APSP;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.Collection;

public class NetCreator {

    static APSP apsp = new APSP();
    public static String flows_attribute_name = "flows";
    public static String distance_attribute_name = "distance";

    public static Graph example_graph(){

        //create graph
        Graph graph = new SingleGraph("example");
        NetCreator.add_brunch(graph,"A", 15, "-");
        NetCreator.add_brunch(graph,"B", 7, "A5");
        NetCreator.add_brunch(graph,"C", 8, "A10");

        //
        apsp.init(graph);
        apsp.setDirected(false);
        apsp.setWeightAttributeName(NetCreator.distance_attribute_name);
        apsp.compute();

        //add flows
        NetCreator.add_flow(graph, "A0", "B3", 1.58);
        NetCreator.add_flow(graph, "A0", "C5", 3.14);

        return graph;
    }

    public static Graph add_brunch(Graph graph, String suffix, int len, String attachment_station_name){
        for(int i = 0; i < len; i++){
            graph.addNode(suffix + Integer.toString(i));
            graph.getNode(suffix + Integer.toString(i)).addAttribute(NetCreator.flows_attribute_name, new ArrayList<Flow>());
            if(i > 0){
                graph.addEdge(suffix + Integer.toString(i-1) + "-" + suffix + Integer.toString(i), suffix + Integer.toString(i-1), suffix + Integer.toString(i));
                graph.getEdge(suffix + Integer.toString(i-1) + "-" + suffix + Integer.toString(i)).setAttribute(NetCreator.distance_attribute_name, 1);
            }
        }
        if(!attachment_station_name.equals("-")){
            graph.addEdge(attachment_station_name + "-" + suffix + "0", attachment_station_name, suffix + "0");
        }
        return graph;
    }

    public static void add_flow(Graph graph, String from, String to, double rate){
        Flow flow = new Flow(rate, NetCreator.shortest_route(graph, from, to));
        ArrayList<Flow> flows = (ArrayList<Flow>)graph.getNode(from).getAttribute(NetCreator.flows_attribute_name);
        flows.add(flow);
    }

    public static Collection<Edge> shortest_route(Graph graph, String from, String to) {
        APSP.APSPInfo info = graph.getNode(from).getAttribute(APSP.APSPInfo.ATTRIBUTE_NAME);
        return info.getShortestPathTo(to).getEdgeSet();
    }
}
