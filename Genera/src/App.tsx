import { useState } from "react";
import "./App.css";
import SketchCanvas from "./components/SketchCanvas";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <SketchCanvas />
    </>
  );
}

export default App;
