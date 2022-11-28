class Node {
  PVector vel = new PVector(random(-1, 1), random(-1, 1));
  color nodeCol = color(random(255), random(255), random(255));
  float range = random(0, 1);
  PGraphics platform;
  PVector loc;
  Graph parent;

  Node(Graph p) {
    parent = p;
    if (parent.colorUniformity) {
      nodeCol = parent.allCol;
    }
    loc = new PVector(random(width), random(height));
  }

  Node(Graph p, PVector start) {
    parent = p;
    loc = start;
    if (parent.colorUniformity) {
      nodeCol = parent.allCol;
    }
  }

  Node(Graph p, PVector start, PVector drift) {
    parent = p;
    loc = start;
    vel = drift;
    if (parent.colorUniformity) {
      nodeCol = parent.allCol;
    }
  }

  public void update() {
    vel.normalize();
    float actRange = map(range, 0, 1, parent.minRange, parent.maxRange);
    PVector drift = new PVector(map(vel.x, -1, 1, -parent.driftLimit, parent.driftLimit), map(vel.y, -1, 1, -parent.driftLimit, parent.driftLimit));
    loc.x = constrain(loc.x + drift.x, 0, width);
    loc.y = constrain(loc.y + drift.y, 0, height);
    if (parent.edgeBehavior == 0) {
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
    if (parent.displayNode) {
      if (parent.colorUniformity) {
        stroke(parent.allCol);
        fill(parent.allCol);
      } else {
        stroke(nodeCol);
        fill(nodeCol);
      }
      ellipse(loc.x, loc.y, 10, 10);
    }
    if (parent.displayRange) {
      noFill();
      strokeWeight(2);
      if (parent.colorUniformity) {
        stroke(parent.allCol);
      } else {
        stroke(nodeCol);
      }
      if (parent.rangeUniformity) {
        ellipse(loc.x, loc.y, parent.uniRange * 2, parent.uniRange * 2);
      } else {
        ellipse(loc.x, loc.y, actRange * 2, actRange * 2);
      }
    }
  }
}
