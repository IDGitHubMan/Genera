import controlP5.*;
import java.util.Arrays;
int running = 0;
boolean viewMode = false;
boolean explanation = false;

PVector touchStart;

ControlP5 cp5;
int sliderValue = 100;
Graph g;
DrifterCol d;


//Make the app open fullscreen. 
void settings() {
  fullScreen();
}

void setup() {
  //Object for controls
  cp5 = new ControlP5(this);
  
  //Object for managing Flows
  d = new DrifterCol(cp5);
  
  //Object for managing Nodes
  g = new Graph(cp5);
  
  //Add tab that displays digest of how each system works.
  cp5.getTab("default").activateEvent(true).setLabel("Explanation");
}

//Function to reset the Nodes
void graph() {
  g = new Graph(cp5);
}

//function to reset Flows
void drifterCol() {
  d = new DrifterCol(cp5);
}

//Draw loop to 
void draw() {
  if (viewMode) {
    cp5.hide();
  } else {
    cp5.show();
  }
  if (running == 0) {
    g.update();
    if (explanation) {
      fill(0, 128);
      noStroke();
      rectMode(CORNER);
      rect(0, 0, width, height);
      fill(255, 255);
      textSize(30);
      textAlign(CENTER, CENTER);
      rectMode(CENTER);
      text("Nodes works by creating a set of objects each with a range, location, and a motion defined by a vector. As they move according to this vector, the system checks if any node falls within the range of another. If it does, draw a line. If the relation goes both ways, add a sparkle.", width/2, height/2, width/2, height/2);
    }
  } else if (running == 1){
    if (!cp5.isMouseOver()){
      d.includeMouse = mousePressed;
    }
    d.update();
    if (explanation) {
      fill(0, 128);
      noStroke();
      rectMode(CORNER);
      rect(0, 0, width, height);
      fill(255, 255);
      textSize(30);
      textAlign(CENTER, CENTER);
      rectMode(CENTER);
      text("Flows sets up multiple objects within a flow field (a grid where each cell is a direction for movement), that can be defined by Perlin noise or a set of points", width/2, height/2, width/2, height/2);
    }
  }
  if (g.colorUniformity) {
    cp5.getController("allCol").show();
  } else {
    cp5.getController("allCol").hide();
  }

  if (g.rangeUniformity) {
    cp5.getController("sizeRange").hide();
    cp5.getController("uniRange").show();
  } else {
    cp5.getController("sizeRange").show();
    cp5.getController("uniRange").hide();
  }
  if (g.sparkStyle == 0) {
    cp5.getController("sparkCol").show();
  } else {
    cp5.getController("sparkCol").hide();
  }
  if (d.fieldGenSystem == 0) {
    cp5.get(Group.class, "noise").show();
    cp5.get(Group.class, "points").hide();
    cp5.get(Group.class,"touches").hide();
  } else {
    cp5.get(Group.class, "noise").hide();
    cp5.get(Group.class, "points").show();
    cp5.get(Group.class,"touches").show();
  }
  if (d.colorMode == 0) {
    cp5.get(Group.class, "HorizontalGradient").hide();
    cp5.get(Group.class, "uniformColors").hide();
    cp5.get(Group.class, "VerticalGradient").hide();
  } else if (d.colorMode == 1) {
    cp5.get(Group.class, "HorizontalGradient").show();
    cp5.get(Group.class, "uniformColors").hide();
    cp5.get(Group.class, "VerticalGradient").hide();
  } else if (d.colorMode == 2) {
    cp5.get(Group.class, "HorizontalGradient").hide();
    cp5.get(Group.class, "uniformColors").hide();
    cp5.get(Group.class, "VerticalGradient").setPosition(0, height-230).show();
  } else if (d.colorMode == 3) {
    cp5.get(Group.class, "HorizontalGradient").show();
    cp5.get(Group.class, "uniformColors").hide();
    cp5.get(Group.class, "VerticalGradient").setPosition(400, height-230).show();
  } else if (d.colorMode == 4) {
    cp5.get(Group.class, "HorizontalGradient").hide();
    cp5.get(Group.class, "uniformColors").show();
    cp5.get(Group.class, "VerticalGradient").hide();
  }
}

void keyPressed() {
  viewMode = !viewMode;
  d.showPoints = !d.showPoints;
}

void controlEvent(ControlEvent theControlEvent) {
  if (theControlEvent.getName()=="NODES") {
    running = 0;
    explanation = false;
  }
  if (theControlEvent.getName()=="Flows") {
    running = 1;
    explanation = false;
  }
  if (theControlEvent.getName()=="default") {
    explanation = true;
  }
}

void mousePressed() {
  if (!cp5.isMouseOver()) {
    if (running == 0) {
      if (g.touchBehavior == 0) {
        g.nodeCount += 1;
        g.nodes.add(new Node(g, new PVector(mouseX, mouseY)));
        cp5.get(Slider.class, "nodeCount").setValue(g.nodeCount);
      } else if (g.touchBehavior == 3) {
        for (Node n : g.nodes) {
          n.range = random(0, 1);
          n.vel = new PVector(random(-1, 1), random(-1, 1));
        }
      }
      touchStart = new PVector(mouseX, mouseY);
    }
  }
}

void mouseDragged() {
  if (!cp5.isMouseOver()) {
    if (running == 0) {
      if (g.touchBehavior == 1 || g.touchBehavior == 2) {
        stroke(255);
        line(touchStart.x, touchStart.y, mouseX, mouseY);
      }
    }
  }
}

void mouseReleased() {
  if (!cp5.isMouseOver()) {
    if (running == 0) {
      if (g.touchBehavior == 1) {
        PVector direction = new PVector(touchStart.x - mouseX, touchStart.y - mouseY);
        PVector last = new PVector(mouseX, mouseY);
        g.nodes.add(new Node(g, last, direction));
      } else if (g.touchBehavior == 2) {
        PVector direction = new PVector(mouseX - touchStart.x, mouseY - touchStart.y);
        g.nodes.add(new Node(g, touchStart, direction));
      }
    }
  }
}
