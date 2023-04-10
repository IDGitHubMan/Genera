import React from "react";
import Sketch from "react-p5";
import { FlowSet, Graph } from "./classes";
let g;
let f;
let current = 0;
export default (props) => {
  const setup = (p5, canvasParentRef) => {
    p5.createCanvas(p5.windowWidth, 300).parent(canvasParentRef);
    g = new Graph(p5);
    f = new FlowSet(p5);
  };

  const draw = (p5) => {
    console.log(current);
    if (p5.frameCount % 200 == 0) {
      current += 1;
      f = new FlowSet(p5);
    }
    if (current >= 2) {
      current = 0;
    }
    if (current == 0) {
      g.update();
    } else {
      f.update();
    }
  };

  const windowResized = (p5) => {
    p5.resizeCanvas(p5.windowWidth, 300);
  };

  return <Sketch setup={setup} draw={draw} windowResized={windowResized} />;
};
