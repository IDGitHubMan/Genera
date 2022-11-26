class Graph {
  ControlP5 cp5;
  float maxRange = 300;
  float minRange = 25;
  float uniRange = 300;
  float alpha = 20;
  int nodeCount = 40;
  float driftLimit = 1;
  int sparkSize = 3;
  int dualSparkDisplacement = 1;
  boolean showConnections = true;
  boolean colorUniformity = false;
  boolean rangeUniformity = true;
  boolean displayNode = true;
  boolean displayRange = false;
  boolean displayDualSparks = true;
  int edgeBehavior = 0;
  int touchBehavior = 0;
  color allCol = color(66, 66, 207);
  color bg = color(0, 0, 0);
  int sparkStyle = 1;
  color sparkCol = color(255, 255, 255);
  ArrayList<Node> nodes;
  Graph(ControlP5 controller) {
    cp5 = controller;
    cp5.getTab("NODES").remove();
    cp5.addTab("NODES")
      .activateEvent(true);
    cp5.addGroup("nodes")
      .setPosition(0, 30)
      .setSize(200, 40)
      .setBackgroundHeight(height/2)
      .setTab("NODES");
    cp5.addSlider("nodeCount")
      .setPosition(20, 5)
      .setRange(1, 100)
      .setValue(nodeCount)
      .plugTo(this)
      .setLabel("Node amount")
      .setSliderMode(Slider.FLEXIBLE)
      .setGroup("nodes");
    cp5.addSlider("driftLimit")
      .setRange(0, 50)
      .setValue(driftLimit)
      .plugTo(this)
      .setPosition(20, 20)
      .setLabel("Speed limit")
      .setSliderMode(Slider.FLEXIBLE)
      .setGroup("nodes");
    cp5.addToggle("displayNode")
      .setValue(displayNode)
      .plugTo(this)
      .setPosition(30, 35)
      .setLabel("Cores")
      .setGroup("nodes")
      .getCaptionLabel()
      .align(ControlP5.CENTER, ControlP5.BOTTOM_OUTSIDE);
    cp5.addToggle("showConnections")
      .setValue(showConnections)
      .plugTo(this).setPosition(80, 35)
      .setLabel("Connections")
      .setGroup("nodes")
      .getCaptionLabel()
      .align(ControlP5.CENTER, ControlP5.BOTTOM_OUTSIDE);
    cp5.addToggle("displayRange")
      .setValue(displayRange)
      .plugTo(this).setPosition(130, 35)
      .setLabel("Ranges")
      .setGroup("nodes")
      .getCaptionLabel()
      .align(ControlP5.CENTER, ControlP5.BOTTOM_OUTSIDE);
    cp5.addToggle("rangeUniformity")
      .setValue(rangeUniformity)
      .plugTo(this).setPosition(20, 75)
      .setLabel("Uniform ranges")
      .setGroup("nodes")
      .getCaptionLabel()
      .align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER)
      .setPaddingX(5);
    cp5.addRange("sizeRange")
      .setRange(10, 300)
      .setRangeValues(minRange, maxRange)
      .setPosition(20, 100)
      .setLabel("Range")
      .setGroup("nodes")
      .plugTo(this);
    cp5.addSlider("uniRange")
      .setRange(10, 300)
      .setValue(uniRange)
      .plugTo(this)
      .setPosition(20, 100)
      .setLabel("Range")
      .setSliderMode(Slider.FLEXIBLE)
      .setGroup("nodes");
    cp5.addToggle("colorUniformity")
      .plugTo(this)
      .setValue(colorUniformity)
      .setGroup("nodes")
      .setPosition(20, 125)
      .setLabel("Color uniformity")
      .getCaptionLabel()
      .align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER)
      .setPaddingX(5);
    cp5.addColorWheel("allCol")
      .setRGB(allCol)
      .setPosition(20, 150)
      .setGroup("nodes")
      .plugTo(this)
      .hide();
    cp5.addGroup("sparkles!")
      .setPosition(200, 30)
      .setSize(200, 40)
      .setBackgroundHeight(height/2)
      .setTab("NODES");
    cp5.addToggle("displayDualSparks")
      .setValue(displayDualSparks)
      .setPosition(20, 5)
      .setLabel("Sparkles!")
      .setGroup("sparkles!")
      .plugTo(this)
      .getCaptionLabel()
      .align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER)
      .setPaddingX(5);
    cp5.addSlider("sparkSize")
      .setRange(1, 20)
      .setValue(sparkSize)
      .plugTo(this)
      .setPosition(20, 30)
      .setLabel("Spark size")
      .setSliderMode(Slider.FLEXIBLE)
      .setGroup("sparkles!");
    cp5.addSlider("dualSparkDisplacement")
      .setRange(0, 50)
      .setValue(dualSparkDisplacement)
      .plugTo(this)
      .setPosition(20, 45)
      .setLabel("Displacement")
      .setSliderMode(Slider.FLEXIBLE)
      .setGroup("sparkles!");
    cp5.addScrollableList("sparkStyle")
      .setLabel("Spark Color Mode")
      .setPosition(20, 60)
      .addItem("Uniform", 0)
      .addItem(" Blend Inherit", 1)
      .addItem("Direct Inherit", 2)
      .addItem("Random", 3)
      .setValue(sparkStyle)
      .plugTo(this)
      .setGroup("sparkles!");
    cp5.addColorWheel("sparkCol")
      .setRGB(sparkCol)
      .plugTo(this)
      .setPosition(20, 150)
      .setGroup("sparkles!")
      .setLabel("Sparkle Color")
      .hide();
    cp5.addGroup("nExtra")
      .setPosition(400, 30)
      .setSize(200, 40)
      .setBackgroundHeight(height/2)
      .setTab("NODES")
      .setLabel("extra");
    cp5.addBang("randomise")
      .setGroup("nExtra")
      .setPosition(20, 5)
      .plugTo(this)
      .getCaptionLabel()
      .align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER)
      .setPaddingX(5);
    cp5.addBang("graph")
      .setGroup("nExtra")
      .setPosition(20, 30)
      .setLabel("Reset")
      .getCaptionLabel()
      .align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER)
      .setPaddingX(5);
    cp5.addScrollableList("edgeBehavior")
      .addItem("Bounce", 0)
      .addItem("Loop", 1)
      .setValue(edgeBehavior)
      .plugTo(this)
      .setPosition(20, 55)
      .setGroup("nExtra");
    cp5.addColorWheel("bg")
      .setRGB(bg)
      .setAlpha((int)alpha(bg))
      .plugTo(this)
      .setPosition(width-200, height-200)
      .setTab("NODES")
      .setLabel("Background");
    cp5.addSlider("alpha")
      .plugTo(this)
      .setValue(20)
      .setPosition(width-200, height-230)
      .setRange(0, 255)
      .setSize(200, 30)
      .setSliderMode(Slider.FLEXIBLE)
      .setTab("NODES");
    cp5.getTab("NODES").bringToFront();
    cp5.addGroup("Touches")
      .setTab("NODES")
      .setPosition(600,30)
      .setSize(200,40);
    nodes = new ArrayList();
    for (int i = 0; i < nodeCount; i++) {
      nodes.add(new Node(this));
    }
  }

  void update() {
    noStroke();
    fill(bg, alpha);
    rectMode(CORNER);
    rect(0, 0, width, height);
    if (nodeCount != 0) {
      if (nodeCount>100) {
        nodeCount=100;
      }
      if (nodeCount > nodes.size()) {
        for (int i = 0; i < nodeCount - nodes.size(); i++) {
          nodes.add(new Node(this));
        }
      } else if (nodeCount < nodes.size()) {
        for (int i = 0; i < nodes.size() - nodeCount; i++) {
          nodes.remove((int)random(nodes.size()));
        }
      }
    }
    for (Node n : nodes) {
      for (Node n2 : nodes) {
        float distance;
        distance = dist(n.loc.x, n.loc.y, n2.loc.x, n2.loc.y);
        float lineAlpha;
        if (rangeUniformity) {
          lineAlpha = map(distance, 1, uniRange, 255, 0);
        } else {
          lineAlpha = map(distance, 1, map(n.range, 0, 1, minRange, maxRange), 255, 0);
        }
        if (!rangeUniformity) {
          if (distance <= map(n.range, 0, 1, minRange, maxRange) && distance <= map(n2.range, 0, 1, minRange, maxRange) && distance != 0) {
            if (displayDualSparks) {
              strokeWeight(sparkSize);
              if (sparkStyle == 0) {
                stroke(sparkCol, lineAlpha);
              } else if (sparkStyle == 1) {
                stroke(lerpColor(n.nodeCol, n2.nodeCol, 0.5), lineAlpha);
              } else if (sparkStyle == 2){
                if (colorUniformity) {
                  stroke(allCol, lineAlpha);
                } else {
                  stroke(n.nodeCol, lineAlpha);
                }
              }
              else {
                stroke(random(255), random(255), random(255), lineAlpha);
              }
              point((n.loc.x + n2.loc.x) / 2 + random(-dualSparkDisplacement, dualSparkDisplacement), (n.loc.y + n2.loc.y) / 2 + random(-dualSparkDisplacement, dualSparkDisplacement));
            }
            if (showConnections) {
              strokeWeight(sparkSize);
              if (colorUniformity) {
                stroke(allCol, lineAlpha);
              } else {
                stroke(n.nodeCol, lineAlpha);
              }
              line(n.loc.x, n.loc.y, (n.loc.x + n2.loc.x) / 2, (n.loc.y + n2.loc.y) / 2);
            }
          } else if (distance <= map(n.range, 0, 1, minRange, maxRange) && distance != 0 && showConnections) {
            if (colorUniformity) {
              stroke(allCol, lineAlpha);
            } else {
              stroke(n.nodeCol, lineAlpha);
            }
            line(n.loc.x, n.loc.y, n2.loc.x, n2.loc.y);
          }
        } else {
          if (distance <= uniRange && distance!=0) {
            if (displayDualSparks) {
              strokeWeight(sparkSize);
              if (sparkStyle==0) {
                stroke(sparkCol, 255);
              } else if (sparkStyle==1) {
                if (colorUniformity) {
                  stroke(allCol, lineAlpha);
                } else {
                  stroke(lerpColor(n.nodeCol, n2.nodeCol, 0.5), lineAlpha);
                }
              } else if (sparkStyle==2) {
                stroke(n.nodeCol, 255);
              } else {
                stroke(random(255), random(255), random(255), lineAlpha);
              }
              point((n.loc.x+n2.loc.x)/2+random(-dualSparkDisplacement, dualSparkDisplacement), (n.loc.y+n2.loc.y)/2+random(-dualSparkDisplacement, dualSparkDisplacement));
            }
            if (showConnections) {
              strokeWeight(sparkSize);
              if (colorUniformity) {
                stroke(allCol, lineAlpha);
              } else {
                stroke(n.nodeCol, lineAlpha);
              }
              line(n.loc.x, n.loc.y, (n.loc.x+n2.loc.x)/2, (n.loc.y+n2.loc.y)/2);
            }
          }
        }
      }
      n.update();
    }
  }

  void randomise() {
    nodeCount = int(random(10, 100));
    cp5.getController("nodeCount").setValue(nodeCount);
    maxRange = random(100, 400);
    controlP5.Range r = (controlP5.Range) cp5.getController("sizeRange");
    r.setHighValue(maxRange);
    minRange = int(random(50, 100));
    r.setLowValue(minRange);
    uniRange = int(random(50, 400));
    cp5.getController("uniRange").setValue(uniRange);
    driftLimit = random(0, 50);
    cp5.getController("driftLimit").setValue(driftLimit);
    sparkSize = int(random(1, 25));
    cp5.getController("sparkSize").setValue(sparkSize);
    dualSparkDisplacement = int(random(0, 100));
    cp5.getController("dualSparkDisplacement").setValue(dualSparkDisplacement);
    int mustShow = int(random(4));
    if (mustShow==0) {
      showConnections = true;
      int mustShow2 = int(random(2));
      if (mustShow2 == 0) {
        displayNode = true;
        displayRange = int(random(2)) == 1;
      } else {
        displayNode = int(random(2)) == 1;
        displayRange = true;
      }
      displayDualSparks = int(random(2)) == 1;
    } else if (mustShow == 1) {
      displayDualSparks = true;
      int mustShow2 = int(random(2));
      if (mustShow2 == 0) {
        displayNode = true;
        displayRange = int(random(2)) == 1;
      } else {
        displayNode = int(random(2)) == 1;
        displayRange = true;
      }
      showConnections = int(random(2)) == 1;
    } else if (mustShow == 2) {
      showConnections = int(random(2)) == 1;
      displayDualSparks = int(random(2)) == 1;
      displayNode = true;
      displayRange = int(random(2)) == 1;
    } else {
      showConnections = int(random(2)) == 1;
      displayDualSparks = int(random(2)) == 1;
      displayNode = int(random(2)) == 1;
      displayRange = true;
    }
    cp5.getController("showConnections").setValue(showConnections? 1:0);
    cp5.getController("displayDualSparks").setValue(displayDualSparks? 1:0);
    cp5.getController("displayNode").setValue(displayNode? 1:0);
    cp5.getController("displayRange").setValue(displayRange? 1:0);
    colorUniformity = int(random(2)) == 1;
    cp5.getController("colorUniformity").setValue(colorUniformity? 1:0);
    rangeUniformity = int(random(2)) == 1;
    cp5.getController("rangeUniformity").setValue(rangeUniformity? 1:0);
    edgeBehavior = int(random(2));
    cp5.getController("edgeBehavior").setValue(edgeBehavior);
    allCol = color(random(55, 255), random(55, 255), random(55, 255));
    controlP5.ColorWheel c = (controlP5.ColorWheel) cp5.getController("allCol");
    c.setRGB(allCol);
    sparkStyle = int(random(3));
    cp5.getController("sparkStyle").setValue(sparkStyle);
    sparkCol = color(random(255), random(255), random(255));
    c = (controlP5.ColorWheel) cp5.getController("sparkCol");
    c.setRGB(sparkCol);
  }

  void controlEvent(ControlEvent theControlEvent) {
    if (theControlEvent.getName() == "sizeRange") {
      minRange = theControlEvent.getController().getArrayValue(0);
      maxRange = theControlEvent.getController().getArrayValue(1);
    }
  }
}
