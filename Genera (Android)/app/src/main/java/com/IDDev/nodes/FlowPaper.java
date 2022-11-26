package com.IDDev.nodes;

import processing.android.PWallpaper;
import processing.core.PApplet;

public class FlowPaper extends PWallpaper {
    public PApplet createSketch() {
        FlowSketch f = new FlowSketch(this);
        f.settingsMode = false;
        return f;
    }
}
