class Drifter {
  PVector acc = new PVector(0, 0);
  PVector vel = new PVector(0, 0);
  color col = color(random(255), random(255), random(255));
  PGraphics platform;
  PVector loc;
  DrifterCol parent;

  Drifter(DrifterCol par) {
    platform = par.plat;
    loc = new PVector(random(width), random(height));
    parent = par;
  }

  void update() {
    float theta;
    if (parent.fieldGenSystem == 0) {
      if (parent.dynamicMode==0) {
        if (parent.noiseFullCircle) {
          theta = map(noise(floor(loc.x / parent.resolution) * parent.noiseX1 * pow(10, 3*parent.noiseX2), floor(loc.y / parent.resolution) * parent.noiseY1 * pow(10, 3*parent.noiseY2), (millis() / (float)1000)), 0, 1, -TWO_PI, TWO_PI);
        } else {
          theta = map(noise(floor(loc.x / parent.resolution) * parent.noiseX1 * pow(10, 3*parent.noiseX2), floor(loc.y / parent.resolution) * parent.noiseY1 * pow(10, 3*parent.noiseY2), (millis() / (float)1000)), 0, 1, 0, TWO_PI);
        }
      } else if (parent.dynamicMode==1) {
        if (parent.noiseFullCircle) {
          theta = map(noise(floor(loc.x / parent.resolution) * parent.noiseX1 * pow(10, 3*parent.noiseX2), floor(loc.y / parent.resolution) * parent.noiseY1 * pow(10, 3*parent.noiseY2), floor((millis() / (float)parent.timeInterval))), 0, 1, -TWO_PI, TWO_PI);
        } else {
          theta = map(noise(floor(loc.x / parent.resolution) * parent.noiseX1 * pow(10, 3*parent.noiseX2), floor(loc.y / parent.resolution) * parent.noiseY1 * pow(10, 3*parent.noiseY2), floor((millis() / (float)parent.timeInterval))), 0, 1, 0, TWO_PI);
        }
      } else {
        if (parent.noiseFullCircle) {
          theta = map(noise(floor(loc.x / parent.resolution) * parent.noiseX1 * pow(10, 3*parent.noiseX2), floor(loc.y / parent.resolution) * parent.noiseY1 * pow(10, 3*parent.noiseY2)), 0, 1, -TWO_PI, TWO_PI);
        } else {
          theta = map(noise(floor(loc.x / parent.resolution) * parent.noiseX1 * pow(10, 3*parent.noiseX2), floor(loc.y / parent.resolution) * parent.noiseY1 * pow(10, 3*parent.noiseY2)), 0, 1, 0, TWO_PI);
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
      for (Point point : parent.points) {
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
        PVector desired = PVector.sub(point.pos, loc);
        if (point.type == 0) {
          float factor = map(dist(point.pos.x, point.pos.y, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
          desired.normalize();
          desired.mult(factor);
          acc.add(desired);
        } else if (point.type == 1) {
          float factor = map(dist(point.pos.x, point.pos.y, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
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
          PVector orbDesired = PVector.sub(target, loc);
          float factor = map(dist(point.pos.x, point.pos.y, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
          orbDesired.normalize();
          orbDesired.mult(factor);
          acc.add(orbDesired);
        }
        if (loc.x > width || loc.x < 0 || loc.y > height || loc.y < 0) {
          loc = new PVector(random(width), random(height));
        }
      }
      if (parent.includeMouse) {
        PVector mouse = PVector.sub(new PVector(mouseX, mouseY), loc);
        if (parent.mouseType == 0) {
          float factor = map(dist(mouseX, mouseY, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
          mouse.normalize();
          mouse.mult(factor);
          acc.add(mouse);
        } else if (parent.mouseType == 1) {
          float factor = map(dist(mouseX, mouseY, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
          mouse.normalize();
          mouse.mult(factor);
          acc.sub(mouse);
        } else {
          float orbTheta = atan((mouseY - loc.y) / (mouseX - loc.x));
          float r = dist(mouseX, mouseY, loc.x, loc.y);
          PVector target;
          if (mouseX >= loc.x) {
            target = new PVector(-r * cos(orbTheta + 0.01f) + mouseX, -r * sin(orbTheta + 0.01f) + mouseY);
          } else {
            target = new PVector(r * cos(orbTheta + 0.01f) + mouseX, r * sin(orbTheta + 0.01f) + mouseY);
          }
          PVector orbDesired = PVector.sub(target, loc);
          float factor = map(dist(mouseX, mouseY, loc.x, loc.y), 0, dist(0, 0, width, height), 5, 0);
          orbDesired.normalize();
          orbDesired.mult(factor);
          acc.add(orbDesired);
        }
        if (dist(mouseX, mouseY, loc.x, loc.y) <= 10) {
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
      }
    }
    vel.add(acc);
    vel.limit(parent.velLimit);
    loc.add(vel);
    acc.mult(0);
    strokeWeight(parent.drifterSize);
    if (parent.colorMode == 0) {
      stroke(col);
    } else if (parent.colorMode == 1) {
      stroke(lerpColor(color(parent.xGradStart), color(parent.xGradEnd), map(loc.x, 0, width, 0, 1)), 255);
    } else if (parent.colorMode == 2) {
      stroke(lerpColor(color(parent.yGradStart), color(parent.yGradEnd), map(loc.y, 0, height, 0, 1)), 255);
    } else if (parent.colorMode == 3) {
      int c1 = color(lerpColor(color(parent.xGradStart), color(parent.xGradEnd), map(loc.x, 0, width, 0, 1)));
      int c2 = color(lerpColor(color(parent.yGradStart), color(parent.yGradEnd), map(loc.y, 0, height, 0, 1)));
      stroke(lerpColor(c1, c2, 0.5f), 255);
    } else if (parent.colorMode == 4) {
      stroke(parent.uniform, 255);
    } else {
      stroke(col, 255);
    }
    point(loc.x, loc.y);
  }
}
