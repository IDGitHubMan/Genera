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

  BrownianMove() {
    for (let p of this.particles) {
      let angle = Math.random() * Math.PI * 2;
      p.applyForce(new Vector(Math.cos(angle), Math.sin(angle)));
    }
  }

  update() {
    this.BrownianMove();
    for (let p of this.particles) {
      p.update();
    }
  }

  render(ctx: CanvasRenderingContext2D, width: number, height: number) {
    ctx.fillStyle = "rgb(0,0,0,0.05)";
    ctx.fillRect(0, 0, width, height);
    for (let p of this.particles) {
      ctx.beginPath();
      ctx.arc(p.pos.x, p.pos.y, 1, 0, Math.PI * 2);
      ctx.fillStyle = `rgb(${p.color[0]},${p.color[1]},${p.color[2]})`;
      ctx.fill();
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
