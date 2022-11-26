class DrifterCol {
  ControlP5 cp5;
  int resolution = 1;
  int drifterCount = 5000;
  int drifterAmount = drifterCount/100;
  float drifterSize = 1.0f;
  int velLimit = 5;
  int ba = 0;
  boolean showPoints = true;
  int touchAction = 0;
  boolean touching = true;

  int fieldGenSystem = 1;
  int dynamicMode = 2;
  int timeInterval = 5000;
  int colorMode = 1;
  color uniform = color(0, 119, 255);
  color xGradStart = color(255, 255, 255);
  color xGradEnd = color(0, 255, 0);
  color yGradStart = color(255, 255, 0);
  color yGradEnd = color(255, 0, 0);
  color back = color(0, 0, 0);
  boolean noiseFullCircle = true;

  int pCount = 3;

  int noiseX1 = 99;
  int noiseX2 = 2;
  int noiseY1 = 1;
  int noiseY2 = 1;

  ArrayList<Point> points = new ArrayList();

  PGraphics plat = createGraphics(width, height);
  ArrayList<Drifter> flows = new ArrayList();

  DrifterCol(ControlP5 controller) {
    cp5 = controller;
    for (int i = 0; i < drifterCount; i++) {
      flows.add(new Drifter(this));
    }
    points.add(new Point(0, width/2, height/2));
    points.add(new Point(2, width/2+50, height/2));
    points.add(new Point(2, width/2-50, height/2));
    cp5.getTab("Flows").remove();
    cp5.addTab("Flows").activateEvent(true);
    cp5.addGroup("flows")
      .setTab("Flows")
      .setPosition(0, 30)
      .setSize(200, 40);
    cp5.addSlider("drifterAmount")
      .setGroup("flows")
      .setPosition(20, 5)
      .setRange(1, 50)
      .setValue(drifterAmount)
      .plugTo(this)
      .setSliderMode(Slider.FLEXIBLE);
    cp5.addSlider("drifterSize")
      .setGroup("flows")
      .setPosition(20, 20)
      .setRange(1, 15)
      .setValue(drifterSize)
      .plugTo(this)
      .setSliderMode(Slider.FLEXIBLE);
    cp5.addSlider("velLimit")
      .setGroup("flows")
      .setPosition(20, 35)
      .setRange(0, 50)
      .setValue(velLimit)
      .setSliderMode(Slider.FLEXIBLE)
      .plugTo(this);
    cp5.addScrollableList("fieldGenSystem")
      .setGroup("flows")
      .plugTo(this)
      .setPosition(20, 50)
      .addItem("Noise", 0)
      .addItem("Points", 1)
      .setValue(fieldGenSystem);
    cp5.addScrollableList("colorMode")
      .setGroup("flows")
      .setPosition(20, 90)
      .addItem("Unique", 0)
      .addItem("Horizontal Gradient", 1)
      .addItem("Vertical Gradient", 2)
      .addItem("Dual Gradient", 3)
      .addItem("Uniform", 4)
      .setValue(colorMode)
      .plugTo(this);
    cp5.addGroup("noise")
      .setTab("Flows")
      .setPosition(200, 30)
      .setSize(200, 40);
    cp5.addSlider("resolution")
      .plugTo(this)
      .setGroup("noise")
      .setPosition(20, 5)
      .setRange(1, 20)
      .setValue(resolution)
      .setSliderMode(Slider.FLEXIBLE);
    cp5.addToggle("noiseFullCircle")
      .plugTo(this)
      .setGroup("noise")
      .setPosition(20, 20)
      .setLabel("360?")
      .setValue(noiseFullCircle)
      .getCaptionLabel()
      .align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER)
      .setPaddingX(5);
    cp5.addSlider("noiseX1")
      .setGroup("noise")
      .setValue(noiseX1)
      .plugTo(this)
      .setPosition(20, 45)
      .setRange(1, 99)
      .setSliderMode(Slider.FLEXIBLE);
    cp5.addSlider("noiseX2")
      .setGroup("noise")
      .setValue(noiseX2)
      .plugTo(this)
      .setPosition(20, 60)
      .setRange(-5, 5)
      .setSliderMode(Slider.FLEXIBLE);
    cp5.addSlider("noiseY1")
      .setGroup("noise")
      .setValue(noiseY1)
      .plugTo(this)
      .setPosition(20, 75)
      .setRange(1, 99)
      .setSliderMode(Slider.FLEXIBLE);
    cp5.addSlider("noiseY2")
      .setGroup("noise")
      .setValue(noiseY2)
      .plugTo(this)
      .setPosition(20, 90)
      .setRange(-5, 5)
      .setSliderMode(Slider.FLEXIBLE);
    cp5.addScrollableList("dynamicMode")
      .setGroup("noise")
      .plugTo(this)
      .setPosition(20, 105)
      .addItem("Smooth", 0)
      .addItem("Rough", 1)
      .addItem("None", 2)
      .setValue(dynamicMode);
    cp5.addSlider("timeInterval")
      .setLabel("Interval")
      .setGroup("noise")
      .setPosition(20, 145)
      .plugTo(this)
      .setValue(timeInterval)
      .setRange(100, 10000);
    cp5.addGroup("points")
      .setTab("Flows")
      .setPosition(200, 30)
      .setSize(200, 40);
    cp5.addSlider("pCount")
      .setGroup("points")
      .plugTo(this)
      .setValue(points.size())
      .setRange(1, 5)
      .setPosition(20, 5)
      .setSliderMode(Slider.FLEXIBLE);
    for (Point p : points) {
      String pLabelX = "Point" + (points.indexOf(p)+1) + "x";
      String pLabelY = "Point" + (points.indexOf(p)+1) + "y";
      String pLabelType = "Point" + (points.indexOf(p)+1) + "Type";
      cp5.addSlider(pLabelX).setValue(p.pos.x).plugTo(this).setGroup("points").setPosition(20, 20 + points.indexOf(p)*75).setRange(0, width).setSliderMode(Slider.FLEXIBLE);
      cp5.addSlider(pLabelY).setValue(p.pos.y).plugTo(this).setGroup("points").setPosition(20, 30 + points.indexOf(p)*75).setRange(0, height).setSliderMode(Slider.FLEXIBLE);
      cp5.addScrollableList(pLabelType).plugTo(this).setGroup("points").setPosition(20, 40 + points.indexOf(p)*75).addItem("Attractor", 0).addItem("Repulsor", 1).addItem("Orbit", 2).setValue(p.type);
    }
    cp5.addGroup("uniformColors")
      .setPosition(0, height-230)
      .setTab("Flows");
    cp5.addColorWheel("uniform")
      .plugTo(this)
      .setRGB(uniform)
      .setPosition(0, 0)
      .setGroup("uniformColors");
    cp5.addGroup("HorizontalGradient")
      .setTab("Flows")
      .setPosition(0, height-230);
    cp5.addColorWheel("xGradStart")
      .plugTo(this)
      .setRGB(xGradStart)
      .setGroup("HorizontalGradient")
      .setPosition(0, 0);
    cp5.addColorWheel("xGradEnd")
      .plugTo(this)
      .setRGB(xGradEnd)
      .setGroup("HorizontalGradient")
      .setPosition(200, 0);
    cp5.addGroup("VerticalGradient")
      .setTab("Flows")
      .setPosition(0, height-230);
    cp5.addColorWheel("yGradStart")
      .plugTo(this)
      .setRGB(yGradStart)
      .setGroup("VerticalGradient")
      .setPosition(0, 0);
    cp5.addColorWheel("yGradEnd")
      .plugTo(this)
      .setRGB(yGradEnd)
      .setGroup("VerticalGradient")
      .setPosition(200, 0);
    cp5.addColorWheel("back")
      .setTab("Flows")
      .setPosition(width-200, height-200)
      .setRGB(back)
      .plugTo(this);
    cp5.addSlider("ba")
      .plugTo(this)
      .setValue(20)
      .setPosition(width-200, height-230)
      .setRange(0, 255)
      .setSize(200, 30)
      .setSliderMode(Slider.FLEXIBLE)
      .setTab("Flows");
    cp5.addGroup("dExtra")
      .setPosition(400, 30)
      .setSize(200, 40)
      .setBackgroundHeight(height/2)
      .setTab("Flows")
      .setLabel("extra");
    cp5.addBang("randomize")
      .setGroup("dExtra")
      .setPosition(20, 5)
      .plugTo(this)
      .getCaptionLabel()
      .align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER)
      .setPaddingX(5);
    cp5.addBang("drifterCol")
      .setGroup("dExtra")
      .setPosition(20, 30)
      .setLabel("Reset")
      .getCaptionLabel()
      .align(ControlP5.RIGHT_OUTSIDE, ControlP5.CENTER)
      .setPaddingX(5);
    cp5.getTab("Flows").bringToFront();
  }

  void controlEvent(ControlEvent ce) {
    if (ce.getName() == "drifterAmount") {
      drifterCount = (int) ce.getController().getValue() * 100;
    }
    if (ce.getName().length() > 5 && ce.getName().contains("Point")) {
      if (ce.getName().charAt(6)=='x') {
        int id = Integer.parseInt(Character.toString(ce.getName().charAt(5))) - 1;
        points.get(id).pos.x = cp5.get(Slider.class, ce.getName()).getValue();
      } else if (ce.getName().charAt(6)=='y') {
        int id = Integer.parseInt(Character.toString(ce.getName().charAt(5))) - 1;
        points.get(id).pos.y = cp5.get(Slider.class, ce.getName()).getValue();
      } else {
        int id = Integer.parseInt(Character.toString(ce.getName().charAt(5))) - 1;
        points.get(id).type = (int) cp5.get(ScrollableList.class, ce.getName()).getValue();
      }
    }
  }

  void update() {
    if (points.size() > pCount) {
      for (Point p : points) {
        String pLabelX = "Point" + (points.indexOf(p)+1) + "x";
        String pLabelY = "Point" + (points.indexOf(p)+1) + "y";
        String pLabelType = "Point" + (points.indexOf(p)+1) + "Type";
        cp5.remove(pLabelX);
        cp5.remove(pLabelY);
        cp5.remove(pLabelType);
      }
      for (int i = 0; i < points.size() - pCount; i++) {
        points.remove((int)random(points.size()));
      }
      for (Point p : points) {
        String pLabelX = "Point" + (points.indexOf(p)+1) + "x";
        String pLabelY = "Point" + (points.indexOf(p)+1) + "y";
        String pLabelType = "Point" + (points.indexOf(p)+1) + "Type";
        cp5.remove(pLabelX);
        cp5.remove(pLabelY);
        cp5.remove(pLabelType);
        cp5.addSlider(pLabelX).setValue(p.pos.x).plugTo(this).setGroup("points").setPosition(20, 60 + points.indexOf(p)*75).setRange(0, width).setSliderMode(Slider.FLEXIBLE);
        cp5.addSlider(pLabelY).setValue(p.pos.y).plugTo(this).setGroup("points").setPosition(20, 70 + points.indexOf(p)*75).setRange(0, height).setSliderMode(Slider.FLEXIBLE);
        cp5.addScrollableList(pLabelType).plugTo(this).setGroup("points").setPosition(20, 80 + points.indexOf(p)*75).addItem("Attractor", 0).addItem("Repulsor", 1).addItem("Orbit", 2).setValue(p.type);
      }
    } else if (points.size() < pCount) {
      for (Point p : points) {
        String pLabelX = "Point" + (points.indexOf(p)+1) + "x";
        String pLabelY = "Point" + (points.indexOf(p)+1) + "y";
        String pLabelType = "Point" + (points.indexOf(p)+1) + "Type";
        cp5.remove(pLabelX);
        cp5.remove(pLabelY);
        cp5.remove(pLabelType);
      }
      points.add(new Point((int) random(3)));
      for (Point p : points) {
        String pLabelX = "Point" + (points.indexOf(p)+1) + "x";
        String pLabelY = "Point" + (points.indexOf(p)+1) + "y";
        String pLabelType = "Point" + (points.indexOf(p)+1) + "Type";
        cp5.remove(pLabelX);
        cp5.remove(pLabelY);
        cp5.remove(pLabelType);
        cp5.addSlider(pLabelX).setValue(p.pos.x).plugTo(this).setGroup("points").setPosition(20, 60 + points.indexOf(p)*75).setRange(0, width).setSliderMode(Slider.FLEXIBLE);
        cp5.addSlider(pLabelY).setValue(p.pos.y).plugTo(this).setGroup("points").setPosition(20, 70 + points.indexOf(p)*75).setRange(0, height).setSliderMode(Slider.FLEXIBLE);
        cp5.addScrollableList(pLabelType).plugTo(this).setGroup("points").setPosition(20, 80 + points.indexOf(p)*75).addItem("Attractor", 0).addItem("Repulsor", 1).addItem("Orbit", 2).setValue(p.type);
      }
    }
    if (showPoints) {
      textSize(10);
      stroke(255-red(back), 255-green(back), 255-blue(back), 255);
      fill(255-red(back), 255-green(back), 255-blue(back), 255);
      for (Point p : points) {
        text(points.indexOf(p)+1, p.pos.x-10, p.pos.y);
        p.display();
      }
    }
    if (drifterCount > flows.size()) {
      for (int i = 0; i < drifterCount - flows.size(); i++) {
        flows.add(new Drifter(this));
      }
    } else if (drifterCount < flows.size()) {
      for (int i = 0; i < flows.size() - drifterCount; i++) {
        flows.remove((int)random(flows.size()));
      }
    }
    noStroke();
    fill(back, ba);
    rectMode(CORNER);
    rect(0, 0, width, height);
    for (Drifter flow : flows) {
      flow.update();
    }
  }

  void randomize() {
    //fieldGenSystem = (int) random(2);
    cp5.get(ScrollableList.class, "fieldGenSystem").setValue(fieldGenSystem);
    resolution = (int) random(1, 20);
    cp5.get(Slider.class, "resolution").setValue(resolution);
    drifterSize = random(1, 15);
    cp5.get(Slider.class, "drifterSize").setValue(drifterSize);
    velLimit = (int) random(1, 10);
    cp5.get(Slider.class, "velLimit").setValue(velLimit);
    dynamicMode = (int) random(3);
    cp5.get(ScrollableList.class, "dynamicMode").setValue(dynamicMode);
    timeInterval = (int) random(5000);
    cp5.get(Slider.class, "timeInterval").setValue(timeInterval);
    colorMode = (int) random(4);
    cp5.get(ScrollableList.class, "colorMode").setValue(colorMode);
    uniform = color(random(50, 255), random(50, 255), random(50, 255));
    cp5.get(ColorWheel.class, "uniform").setRGB(uniform);
    xGradStart = color(random(255), random(255), random(255));
    cp5.get(ColorWheel.class, "xGradStart").setRGB(xGradStart);
    xGradEnd = color(random(255), random(255), random(255));
    cp5.get(ColorWheel.class, "xGradEnd").setRGB(xGradEnd);
    yGradStart = color(random(255), random(255), random(255));
    cp5.get(ColorWheel.class, "yGradStart").setRGB(yGradStart);
    yGradEnd = color(random(255), random(255), random(255));
    cp5.get(ColorWheel.class, "yGradEnd").setRGB(yGradEnd);
    noiseFullCircle = random(1) > 0.5;
    cp5.get(Toggle.class, "noiseFullCircle").setValue(noiseFullCircle? 1:0);
    noiseX1 = (int) random(100);
    cp5.get(Slider.class, "noiseX1").setValue(noiseX1);
    noiseX2 = (int) random(10);
    cp5.get(Slider.class, "noiseX2").setValue(noiseX2);
    noiseY1 = (int) random(100);
    cp5.get(Slider.class, "noiseY1").setValue(noiseY1);
    noiseY2 = (int) random(10);
    cp5.get(Slider.class, "noiseY2").setValue(noiseY2);
    for (Point p : points) {
      String pLabelX = "Point" + (points.indexOf(p)+1) + "x";
      String pLabelY = "Point" + (points.indexOf(p)+1) + "y";
      String pLabelType = "Point" + (points.indexOf(p)+1) + "Type";
      cp5.remove(pLabelX);
      cp5.remove(pLabelY);
      cp5.remove(pLabelType);
    }
    points = new ArrayList();
    points.add(new Point((int)random(3), width/2, height/2));
    for (int i2 = 0; i2 < random(4); i2++) {
      points.add(new Point((int) random(3)));
    }
    for (Point p : points) {
      String pLabelX = "Point" + (points.indexOf(p)+1) + "x";
      String pLabelY = "Point" + (points.indexOf(p)+1) + "y";
      String pLabelType = "Point" + (points.indexOf(p)+1) + "Type";
      cp5.remove(pLabelX);
      cp5.remove(pLabelY);
      cp5.remove(pLabelType);
      cp5.addSlider(pLabelX).setValue(p.pos.x).plugTo(this).setGroup("points").setPosition(20, 60 + points.indexOf(p)*75).setRange(0, width).setSliderMode(Slider.FLEXIBLE);
      cp5.addSlider(pLabelY).setValue(p.pos.y).plugTo(this).setGroup("points").setPosition(20, 70 + points.indexOf(p)*75).setRange(0, height).setSliderMode(Slider.FLEXIBLE);
      cp5.addScrollableList(pLabelType).plugTo(this).setGroup("points").setPosition(20, 80 + points.indexOf(p)*75).addItem("Attractor", 0).addItem("Repulsor", 1).addItem("Orbit", 2).setValue(p.type);
    }
    cp5.get(Slider.class, "pCount").setValue(points.size());
    for (Drifter flow : flows) {
      flow.col = color(random(255), random(255), random(255));
    }
    if (fieldGenSystem == 0) {
      showPoints = false;
    } else {
      showPoints = true;
    }
  }
}
