enum Boundary {
  Bounce,
  Wrap,
  Respawn,
}

class Vector {
  public x: number;
  public y: number;
  constructor(x: number, y: number) {
    this.x = x;
    this.y = y;
  }

  add(v: Vector) {}
  sub(v: Vector) {}
  mult(s: number) {}
}

class ParticleEngine {
  public c1: number[];
  public c2: number[];
  public maxSpeed: number;
  public b: Boundary;
  constructor() {
    this.c1 = [255, 255, 255];
    this.c2 = [0, 0, 0];
    this.maxSpeed = 4.0;
    this.b = Boundary.Wrap;
  }
  Particle = class {
    public color: number[];
    public pos: number[];
    public vel: number[];
    public acc: number[];
    constructor(public superThis: ParticleEngine) {
      this.color = [255, 255, 255];
      this.pos = [0, 0];
      this.vel = [0, 0];
      this.acc = [0, 0];
    }

    applyForce(force: number[]) {
      let f = [...force];
      this.acc[0] += f[0];
      this.acc[1] += f[1];
    }

    update() {
      this.vel[0] += this.acc[0];
    }
  };
}
