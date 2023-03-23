export class Star {
  constructor(sketch) {
    this.p5 = sketch;
    this.loc = this.p5.createVector(this.p5.random(1), this.p5.random(1));
    this.points = this.p5.random(4, 6);
    this.rotation = this.p5.random(this.p5.TWO_PI);
    this.starType = this.p5.random(1);
    this.growth = 0;
    if (this.starType <= 0.7645) {
      this.startCol = this.p5.color(255, 204, 111);
      this.endCol = this.p5.color(255, 210, 161);
    } else if (this.starType <= 0.8855) {
      this.startCol = this.p5.color(255, 210, 161);
      this.endCol = this.p5.color(255, 244, 234);
    } else if (this.starType <= 0.9615) {
      this.startCol = this.p5.color(255, 244, 234);
      this.endCol = this.p5.color(248, 247, 255);
    } else if (this.starType <= 0.9915) {
      this.startCol = this.p5.color(248, 247, 255);
      this.endCol = this.p5.color(202, 215, 255);
    } else if (this.starType <= 0.9975) {
      this.startCol = this.p5.color(202, 215, 255);
      this.endCol = this.p5.color(170, 191, 255);
    } else {
      this.startCol = this.p5.color(170, 191, 255);
      this.endCol = this.p5.color(155, 176, 255);
    }
    this.intensity = this.p5.random(1);
    this.twinkling = false;
    this.timing = 0;
    this.rotationIncrement = this.p5.random(0.01, 0.1);
  }

  twinkle() {
    this.rotation += this.rotationIncrement;
    if (this.timing >= 200) {
      this.twinkling = false;
      this.timing = 0;
      this.rotationIncrement = this.p5.random(0.01, 0.1);
      this.growth = 0;
    }
    this.p5.strokeWeight(this.intensity);
    this.p5.stroke(
      this.p5.lerpColor(this.startCol, this.endCol, this.intensity)
    );
    this.p5.point(this.loc.x * this.p5.width, this.loc.y * this.p5.height);
    this.p5.strokeWeight(1);
    if (!this.twinkling) {
      let choice = this.p5.random(1);
      if (choice >= 0.999) {
        this.twinkling = true;
      }
    } else {
      this.p5.beginShape();
      for (let i = 0; i < this.points * 3; i++) {
        if (this.timing < 100) {
          this.growth = this.p5.map(this.timing, 0, 100, 0, 10);
        } else {
          this.growth = this.p5.map(this.timing, 100, 200, 10, 0);
        }
        let position = this.p5.map(i, 0, this.points * 3, 0, this.p5.TWO_PI);
        position += this.rotation;
        if (i % 3 === 0) {
          this.p5.vertex(
            this.p5.cos(position - 0.01) * this.intensity +
              this.loc.x * this.p5.width,
            this.p5.sin(position - 0.01) * this.intensity +
              this.loc.y * this.p5.height
          );
        } else if (i % 3 === 1) {
          this.p5.vertex(
            this.p5.cos(position) * this.growth * this.intensity +
              this.loc.x * this.p5.width,
            this.p5.sin(position) * this.growth * this.intensity +
              this.loc.y * this.p5.height
          );
        } else {
          this.p5.vertex(
            this.p5.cos(position + 0.01) * this.intensity +
              this.loc.x * this.p5.width,
            this.p5.sin(position + 0.01) * this.intensity +
              this.loc.y * this.p5.height
          );
        }
      }
      this.p5.endShape();
      this.timing += 1;
    }
  }
}
