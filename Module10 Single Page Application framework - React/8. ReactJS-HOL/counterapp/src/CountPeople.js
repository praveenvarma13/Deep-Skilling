import React, { Component } from 'react';

class CountPeople extends Component {
  constructor() {
    super();
    // Initialize state to store entrycount and exitcount as required
    this.state = {
      entrycount: 0,
      exitcount: 0,
      c: 0
    };

    // Bind methods to 'this' context to prevent undefined reference errors
    this.updateEntry = this.updateEntry.bind(this);
    this.updateExit = this.updateExit.bind(this);
  }

  // Method to increment entry count when login is clicked
  updateEntry() {
    this.setState((prevState, props) => {
      return { entrycount: prevState.entrycount + 1 };
    });
  }

  // Method to increment exit count when exit is clicked
  updateExit() {
    this.setState((prevState, props) => {
      return { exitcount: prevState.exitcount + 1 };
    });
  }

  render() {
    // Styling constants to match the visual screenshot exactly[cite: 5]
    const buttonStyle = {
      backgroundColor: '#90ee90', // lightgreen
      border: '2px solid #555',
      borderRadius: '4px',
      padding: '5px 12px',
      fontSize: '16px',
      cursor: 'pointer',
      marginRight: '8px'
    };

    const containerStyle = {
      display: 'flex',
      justifyContent: 'space-around',
      alignItems: 'center',
      minHeight: '60vh',
      fontFamily: 'sans-serif',
      fontSize: '18px'
    };

    const flexBoxStyle = {
      display: 'flex',
      alignItems: 'center'
    };

    return (
      <div style={containerStyle}>
        {/* Left Side: Login / Entry Tracker[cite: 5] */}
        <div style={flexBoxStyle}>
          <button style={buttonStyle} onClick={this.updateEntry}>
            Login
          </button>
          <span>{this.state.entrycount} People Entered!!!</span>
        </div>

        {/* Right Side: Exit / Left Tracker[cite: 5] */}
        <div style={flexBoxStyle}>
          <button style={buttonStyle} onClick={this.updateExit}>
            Exit
          </button>
          <span>{this.state.exitcount} People Left!!!</span>
        </div>
      </div>
    );
  }
}

export default CountPeople;