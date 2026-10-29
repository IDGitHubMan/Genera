import { useEffect, useRef } from "react";
import ParticleEngine from "../../../engine/ParticleEngine";
export default function SketchCanvas({}) {
  const canvasRef = useRef<HTMLCanvasElement>(null);
  const animationFrameRef = useRef<number | null>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    let width = canvas.width;
    let height = canvas.height;
    const ctx = canvas.getContext("2d");
    if (!ctx) return;
    const resize = () => {
      width = canvas.width = window.innerWidth;
      height = canvas.height = window.innerHeight;
      console.log(width, p.width);
      console.log(height, p.height);
      p.width = width;
      p.height = height;
    };
    const p = new ParticleEngine(width, height, ctx);
    resize();

    const animate = () => {
      p.update();
      p.render();
      animationFrameRef.current = requestAnimationFrame(animate);
    };
    window.addEventListener("resize", resize);
    animate();
    return () => {
      window.removeEventListener("resize", resize);
      if (animationFrameRef.current !== null) {
        cancelAnimationFrame(animationFrameRef.current);
      }
    };
  }, []);
  return <canvas ref={canvasRef}></canvas>;
}
