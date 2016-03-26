package gui;

import net.Flow;
import net.NetCreator;
import net.NetViewer;
import org.graphstream.graph.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class ControlWindow extends JPanel {

    JComboBox<Node> stations_box = new JComboBox<Node>(new Vector<Node>(MainWindow.graph.getNodeSet()));
    JComboBox<Flow> flows_box = new JComboBox<Flow>();

    public ControlWindow() {

        this.setPreferredSize(new Dimension(450, 200));
        stations_box.setPreferredSize(new Dimension(75, 30));
        stations_box_act();
        flows_box.setPreferredSize(new Dimension(75, 30));
        flows_box_act();

        stations_box.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stations_box_act();
            }
        });
        flows_box.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flows_box_act();
            }
        });

        this.add(stations_box);
        this.add(flows_box);
    }

    void stations_box_act(){
        flows_box.removeAllItems();
        Node node = (Node)stations_box.getSelectedItem();
        ArrayList<Flow> flows = (ArrayList<Flow>)node.getAttribute(NetCreator.flows_attribute_name);
        for(Flow flow : flows){
            flows_box.addItem(flow);
        }
    }

    void flows_box_act(){
        Flow flow = (Flow)flows_box.getSelectedItem();
        NetViewer.distinguish_nodes(MainWindow.graph, flow.getRoute());
    }
}
