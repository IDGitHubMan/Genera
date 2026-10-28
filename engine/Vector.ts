export default class Vector {
  public x: number;
  public y: number;
  constructor(x: number, y: number) {
    this.x = x;
    this.y = y;
  }

  add(v: Vector): Vector {
    this.x += v.x;
    this.y += v.y;
    return this;
  }
  sub(v: Vector): Vector {
    this.x -= v.x;
    this.y -= v.y;
    return this;
  }
  mult(s: number): Vector {
    this.x *= s;
    this.y *= s;
    return this;
  }
  normalize(): Vector {
    let magnitude = Math.sqrt(this.x ** 2 + this.y ** 2);
    if (magnitude > 0) {
      this.x /= magnitude;
      this.y /= magnitude;
    }
    return this;
  }
  limit(s: number): Vector {
    let magnitude = this.x ** 2 + this.y ** 2;
    if (magnitude > s ** 2) {
      this.normalize().mult(s);
    }
    return this;
  }
  copy(): Vector {
    return new Vector(this.x, this.y);
  }
}
