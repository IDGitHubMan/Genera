PVector[] pos;
ParticleEngine engine;
void setup(){
  background(0);
  fullScreen();
  engine = new ParticleEngine();
}

void draw(){
  noStroke();
  fill(0,50);
  rect(0,0,width,height);
  engine.update();
  engine.render();
}
