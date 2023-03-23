import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import NotFound from "./Components/NotFound";

function App() {
  return (
    <main>
      <BrowserRouter>
        <Routes>
          <Route path="/*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </main>
  );
}

export default App;
