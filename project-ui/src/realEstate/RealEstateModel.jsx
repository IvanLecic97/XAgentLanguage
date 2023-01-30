import axios from "axios";
import React, { useState, useEffect } from "react";
import { useWebSocket } from "react-use-websocket/dist/lib/use-websocket";

function RealEstateModel({ dataFromApi }) {
  const [location, setLocation] = useState("");
  const [minPrice, setMinPrice] = useState(0);
  const [maxPrice, setMaxPrice] = useState(0);
  const [type, setType] = useState("");
  const [minSize, setMinSize] = useState(0);
  const [maxSize, setMaxSize] = useState(0);
  const [username, setUsername] = useState(localStorage.getItem("username"));
  const [realEstateList, setRealEstateList] = useState([]);
  const [socketUrl, setSocketUrl] = useState(
    "ws://ivan:8080/project-war/ws/:" + localStorage.getItem("username")
  );
  const [socketUrl2, setSocketUrl2] = useState(
    "ws://ivan:8080/project-war/ws/secondEndpoint/:" +
      localStorage.getItem("username")
  );

  const locations = [
    {
      id: 0,
      name: "",
    },
    {
      id: 1,
      name: "Grbavica",
    },
    {
      id: 2,
      name: "Liman",
    },
    {
      id: 3,
      name: "Detelinara",
    },
    {
      id: 4,
      name: "Centar",
    },
    {
      id: 5,
      name: "Veternik",
    },
    {
      id: 6,
      name: "Telep",
    },
    {
      id: 7,
      name: "Klisa",
    },
    {
      id: 8,
      name: "Petrovaradin",
    },
    {
      id: 9,
      name: "Novo Naselje",
    },
  ];

  const estateTypes = [
    {
      id: 0,
      name: "",
    },
    {
      id: 1,
      name: "apartment",
    },
    {
      id: 2,
      name: "businiess",
    },
    {
      id: 3,
      name: "garage",
    },
    {
      id: 4,
      name: "house",
    },
    {
      id: 5,
      name: "villa",
    },
  ];

  const handleLocationChange = (event) => setLocation(event.target.value);
  const handleMinPriceChange = (event) => setMinPrice(event.target.value);
  const handleMaxPriceChange = (event) => setMaxPrice(event.target.value);
  const handleTypeChange = (event) => setType(event.target.value);
  const handleMinSizeChange = (event) => setMinSize(event.target.value);
  const handleMaxSizeChange = (event) => setMaxSize(event.target.value);

  const onSubmitFilter = () => {
    const url = "http://localhost:8080/project-war/rest/agentRest/requestData";

    console.log(type);
    console.log(username);

    const realEstateDTO = {
      type: type,
      minSize: minSize,
      maxSize: maxSize,
      minPrice: minPrice,
      maxPrice: maxPrice,
      location: location,
      username: username,
    };

    axios.post(url, realEstateDTO);
    //  getFilteredEstates();
  };

  const getFilteredEstates = async () => {
    const url =
      "http://localhost:8080/project-war/rest/agentRest/getFiteredRealEstate";

    try {
      const response = await fetch(url);

      const json = await response.json();

      setRealEstateList(json);

      console.log(json);
    } catch (error) {
      console.log("error", error);
    }
  };

  // useEffect(() => {}, []);

  const initWebSocket = () => {
    let connection = new WebSocket(socketUrl);
    connection.onopen = function () {
      console.log("socket is opened!");
    };

    connection.onmessage = function (event) {
      const json = JSON.parse(event.data);
      console.log("Data :" + event.data);
      console.log(json.length);
      // updateEstatesWebSocket(json);
    };
  };

  const initWebSocket2 = () => {
    let connection2 = new WebSocket(socketUrl2);
    connection2.onopen = function () {
      console.log("second websocket is opened!");
    };
    connection2.onmessage = function (event) {
      const json = JSON.parse(event.data);
      console.log("Second data: " + event.data);
      updateEstatesWebSocket(json);
    };
  };

  const updateEstatesWebSocket = (json) => {
    let lista = [...realEstateList];
    let values = [...lista];
    values = [...json];
    setRealEstateList(values);
  };

  useEffect(() => {
    initWebSocket();
    initWebSocket2();
  }, []);

  return (
    <div>
      <label>Location:</label>
      <select value={location} onChange={handleLocationChange}>
        {locations.map((e) => (
          <option key={e.id} value={e.name}>
            {e.name}
          </option>
        ))}
      </select>
      <br />
      <label>Min Price:</label>
      <input type="number" value={minPrice} onChange={handleMinPriceChange} />
      <br />
      <label>Max Price:</label>
      <input type="number" value={maxPrice} onChange={handleMaxPriceChange} />
      <br />
      <label>Type:</label>
      <select value={type} onChange={handleTypeChange}>
        {estateTypes.map((e) => (
          <option key={e.id} value={e.name}>
            {e.name}
          </option>
        ))}
      </select>
      <br />
      <label>Min Size:</label>
      <input type="number" value={minSize} onChange={handleMinSizeChange} />
      <br />
      <label>Max Size:</label>
      <input type="number" value={maxSize} onChange={handleMaxSizeChange} />
      <br />
      <button type="submit" onClick={onSubmitFilter}>
        Filter
      </button>
      <button onClick={getFilteredEstates}>Show</button> <br /> <br />
      <section>
        {realEstateList &&
          realEstateList.map((e) => (
            <ul key={Math.floor(Math.random() * 100)}>
              <li>Location : {e.location}</li>
              <li>Price : {e.price}</li>
              <li>Address : {e.address}</li>
              <li>Owner number : {e.ownerNumber}</li>
              <li>Name : {e.name}</li>
              <li>Rooms : {e.roomsNumber}</li>
              <li>Floor : {e.floor}</li>
              <li>Elevator : {e.elevator}</li>
              <li>Type : {e.type}</li>
            </ul>
          ))}
      </section>
    </div>
  );
}

export default RealEstateModel;
