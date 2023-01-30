import React from "react";
import { Link } from "react-router-dom";
import "./NavBar.css";

function NavBar() {
  return (
    <nav className="navbar">
      <ul>
        <li>
          <Link to="/register">Register</Link>
        </li>
        <li>
          <Link to="/login">Login</Link>
        </li>
        <li>
          <Link to="/send-message">Send Message</Link>
        </li>
        <li>
          <Link to="/view-messages">View Messages</Link>
        </li>
        <li>
          <Link to="/realEstateModel">Real estate</Link>
        </li>
        <li>
          <Link to="/view-acl-messages">ACL Messages</Link>
        </li>
      </ul>
    </nav>
  );
}

export default NavBar;
