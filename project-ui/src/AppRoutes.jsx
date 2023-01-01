import { React } from "react";
import { Route, Routes } from "react-router-dom";
import Login from "./login/Login";
import Message from "./messages/Message";
import ViewMessages from "./messages/ViewMessages";

import Register from "./register/Register";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/register" element={<Register />} />
      <Route path="/login" element={<Login />} />
      <Route path="/send-message" element={<Message />} />
      <Route path="/view-messages" element={<ViewMessages />} />
    </Routes>
  );
};

export default AppRoutes;
