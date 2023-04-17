import React from "react";
import Sketch from "react-p5";
import { FlowSet, Graph, NoiseWave } from "./classes";
let g;
let f;
let r;
let current = 0;
let time;
export const Preview = (props) => {
  const setup = (p5, canvasParentRef) => {
    p5.createCanvas(p5.windowWidth, p5.windowHeight).parent(canvasParentRef);
    g = new Graph(p5);
    f = new FlowSet(p5);
    r = new NoiseWave(p5);
    time = p5.millis();
  };

  const draw = (p5) => {
    if (p5.millis() - time >= 5000) {
      f.randomize();
      time = p5.millis();
      current += 1;
    }
    if (current >= 3) {
      current = 0;
    }
    if (current === 0) {
      g.update();
    } else if (current === 1) {
      f.update();
    } else if (current === 2) {
      r.update();
    }
  };

  const windowResized = (p5) => {
    p5.resizeCanvas(p5.windowWidth, p5.windowHeight);
  };

  return <Sketch setup={setup} draw={draw} windowResized={windowResized} />;
};
export default Preview;
