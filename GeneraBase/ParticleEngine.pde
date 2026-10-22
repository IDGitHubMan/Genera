class ParticleEngine{
  color c1;
  color c2;
  float lifeSpan;
  float maxSpeed = 4.0;
  Boundary b = Boundary.WRAP;


  private class Particle{
    color col;
    PVector pos;
    PVector vel;
    PVector acc;
    float lifeTime;

    Particle(){
      col = color(random(255),random(255),random(255));
      pos = new PVector(random(width),random(height));
      vel = new PVector(0,0);
      acc = new PVector(0,0);
      lifeTime = 0;
    }

    void applyForce(PVector force){
      PVector f = force.copy();
      acc.add(f);
    }

    void update(){
      vel.add(acc);
      pos.add(vel);
      acc.mult(0);
    }
  }

  Particle[] particles = new Particle[1000];

  ParticleEngine(){
    for (int i =0; i < particles.length; i++){
      particles[i] = new Particle();
    }
  }
  void update(){
    for (Particle p:particles){
      p.applyForce(PVector.random2D().mult(maxSpeed));
      p.vel.limit(maxSpeed);
      p.update();
      if (p.pos.x < 0 || p.pos.x > width){
        switch (b){
          case WRAP:
            p.pos.x = ((p.pos.x % width) + width) % width;
            break;
          case BOUNCE: 
            p.pos.x = constrain(p.pos.x,0,width);
            p.vel.mult(-1);
            break;
          case RESPAWN: 
            p.pos = PVector.random2D();
            break;
        }
      }
      if (p.pos.y < 0 || p.pos.y > height){
        switch (b){
          case WRAP:
            p.pos.y = ((p.pos.y % height) + height) % height;
            break;
          case BOUNCE: 
            p.pos.y = constrain(p.pos.y,0,height);
            p.vel.mult(-1);
            break;
          case RESPAWN: 
            p.pos = PVector.random2D();
            break;
        }
      }
    }
  }
  void render(){
    strokeWeight(5);
    for (Particle p:particles){
      stroke(p.col);
      point(p.pos.x,p.pos.y);
    }
  }
}
