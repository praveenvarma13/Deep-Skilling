import React from 'react';
import { Link } from 'react-router-dom';

function TrainersList({ trainers }) {
  return (
    <div>
      <h2>Trainers List</h2>
      <ul>
        {trainers.map((trainer) => (
          <li key={trainer.trainerId} style={{ margin: '10px 0' }}>
            <Link 
              to={`/trainers/${trainer.trainerId}`} 
              style={{ fontSize: '18px', textDecoration: 'underline', color: 'blue' }}
            >
              {trainer.name}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TrainersList;