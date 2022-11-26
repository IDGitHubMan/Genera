package com.IDDev.nodes;

import android.content.SharedPreferences;
import android.content.Context;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class NodeSketch extends PApplet {

    final Context context;
    Graph g;
    SharedPreferences prefs;
    SharedPreferences.Editor e;
    PVector touchStart;
    int oldWidth;

    NodeSketch(Context c) {
        context = c;
    }

    public void settings() {
        fullScreen();

    }

    public void setup() {
        prefs = context.getSharedPreferences(context.getString(R.string.prefsName), 0);
        e = prefs.edit();
        g = new Graph();
    }

    public void draw() {
        if (oldWidth != width) {
            g = new Graph();
            oldWidth = width;
        }
        background(prefs.getInt(context.getString(R.string.prefsNodesBGRed), 255), prefs.getInt(context.getString(R.string.prefsNodesBGGreen), 255), prefs.getInt("Nodes Background Blue", 255), prefs.getInt("Nodes Background Alpha", 255));
        g.update();
    }

    public void mousePressed() {
        if (prefs.getInt("Nodes Touch Action", 0) == 0) {
            g.nodes.add(new Node(mouseX, mouseY));
            int actual = 1 + prefs.getInt("Nodes Amount", 30);
            e.putInt("Nodes Amount", actual);
            e.apply();
        }
        if (prefs.getInt("Nodes Touch Action", 0) == 2) {
            for (Node n : g.nodes) {
                n.range = random(0, 1);
                n.vel = new PVector(random(-1, 1), random(-1, 1));
            }
        }
    }

    public void touchStarted() {
        touchStart = new PVector(mouseX, mouseY);
    }

    public void touchMoved() {
        if (prefs.getInt("Nodes Touch Action", 0) == 1 || prefs.getInt("Nodes Touch Action", 0) == 3) {
            stroke(255, 255);
            line(touchStart.x, touchStart.y, mouseX, mouseY);
        }
    }

    public void touchEnded() {
        if (prefs.getInt("Nodes Touch Action", 0) == 1) {
            PVector direction = new PVector(touchStart.x - mouseX, touchStart.y - mouseY);
            g.nodes.add(new Node(mouseX, mouseY, direction));
            int actual = 1 + prefs.getInt("Nodes Amount", 30);
            e.putInt("Nodes Amount", actual);
            e.apply();
        } else if (prefs.getInt("Nodes Touch Action", 0) == 3) {
            PVector direction = new PVector(mouseX - touchStart.x, mouseY - touchStart.y);
            g.nodes.add(new Node((int) touchStart.x, (int) touchStart.y, direction));
            int actual = 1 + prefs.getInt("Nodes Amount", 30);
            e.putInt("Nodes Amount", actual);
            e.apply();
        }
    }

    class Node {
        final PVector loc;
        PVector vel;
        final int nodeCol;
        float range;

        Node() {
            loc = new PVector(random(width), random(height));
            vel = new PVector(random(-1, 1), random(-1, 1));

            nodeCol = color(random(256), random(256), random(256), 255);
            range = random(0, 1);

        }

        Node(float x, float y) {
            loc = new PVector(x, y);
            vel = new PVector(random(-1, 1), random(-1, 1));

            nodeCol = color(random(256), random(256), random(256), 255);
            range = random(0, 1);
        }

        Node(int x, int y, PVector dir) {
            loc = new PVector(x, y);
            vel = new PVector(map(dir.x, -width, width, -prefs.getInt("Nodes Velocity Limit", 5), prefs.getInt("Nodes Velocity Limit", 5)), map(dir.y, -height, height, -prefs.getInt("Nodes Velocity Limit", 5), prefs.getInt("Nodes Velocity Limit", 5)));


            nodeCol = color(random(256), random(256), random(256), 255);
            range = random(0, 1);
        }

        public void update() {
            float actRange = map(range, 0, 1, prefs.getInt("Nodes Minimum Range", 25), prefs.getInt("Nodes Maximum Range", 300));
            PVector drift = new PVector(map(vel.x, -1, 1, -prefs.getInt("Nodes Velocity Limit", 5), prefs.getInt("Nodes Velocity Limit", 5)), map(vel.y, -1, 1, -prefs.getInt("Nodes Velocity Limit", 5), prefs.getInt("Nodes Velocity Limit", 5)));
            loc.x = constrain(loc.x + drift.x, 0, width);
            loc.y = constrain(loc.y + drift.y, 0, height);
            if (prefs.getInt("Nodes Edge Behavior", 0) == 0) {
                if (loc.x == 0 || loc.x == width) {
                    vel.x = -vel.x;
                }

                if (loc.y == 0 || loc.y == height) {
                    vel.y = -vel.y;
                }
            } else {
                if (loc.x == 0) {
                    loc.x = width - 1;
                }
                if (loc.x == width) {
                    loc.x = 1;
                }

                if (loc.y == 0) {
                    loc.y = height - 1;
                }
                if (loc.y == height) {
                    loc.y = 1;
                }
            }
            if (prefs.getBoolean("Nodes Core Display", true)) {
                if (prefs.getBoolean("Nodes Color Uniformity", false)) {
                    stroke(prefs.getInt("Nodes Uniform Red", 255), prefs.getInt("Nodes Uniform Green", 255), prefs.getInt("Nodes Uniform Blue", 255));
                    fill(prefs.getInt("Nodes Uniform Red", 255));
                } else {
                    stroke(nodeCol);
                    fill(nodeCol);
                }
                ellipse(loc.x, loc.y, 10, 10);
            }
            if (prefs.getBoolean(context.getString(R.string.prefs_range_key), true)) {
                noFill();
                strokeWeight(2);
                if (prefs.getBoolean("Nodes Color Uniformity", false)) {
                    stroke(prefs.getInt("Nodes Uniform Red", 255), prefs.getInt("Nodes Uniform Green", 255), prefs.getInt("Nodes Uniform Blue", 255));
                } else {
                    stroke(nodeCol);
                }
                if (prefs.getBoolean("Nodes Range Uniformity", false)) {
                    ellipse(loc.x, loc.y, prefs.getInt("Nodes Uniform Range", 100) * 2, prefs.getInt("Nodes Uniform Range", 100) * 2);
                } else {
                    ellipse(loc.x, loc.y, actRange * 2, actRange * 2);
                }
            }
        }
    }

    class Graph {
        final ArrayList<Node> nodes = new ArrayList<>();

        Graph() {
            for (int i = 0; i < prefs.getInt("Nodes Amount", 30); i++) {
                nodes.add(new Node());
            }
        }

        public void update() {
            if (prefs.getInt("Nodes Amount", 30) != 0) {
                if (prefs.getInt("Nodes Amount", 30) > 300) {
                    e.putInt("Nodes Amount", 300);
                    e.apply();
                }
                if (prefs.getInt("Nodes Amount", 30) > nodes.size()) {
                    for (int i = nodes.size(); i < prefs.getInt("Nodes Amount", 30); i++) {
                        nodes.add(new Node());
                    }
                } else if (prefs.getInt("Nodes Amount", 30) < nodes.size()) {
                    for (int i = 0; i < nodes.size() - prefs.getInt("Nodes Amount", 30); i++) {
                        nodes.remove((int)random(nodes.size()));
                    }
                }
            }
            for (Node n : nodes) {
                for (Node n2 : nodes) {
                    float distance;
                    try {
                        distance = dist(n.loc.x, n.loc.y, n2.loc.x, n2.loc.y);
                    } catch (Exception e) {
                        fill(255, 0, 0);
                        textAlign(LEFT, TOP);
                        textSize(32);
                        text("Node change needs to update.", 0, 300);
                        distance = dist(n.loc.x, n.loc.y, n2.loc.x, n2.loc.y);
                    }
                    float lineAlpha;
                    if (prefs.getBoolean("Nodes Range Uniformity", false)) {
                        lineAlpha = map(distance, 1, prefs.getInt("Nodes Uniform Range", 100), 255, 0);
                    } else {
                        lineAlpha = map(distance, 1, map(n.range, 0, 1, prefs.getInt("Nodes Minimum Range", 25), prefs.getInt("Nodes Maximum Range", 300)), 255, 0);
                    }
                    if (!prefs.getBoolean("Nodes Range Uniformity", false)) {
                        if (distance <= map(n.range, 0, 1, prefs.getInt("Nodes Minimum Range", 25), prefs.getInt("Nodes Maximum Range", 300)) && distance <= map(n2.range, 0, 1, prefs.getInt("Nodes Minimum Range", 25), prefs.getInt("Nodes Maximum Range", 300)) && distance != 0) {
                            if (prefs.getBoolean("Nodes Sparkle Visibility", false)) {
                                strokeWeight(prefs.getInt("Nodes Sparkle Size", 4));
                                if (prefs.getInt("Nodes Sparkle Coloration", 0) == 0) {
                                    stroke(prefs.getInt("Nodes Sparkle Red", 255), prefs.getInt("Nodes Sparkle Green", 255), prefs.getInt("Nodes Sparkle Blue", 255));
                                } else if (prefs.getInt("Nodes Sparkle Coloration", 0) == 1) {
                                    if (prefs.getBoolean("Nodes Color Uniformity", false)) {
                                        stroke(prefs.getInt("Nodes Uniform Red", 255), prefs.getInt("Nodes Uniform Green", 255), prefs.getInt("Nodes Uniform Blue", 255));
                                    } else {
                                        stroke(lerpColor(n.nodeCol,n2.nodeCol,0.5f));
                                    }
                                } else {
                                    stroke(random(255), random(255), random(255));
                                }
                                point((n.loc.x + n2.loc.x) / 2 + random(-prefs.getInt("Nodes Sparkle Displacement", 4), prefs.getInt("Nodes Sparkle Displacement", 4)), (n.loc.y + n2.loc.y) / 2 + random(-prefs.getInt("Nodes Sparkle Displacement", 4), prefs.getInt("Nodes Sparkle Displacement", 4)));
                            }
                            if (prefs.getBoolean("Nodes Connection Visibility", true)) {
                                strokeWeight(1);
                                if (prefs.getBoolean("Nodes Color Uniformity", false)) {
                                    stroke(prefs.getInt("Nodes Uniform Red", 255), prefs.getInt("Nodes Uniform Green", 255), prefs.getInt("Nodes Uniform Blue", 255));
                                } else {
                                    stroke(n.nodeCol, lineAlpha);
                                }
                                line(n.loc.x, n.loc.y, (n.loc.x + n2.loc.x) / 2, (n.loc.y + n2.loc.y) / 2);
                            }
                        } else if (distance <= map(n.range, 0, 1, prefs.getInt("Nodes Minimum Range", 25), prefs.getInt("Nodes Maximum Range", 300)) && distance != 0 && prefs.getBoolean("Nodes Connection Visibility", true)) {
                            if (prefs.getBoolean("Nodes Color Uniformity", false)) {
                                stroke(prefs.getInt("Nodes Uniform Red", 255), prefs.getInt("Nodes Uniform Green", 255), prefs.getInt("Nodes Uniform Blue", 255));
                            } else {
                                stroke(n.nodeCol);
                            }
                            line(n.loc.x, n.loc.y, n2.loc.x, n2.loc.y);
                        }

                    } else {
                        if (distance <= prefs.getInt("Nodes Uniform Range", 100) && distance != 0) {
                            if (prefs.getBoolean("Nodes Sparkle Visibility", false)) {
                                strokeWeight(prefs.getInt("Nodes Sparkle Size", 4));
                                if (prefs.getInt("Nodes Sparkle Coloration", 0) == 0) {
                                    stroke(prefs.getInt("Nodes Sparkle Red", 255), prefs.getInt("Nodes Sparkle Green", 255), prefs.getInt("Nodes Sparkle Blue", 255));
                                } else if (prefs.getInt("Nodes Sparkle Coloration", 0) == 1) {
                                    if (prefs.getBoolean("Nodes Color Uniformity", false)) {
                                        stroke(prefs.getInt("Nodes Uniform Red", 255), prefs.getInt("Nodes Uniform Green", 255), prefs.getInt("Nodes Uniform Blue", 255));
                                    } else {
                                        stroke(lerpColor(n.nodeCol,n2.nodeCol,0.5f));
                                    }
                                } else {
                                    stroke(random(255), random(255), random(255));
                                }
                                point((n.loc.x + n2.loc.x) / 2 + random(-prefs.getInt("Nodes Sparkle Displacement", 4), prefs.getInt("Nodes Sparkle Displacement", 4)), (n.loc.y + n2.loc.y) / 2 + random(-prefs.getInt("Nodes Sparkle Displacement", 4), prefs.getInt("Nodes Sparkle Displacement", 4)));
                            }
                            if (prefs.getBoolean("Nodes Connection Visibility", true)) {
                                strokeWeight(1);
                                if (prefs.getBoolean("Nodes Color Uniformity", false)) {
                                    stroke(prefs.getInt("Nodes Uniform Red", 255), prefs.getInt("Nodes Uniform Green", 255), prefs.getInt("Nodes Uniform Blue", 255));
                                } else {
                                    stroke(n.nodeCol, lineAlpha);
                                }
                                line(n.loc.x, n.loc.y, (n.loc.x + n2.loc.x) / 2, (n.loc.y + n2.loc.y) / 2);
                            }
                        }
                    }
                }
                n.update();
            }
        }
    }
}
