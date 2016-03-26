package net;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

import java.util.Collection;

public class NetViewer {

    static String style ="graph {fill-color: white;}"
            +" node {fill-color: red; text-mode: normal;}"
            +" edge{fill-color: blue; }"
            +" edge.intersection {fill-color: red; size: 3px;}"
            +" edge.distinguished {fill-color: black; size: 3px;}"
            +" node.firstStation {fill-color: green;}"
            +" node.lastStation {fill-color: blue;}";

    public static void show(Graph graph) {
        graph.addAttribute("ui.stylesheet", NetViewer.style);
        Viewer viewer = graph.display();
    }

    public static View viewer_for_swing(Graph graph) {
        graph.addAttribute("ui.stylesheet", NetViewer.style);
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        return viewer.addDefaultView(false);
    }

    public static void reset_distinguish(Graph graph){
        for (Edge edge : graph.getEachEdge())
        {
            edge.addAttribute("ui.class","");
        }
        for (Node node : graph.getEachNode())
        {
            node.setAttribute("ui.class","");
        }
    }

    public static void distinguish_nodes(Graph graph, Collection<Edge> nodes){
        reset_distinguish(graph);
        for(Edge edge : nodes){
            edge.setAttribute("ui.class", "distinguished");
        }
    }

}
