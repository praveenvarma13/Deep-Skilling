import React from 'react';
import '../Stylesheets/mystyle.css'; // Importing custom styles

// Functional component using destructuring to unpack the props
const CalculateScore = ({ Name, School, Total, goal }) => {
  // Calculating average score dynamically
  const average = (Total / goal).toFixed(2);

  return (
    <div className="formatstyle">
      <h1>Student Score Details</h1>
      <div className="details">
        <p><strong>Name:</strong> {Name}</p>
        <p><strong>School:</strong> {School}</p>
        <p><strong>Total Marks:</strong> {Total}</p>
        <p><strong>Goal (Out of):</strong> {goal}</p>
        <p className="average"><strong>Average Score:</strong> {average}</p>
      </div>
    </div>
  );
};

export default CalculateScore;