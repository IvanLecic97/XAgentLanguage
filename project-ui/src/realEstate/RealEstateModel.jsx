import React, { useState } from "react";

function RealEstateModel({ dataFromApi }) {
  const [location, setLocation] = useState("");
  const [minPrice, setMinPrice] = useState(0);
  const [maxPrice, setMaxPrice] = useState(0);
  const [type, setType] = useState("");
  const [minSize, setMinSize] = useState(0);
  const [maxSize, setMaxSize] = useState(0);

  const locations = [
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

  const handleLocationChange = (event) => setLocation(event.target.value);
  const handleMinPriceChange = (event) => setMinPrice(event.target.value);
  const handleMaxPriceChange = (event) => setMaxPrice(event.target.value);
  const handleTypeChange = (event) => setType(event.target.value);
  const handleMinSizeChange = (event) => setMinSize(event.target.value);
  const handleMaxSizeChange = (event) => setMaxSize(event.target.value);

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
      <input type="text" value={type} onChange={handleTypeChange} />
      <br />
      <label>Min Size:</label>
      <input type="number" value={minSize} onChange={handleMinSizeChange} />
      <br />
      <label>Max Size:</label>
      <input type="number" value={maxSize} onChange={handleMaxSizeChange} />
    </div>
  );
}

export default RealEstateModel;
