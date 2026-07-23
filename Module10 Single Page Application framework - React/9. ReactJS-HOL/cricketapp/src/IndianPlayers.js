import React from 'react';

// Destructuring odd indexes: 1st, 3rd, 5th
export function OddPlayers([first, , third, , fifth]) {
  return (
    <div>
      <ul>
        <li>First : {first}</li>
        <li>Third : {third}</li>
        <li>Fifth : {fifth}</li>
      </ul>
    </div>
  );
}

// Destructuring even indexes: 2nd, 4th, 6th
export function EvenPlayers([, second, , fourth, , sixth]) {
  return (
    <div>
      <ul>
        <li>Second : {second}</li>
        <li>Fourth : {fourth}</li>
        <li>Sixth : {sixth}</li>
      </ul>
    </div>
  );
}

// Merge separate T20 and Ranji arrays using the ES6 Spread Operator [...][cite: 6]
const T20Players = ['First Player', 'Second Player', 'Third Player'];
const RanjiTrophyPlayers = ['Fourth Player', 'Fifth Player', 'Sixth Player'];
export const IndianPlayersList = [...T20Players, ...RanjiTrophyPlayers];

// Helper component to cleanly display the merged list array
export function ListofIndianPlayers({ IndianPlayers }) {
  return (
    <ul>
      {IndianPlayers.map((player, index) => (
        <div key={index}>
          <li>Mr. {player}</li>
        </div>
      ))}
    </ul>
  );
}