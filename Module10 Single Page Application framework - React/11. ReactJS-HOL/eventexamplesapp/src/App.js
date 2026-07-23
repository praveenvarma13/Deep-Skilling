import React, { useState } from 'react';
import './App.css';
import CurrencyConvertor from './CurrencyConvertor';

function App() {
  const [counter, setCounter] = useState(0);

  // Method 1: Basic increment arithmetic
  const incrementCounter = () => {
    setCounter(prev => prev + 1);
  };

  // Method 2: Say Hello alert notification
  const sayHello = () => {
    alert("Hello! Member1");
  };

  // Multiple function sequence wrapper execution mapped to the "Increment" action
  const handleIncrementClick = () => {
    incrementCounter();
    sayHello();
  };

  // Basic numeric decrement action[cite: 6]
  const handleDecrementClick = () => {
    setCounter(prev => prev - 1);
  };

  // Parameterized welcome handler routine[cite: 6]
  const sayWelcome = (message) => {
    alert(message);
  };

  // Synthetic React click event interface simulation display[cite: 6]
  const handleSyntheticClick = (e) => {
    // Demonstrating proper event identifier use inside native alert boxes
    if (e.type === 'click') {
      alert("I was clicked");
    }
  };

  return (
    <div className="app-container">
      {/* Upper Control Console Card */}
      <div className="card">
        <div className="counter-display">{counter}</div>
        
        <div className="btn-grid">
          <button className="btn" onClick={handleIncrementClick}>
            Increment
          </button>
          <button className="btn btn-secondary" onClick={handleDecrementClick}>
            Decrement
          </button>
        </div>

        <div className="btn-grid">
          <button className="btn btn-secondary" onClick={() => sayWelcome("welcome")}>
            Say welcome
          </button>
          <button className="btn btn-secondary" onClick={handleSyntheticClick}>
            Click on me
          </button>
        </div>
      </div>

      {/* Currency Component Render Section[cite: 6] */}
      <CurrencyConvertor />
    </div>
  );
}

export default App;