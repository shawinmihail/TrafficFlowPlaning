package net;

import org.graphstream.graph.Edge;

import java.util.ArrayList;
import java.util.Collection;

public class Flow {

    double rate;
    Collection<Edge> route;

    public Flow(double rate, Collection<Edge> route) {
        this.rate = rate;
        this.route = route;
    }

    public double getRate() {
        return rate;
    }

    public Collection<Edge> getRoute() {
        return route;
    }

    @Override
    public String toString() {
        return "Flow";
    }
}
