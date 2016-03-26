package gui;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;

import javax.swing.*;
import java.awt.*;

public class MainWindow {

    static Graph graph;

    public static void show(Graph graph) {
        MainWindow.graph = graph;
        JFrame frame = new JFrame("TrainsFlowPlaning");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout(100, 25));
        frame.add(new GraphWindow(), BorderLayout.CENTER);
        frame.add(new ControlWindow(), BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
    }

}
