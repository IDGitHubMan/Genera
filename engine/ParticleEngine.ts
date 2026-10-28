import Vector from "./Vector";

export default class ParticleEngine {
  public c1: number[];
  public c2: number[];
  public maxSpeed: number;
  public boundary: "WRAP" | "BOUNCE" | "RESPAWN";
  public particles: Particle[];

  constructor() {
    this.c1 = [255, 255, 255];
    this.c2 = [0, 0, 0];
    this.maxSpeed = 4.0;
    this.particles = [];
    this.boundary = "WRAP";
    for (let i = 0; i < 100; i++) {
      this.particles.push(new Particle(this));
    }
  }

  update() {
    for (let p of this.particles) {
      let angle = Math.random() * Math.PI * 2;
      p.applyForce(new Vector(Math.acos(angle), Math.asin(angle)));
      p.update();
    }
  }
}

class Particle {
  public color: number[];
  public pos: Vector;
  public vel: Vector;
  public acc: Vector;
  public engine: ParticleEngine;
  constructor(e: ParticleEngine) {
    this.color = [255, 255, 255];
    this.pos = new Vector(0, 0);
    this.vel = new Vector(0, 0);
    this.acc = new Vector(0, 0);
    this.engine = e;
  }

  applyForce(force: Vector) {
    let f = force.copy();
    this.acc.add(f);
  }

  update() {
    this.vel.add(this.acc);
    this.vel.limit(this.engine.maxSpeed);
    this.pos.add(this.vel);
    this.acc.mult(0);
  }
}
