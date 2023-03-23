import React from "react";
import Sketch from "react-p5";
import { Star } from "./classes";
	let points = [];
export default (props) => {
	const setup = (p5, canvasParentRef) => {
		p5.createCanvas(p5.windowWidth, p5.windowHeight).parent(canvasParentRef);
		for (let i = 0; i < 1000; i++){
			points.push(new Star(p5));
		}
		
	};

	const draw = (p5) => {
		p5.background(0);
		for (let s of points){
			s.twinkle();
		}
		if (points[100].twinkling){
			console.log(points[100].growth + " and "+ points[100].timing);
		}
	};

	const windowResized = (p5) => {
		p5.resizeCanvas(p5.windowWidth,p5.windowHeight);
	}

	return <Sketch setup={setup} draw={draw} windowResized={windowResized}/>;
};