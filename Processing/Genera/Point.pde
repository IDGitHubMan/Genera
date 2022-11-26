class Point {
  int type;
  PVector pos;

  Point(int t) {
    type = t;
    pos = new PVector(random(width), random(height));
  }

  Point(int t, int x, int y) {
    type = t;
    pos = new PVector(x, y);
  }
  
  void display(){
    strokeWeight(3);
    point(pos.x,pos.y);
  }
}
