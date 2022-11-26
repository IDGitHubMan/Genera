package com.IDDev.nodes;

import processing.android.PWallpaper;
import processing.core.PApplet;

public class NodePaper extends PWallpaper {
    public PApplet createSketch(){
        return new NodeSketch(getApplicationContext());
    }
}
