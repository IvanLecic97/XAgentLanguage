import { React, useEffect, useState } from "react";

function ViewACLMessages() {
  const username = localStorage.getItem("username");
  const socketUrl = "ws://ivan:8080/project-war/ws/:" + username;
  const [messages, setMessages] = useState([]);

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
      "http://localhost:8080/project-war/rest/agentRest/getAgentsACLMessages/" +
      username;
    try {
      const response = await fetch(url);
      const json = await response.json();
      setMessages(json);
      console.log(json);
    } catch (error) {
      console.log("error", error);
    }
  };

  useEffect(() => {
    initWebSocket();
    loadMessages();
  }, []);

  return (
    <div>
      <section>
        {messages.map((e) => (
          <ul key={Math.floor(Math.random() * 100)}>
            <li>Performative : {e.performative}</li>
            <li>Content : {e.content}</li>
          </ul>
        ))}
      </section>
    </div>
  );
}

export default ViewACLMessages;
