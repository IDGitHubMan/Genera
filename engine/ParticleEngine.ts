import Vector from "./Vector";

export default class ParticleEngine {
  public c1: number[];
  public c2: number[];
  public maxSpeed: number;
  public boundary: "WRAP" | "BOUNCE" | "RESPAWN";
  public particles: Particle[];
  public width: number;
  public height: number;
  public ctx: CanvasRenderingContext2D;

  constructor(w: number, h: number, ctx: CanvasRenderingContext2D) {
    this.ctx = ctx;
    ctx.fillStyle = "rgb(0,0,0)";
    ctx.fillRect(0, 0, w, h);
    this.c1 = [255, 255, 255];
    this.c2 = [0, 0, 0];
    this.maxSpeed = 4.0;
    this.particles = [];
    this.boundary = "BOUNCE";
    this.width = w;
    this.height = h;
    for (let i = 0; i < 1000; i++) {
      this.particles.push(new Particle(this));
    }
  }

  random() {
    return Math.random();
  }

  BrownianMove() {
    for (let p of this.particles) {
      let angle = this.random() * Math.PI * 2;
      p.applyForce(new Vector(Math.cos(angle), Math.sin(angle)));
    }
  }

  update() {
    this.BrownianMove();
    for (let p of this.particles) {
      p.update();
      if (p.pos.x < 0 || p.pos.x > this.width) {
        switch (this.boundary) {
          case "WRAP":
            p.pos.x = ((p.pos.x % this.width) + this.width) % this.width;
            break;
          case "BOUNCE":
            Math.min(Math.max(p.pos.x, 0), this.width);
            p.vel.x *= -1;
            break;
          case "RESPAWN":
            p.pos = new Vector(
              this.random() * this.width,
              this.random() * this.height,
            );
            break;
        }
      }
      if (p.pos.y < 0 || p.pos.y > this.height) {
        switch (this.boundary) {
          case "WRAP":
            p.pos.y = ((p.pos.y % this.height) + this.height) % this.height;
            break;
          case "BOUNCE":
            Math.min(Math.max(p.pos.y, 0), this.height);
            p.vel.y *= -1;
            break;
          case "RESPAWN":
            p.pos = new Vector(
              this.random() * this.width,
              this.random() * this.height,
            );
            break;
        }
      }
    }
  }

  render() {
    this.ctx.fillStyle = "rgba(0,0,0,0.05)";
    this.ctx.fillRect(0, 0, this.width, this.height);
    for (let p of this.particles) {
      this.ctx.beginPath();
      this.ctx.arc(p.pos.x, p.pos.y, 1, 0, Math.PI * 2);
      this.ctx.fillStyle = `rgb(${p.color[0]},${p.color[1]},${p.color[2]})`;
      this.ctx.fill();
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
    this.pos = new Vector(e.random() * e.width, e.random() * e.height);
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
