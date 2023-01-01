import React from "react";
import { BrowserRouter } from "react-router-dom";

import NavBar from "./ui/NavBar";
import AppRoutes from "./AppRoutes";

function App() {
  return (
    <BrowserRouter>
      <div>
        <NavBar />
        <AppRoutes />
      </div>
    </BrowserRouter>
  );
}

export default App;
