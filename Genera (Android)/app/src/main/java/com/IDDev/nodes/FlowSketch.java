package com.IDDev.nodes;

import processing.core.PApplet;
import processing.core.PVector;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;

public class FlowSketch extends PApplet {
    final Context context;
    SharedPreferences prefs;
    public boolean settingsMode = true;
    public ArrayList<Point> pointsList = new ArrayList<>();

    int oldWidth;
    int oldHeight;
    ArrayList<Drifter> drifters;

    class Drifter {
        PVector loc;
        final PVector acc;
        final PVector vel;
        final int col = color(random(255), random(255), random(255), random(255));

        Drifter() {
            loc = new PVector(random(width), random(height));
            acc = new PVector(0, 0);
            vel = new PVector(0, 0);
        }

        void update() {
            float theta;
            if (prefs.getInt("Flows Field System",1) == 0) {
                if (prefs.getInt("Flows Dynamism",0) == 0) {
                    if (prefs.getBoolean("Flows Noise Rotation",true)) {
                        theta = map(noise(floor(loc.x / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows X Val",1) * pow(10, 3 * prefs.getInt("Flows X Order",1)), floor(loc.y / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows Y Val",1) * pow(10, 3 * prefs.getInt("Flows Y Order",1)), (millis() / (float) 1000)), 0, 1, -TWO_PI, TWO_PI);
                    } else {
                        theta = map(noise(floor(loc.x / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows X Val",1) * pow(10, 3 * prefs.getInt("Flows X Order",1)), floor(loc.y / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows Y Val",1) * pow(10, 3 * prefs.getInt("Flows Y Order",1)), (millis() / (float) 1000)), 0, 1, 0, TWO_PI);
                    }
                } else if (prefs.getInt("Flows Dynamism",0) == 1) {
                    if (prefs.getBoolean("Flows Noise Rotation",true)) {
                        theta = map(noise(floor(loc.x / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows X Val",1) * pow(10, 3 * prefs.getInt("Flows X Order",1)), floor(loc.y / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows Y Val",1) * pow(10, 3 * prefs.getInt("Flows Y Order",1)), floor((millis() / (float) prefs.getInt("Flows Time Interval",5000)))), 0, 1, -TWO_PI, TWO_PI);
                    } else {
                        theta = map(noise(floor(loc.x / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows X Val",1) * pow(10, 3 * prefs.getInt("Flows X Order",1)), floor(loc.y / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows Y Val",1) * pow(10, 3 * prefs.getInt("Flows Y Order",1)), floor((millis() / (float) prefs.getInt("Flows Time Interval",5000)))), 0, 1, 0, TWO_PI);
                    }
                } else {
                    if (prefs.getBoolean("Flows Noise Rotation",true)) {
                        theta = map(noise(floor(loc.x / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows X Val",1) * pow(10, 3 * prefs.getInt("Flows X Order",1)), floor(loc.y / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows Y Val",1) * pow(10, 3 * prefs.getInt("Flows Y Order",1))), 0, 1, -TWO_PI, TWO_PI);
                    } else {
                        theta = map(noise(floor(loc.x / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows X Val",1) * pow(10, 3 * prefs.getInt("Flows X Order",1)), floor(loc.y / prefs.getInt("Flows Field Resolution",1)) * prefs.getInt("Flows Y Val",1) * pow(10, 3 * prefs.getInt("Flows Y Order",1))), 0, 1, 0, TWO_PI);
                    }
                }
                PVector flowForce = new PVector(cos(theta), sin(theta));
                flowForce.mult(100);
                acc.add(flowForce);
                if (loc.x > width) {
                    loc.x = 0;
                }
                if (loc.x < 0) {
                    loc.x = width;
                }
                if (loc.y > height) {
                    loc.y = 0;
                }
                if (loc.y < 0) {
                    loc.y = height;
                }
            } else {
                if (settingsMode) {
                    for (int i = 0; i < pointsList.size(); i++) {
                        stroke(255);
                        fill(255);
                        textAlign(CENTER, CENTER);
                        textSize(50);
                        text(i + 1, pointsList.get(i).pos.x, pointsList.get(i).pos.y);
                    }
                }
                if (loc.x > width || loc.x < 0 || loc.y > height || loc.y < 0) {
                    loc = new PVector(random(width), random(height));
                }
                if (pointsList.size() < 1) {
                    pointsList = new ArrayList<>(Arrays.asList(new Point((int) random(3), width / 2, height / 2), new Point((int) random(3))));
                }
                for (Point point : pointsList) {
                    if (dist(point.pos.x, point.pos.y, loc.x, loc.y) <= 10) {
                        float x, y;
                        int choiceDim = (int) random(2);
                        int choiceEdge = (int) random(2);
                        if (choiceDim == 0) {
                            x = random(width);
                            if (choiceEdge == 0) {
                                loc = new PVector(x, 0);
                            } else {
                                loc = new PVector(x, height);
                            }
                        } else {
                            y = random(height);
                            if (choiceEdge == 0) {
                                loc = new PVector(0, y);
                            } else {
                                loc = new PVector(width, y);
                            }
                        }
                    }
                    if (point.type == 0) {
                        float factor = map(dist(point.pos.x, point.pos.y, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
                        PVector desired = PVector.sub(point.pos, loc);
                        desired.normalize();
                        desired.mult(factor);
                        acc.add(desired);

                    } else if (point.type == 1) {
                        float factor = map(dist(point.pos.x, point.pos.y, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
                        PVector desired = PVector.sub(point.pos, loc);
                        desired.normalize();
                        desired.mult(factor);
                        acc.sub(desired);
                    } else {
                        float orbTheta = atan((point.pos.y - loc.y) / (point.pos.x - loc.x));
                        float r = dist(point.pos.x, point.pos.y, loc.x, loc.y);
                        PVector target;
                        if (point.pos.x >= loc.x) {
                            target = new PVector(-r * cos(orbTheta + 0.01f) + point.pos.x, -r * sin(orbTheta + 0.01f) + point.pos.y);
                        } else {
                            target = new PVector(r * cos(orbTheta + 0.01f) + point.pos.x, r * sin(orbTheta + 0.01f) + point.pos.y);
                        }
                        PVector desired = PVector.sub(target, loc);
                        float factor = map(dist(point.pos.x, point.pos.y, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
                        desired.normalize();
                        desired.mult(factor);
                        acc.add(desired);
                    }
                }
            }
            vel.add(acc);
            vel.limit(prefs.getInt("Flows Velocity Limit",5));
            loc.add(vel);
            acc.mult(0);
            strokeWeight(prefs.getInt("Flows Flow Size",5));
            if (prefs.getInt("Flows Coloration",4) == 0) {
                stroke(prefs.getInt("Flows Uniform Red",255), prefs.getInt("Flows Uniform Green",255), prefs.getInt("Flows Uniform Blue",255));
            } else if (prefs.getInt("Flows Coloration",4) == 1) {
                stroke(lerpColor(color(prefs.getInt("Flows Horizontal 1 Red",255), prefs.getInt("Flows Horizontal 1 Green",255), prefs.getInt("Flows Horizontal 1 Blue",255)), color(prefs.getInt("Flows Horizontal 2 Red",0), prefs.getInt("Flows Horizontal 2 Green",0), prefs.getInt("Flows Horizontal 2 Blue",255)), map(loc.x, 0, width, 0, 1)));
            } else if (prefs.getInt("Flows Coloration",4) == 2) {
                stroke(lerpColor(color(prefs.getInt("Flows Vertical 1 Red",255), prefs.getInt("Flows Vertical 1 Green",255), prefs.getInt("Flows Vertical 1 Blue",255)), color(prefs.getInt("Flows Vertical 2 Red",255), prefs.getInt("Flows Vertical 2 Green",255), prefs.getInt("Flows Vertical 2 Blue",255)), map(loc.y, 0, height, 0, 1)));
            } else if (prefs.getInt("Flows Coloration",4) == 3) {
                int c1 = color(lerpColor(color(prefs.getInt("Flows Horizontal 1 Red",255), prefs.getInt("Flows Horizontal 1 Green",255), prefs.getInt("Flows Horizontal 1 Blue",255)), color(prefs.getInt("Flows Horizontal 2 Red",0), prefs.getInt("Flows Horizontal 2 Green",0), prefs.getInt("Flows Horizontal 2 Blue",255)), map(loc.x, 0, width, 0, 1)));
                int c2 = color(lerpColor(color(prefs.getInt("Flows Vertical 1 Red",255), prefs.getInt("Flows Vertical 1 Green",255), prefs.getInt("Flows Vertical 1 Blue",255)), color(prefs.getInt("Flows Vertical 2 Red",255), prefs.getInt("Flows Vertical 2 Green",255), prefs.getInt("Flows Vertical 2 Blue",255)), map(loc.y, 0, height, 0, 1)));
                stroke(lerpColor(c1, c2, 0.5f));
            } else if (prefs.getInt("Flows Coloration",4) == 4) {
                stroke(col);
            } else {
                stroke(random(255), random(255), random(255), 255);
            }
            point(loc.x, loc.y);
        }
    }

    class Point {
        int type;
        final PVector pos;

        Point(int t) {
            type = t;
            pos = new PVector(random(width), random(height));
        }

        Point(int t, int x, int y) {
            type = t;
            pos = new PVector(x, y);
        }
    }

    FlowSketch(Context c) {
        context = c;
    }

    public void settings() {
        fullScreen();
    }

    public void setup() {
        oldWidth = width;
        oldHeight = height;
        prefs = context.getSharedPreferences(context.getString(R.string.prefsName),0);
        drifters = new ArrayList<>();
        for (int i = 0; i < 100 + prefs.getInt("Flows Flow Count",1000); i++) {
            drifters.add(new Drifter());
        }
        for (int i = 0; i < prefs.getInt("Flows Point Count",2); i++) {
            pointsList.add(new Point((int) random(3), (int) random(width), (int) random(height)));
        }
    }

    public void draw() {
        if (drifters.size() < prefs.getInt("Flows Flow Count",1000)) {
            for (int i = drifters.size(); i < prefs.getInt("Flows Flow Count",1000); i++) {
                drifters.add(new Drifter());
            }
        } else if (drifters.size() > prefs.getInt("Flows Flow Count",1000)) {
            for (int i = 0; i < drifters.size() - prefs.getInt("Flows Flow Count",1000); ) {
                drifters.remove((int)random(drifters.size()));
            }
        }
        if (oldWidth != width || oldHeight != height) {
            oldWidth = width;
            oldHeight = height;
            for (Drifter drift : drifters) {
                drift.loc = new PVector(random(width), random(height));
            }
        }
        background(prefs.getInt("Flows Background Red",0), prefs.getInt("Flows Background Green",0), prefs.getInt("Flows Background Blue",0), prefs.getInt("Flows Background Alpha",20));
        for (Drifter drift : drifters) {
            drift.update();
        }
    }
}
