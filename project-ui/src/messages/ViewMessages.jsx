import { React, useState, useEffect } from "react";
import axios from "axios";

function ViewMessages() {
  const [messages, setMessages] = useState([]);

  const [socketUrl, setSocketUrl] = useState(
    "ws://localhost:8080/project-war/ws/:" + localStorage.getItem("username")
  );

  const initWebSocket = () => {
    let connection = new WebSocket(socketUrl);
    connection.onopen = function () {
      console.log("socket is opened!");
    };

    connection.onmessage = function (event) {
      const json = JSON.parse(event.data);
      setMessages(json);
      console.log("Data :" + event.data);
    };
  };

  const loadMessages = async () => {
    const url =
      "http://localhost:8080/project-war/rest/chat/messages/" +
      localStorage.getItem("username");
    try {
      const response = await fetch(url);
      const json = await response.json();
      setMessages(json);
      console.log(json);
    } catch (error) {
      console.log("error", error);
    }
  };

  const showContent = () => {
    if (messages.length === 0) {
      return "No messages to show!";
    }
  };

  useEffect(() => {
    loadMessages();
    initWebSocket();
  }, []);

  return (
    <div>
      {messages.map((element) => (
        <ul key={element.timestamp}>
          <li>
            <label>Sender : {element.sender}</label>
            <br />
            <label>Content : {element.content}</label>
          </li>
        </ul>
      ))}
    </div>
  );
}

export default ViewMessages;
