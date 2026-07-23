import React, { useState } from 'react';
import './App.css';

// 1. Standalone components for Login and Logout buttons
function LoginButton(props) {
  return (
    <button className="btn-auth btn-login" onClick={props.onClick}>
      Login
    </button>
  );
}

function LogoutButton(props) {
  return (
    <button className="btn-auth btn-logout" onClick={props.onClick}>
      Logout
    </button>
  );
}

// 2. Greeting components representing pages based on status[cite: 6]
function UserGreeting() {
  return <h2 className="auth-title">Welcome back</h2>;
}

function GuestGreeting() {
  return <h2 className="auth-title">Please sign up.</h2>;
}

// 3. Greeting controller that coordinates conditional choices[cite: 6]
function Greeting(props) {
  const isLoggedIn = props.isLoggedIn;
  if (isLoggedIn) {
    return <UserGreeting />;
  }
  return <GuestGreeting />;
}

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Mock flight details visible to all users[cite: 6]
  const flights = [
    { id: 1, name: "Skyline Airways (SL-202)", route: "Mumbai ➔ Delhi", time: "10:30 AM" },
    { id: 2, name: "Oceanic Flight (OC-815)", route: "Chennai ➔ Bangalore", time: "02:15 PM" },
    { id: 3, name: "Star Express (SE-991)", route: "Hyderabad ➔ Kolkata", time: "06:45 PM" }
  ];

  const handleLoginClick = () => {
    setIsLoggedIn(true);
  };

  const handleLogoutClick = () => {
    setIsLoggedIn(false);
  };

  // Using an Element Variable to cleanly render our conditional controls[cite: 6]
  let button;
  if (isLoggedIn) {
    button = <LogoutButton onClick={handleLogoutClick} />;
  } else {
    button = <LoginButton onClick={handleLoginClick} />;
  }

  return (
    <div className="app-wrapper">
      {/* Navigation Header Panel */}
      <div className="auth-header">
        <Greeting isLoggedIn={isLoggedIn} />
        {button}
      </div>

      {/* Flight Display Section */}
      <div className="flights-container">
        <h2>Available Flight Details</h2>
        {flights.map((flight) => (
          <div key={flight.id} className="flight-card">
            <div className="flight-info">
              <h3>{flight.name}</h3>
              <p>{flight.route} | Departure: {flight.time}</p>
            </div>
            
            {/* Conditional Rendering: Only render Book button if logged in */}
            {isLoggedIn ? (
              <button className="btn-book" onClick={() => alert(`Ticket booked successfully for ${flight.name}!`)}>
                Book Ticket
              </button>
            ) : (
              <span className="badge-guest">Login to Book</span>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;