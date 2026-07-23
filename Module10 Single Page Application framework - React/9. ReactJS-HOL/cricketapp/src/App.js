import React from 'react';
import { ListofPlayers, Scorebelow70 } from './ListofPlayers';
import { OddPlayers, EvenPlayers, ListofIndianPlayers, IndianPlayersList } from './IndianPlayers';

function App() {
  // TOGGLE THIS VARIABLE: Set to true or false to shift between layout renders[cite: 6]
  const flag = true; 

  // 11 Players mock data pool[cite: 6]
  const players = [
    { name: "Jack", score: 50 },
    { name: "Michael", score: 70 },
    { name: "John", score: 40 },
    { name: "Ann", score: 61 },
    { name: "Elisabeth", score: 61 },
    { name: "Sachin", score: 95 },
    { name: "Dhoni", score: 100 },
    { name: "Virat", score: 84 },
    { name: "Jadeja", score: 64 },
    { name: "Raina", score: 75 },
    { name: "Rohit", score: 80 }
  ];

  // Destructured array for displaying Odd/Even individual player names[cite: 6]
  const IndianTeam = ["Sachin1", "Dhoni2", "Virat3", "Rohit4", "Yuvraj5", "Raina6"];

  if (flag === true) {
    return (
      <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
        <h1>List of Players</h1>
        <ListofPlayers players={players} />
        <hr />
        <h1>List of Players having Scores Less than 70</h1>
        <Scorebelow70 players={players} />
      </div>
    );
  } else {
    return (
      <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
        <div>
          <h1>Odd Players</h1>
          {OddPlayers(IndianTeam)}
          <hr />
          <h1>Even Players</h1>
          {EvenPlayers(IndianTeam)}
        </div>
        <hr />
        <div>
          <h1>List of Indian Players Merged:</h1>
          <ListofIndianPlayers IndianPlayers={IndianPlayersList} />
        </div>
      </div>
    );
  }
}

export default App;