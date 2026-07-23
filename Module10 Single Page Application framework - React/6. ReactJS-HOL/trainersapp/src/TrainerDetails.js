import React from 'react';
import { useParams } from 'react-router-dom';
import trainersMock from './TrainersMock';

function TrainerDetail() {
  const { id } = useParams(); // Extract ID parameter from URL path
  
  // Find the requested trainer
  const trainer = trainersMock.find((t) => t.trainerId === id);

  if (!trainer) {
    return <h2 style={{ color: 'red' }}>Trainer Details Not Found!</h2>;
  }

  return (
    <div style={{ marginTop: '20px', fontFamily: 'Arial, sans-serif' }}>
      <h2>Trainers Details</h2>
      <h3 style={{ margin: '10px 0', fontSize: '20px' }}>
        {trainer.name} ({trainer.technology})
      </h3>
      <p style={{ fontSize: '18px', margin: '5px 0' }}>{trainer.email}</p>
      <p style={{ fontSize: '18px', margin: '5px 0' }}>{trainer.phone}</p>
      <ul style={{ marginTop: '15px', paddingLeft: '20px' }}>
        {trainer.skills.map((skill, index) => (
          <li key={index} style={{ fontSize: '16px', margin: '5px 0' }}>
            {skill}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TrainerDetail;