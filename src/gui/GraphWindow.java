package gui;

import net.NetViewer;
import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.View;

import javax.swing.*;
import java.awt.*;

public class GraphWindow extends JPanel {

    public GraphWindow() {
        this.setPreferredSize(new Dimension(450, 450));
        View view = NetViewer.viewer_for_swing(MainWindow.graph);
        view.setPreferredSize(new Dimension(450, 450));
        this.add(view);
    }
}
