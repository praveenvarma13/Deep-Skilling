import React from 'react';

// Component to render all 11 players
export function ListofPlayers({ players }) {
  return (
    <ul>
      {players.map((item, index) => (
        <div key={index}>
          <li>Mr. {item.name} <span>{item.score}</span></li>
        </div>
      ))}
    </ul>
  );
}

// Component to extract and render players with scores <= 70
export function Scorebelow70({ players }) {
  // Filter players using an ES6 arrow function
  const players70 = [];
  players.map((item) => {
    if (item.score <= 70) {
      players70.push(item);
    }
    return null;
  });

  return (
    <ul>
      {players70.map((item, index) => (
        <div key={index}>
          <li>Mr. {item.name} <span>{item.score}</span></li>
        </div>
      ))}
    </ul>
  );
}