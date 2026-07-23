import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './Home';
import TrainersList from './TrainerList';
import TrainerDetail from './TrainerDetails';
import trainersMock from './TrainersMock';

function App() {
  return (
    <Router>
      <div style={{ padding: '25px', fontFamily: 'Arial, sans-serif' }}>
        <h1 style={{ margin: '0 0 10px 0', fontSize: '32px' }}>My Academy Trainers App</h1>
        
        <nav style={{ marginBottom: '20px', fontSize: '18px' }}>
          <Link to="/" style={{ textDecoration: 'underline', color: 'blue', marginRight: '10px' }}>Home</Link>
          {' | '}
          <Link to="/trainers" style={{ textDecoration: 'underline', color: 'blue', marginLeft: '10px' }}>Show Trainers</Link>
        </nav>
        
        <hr style={{ border: '1px solid #ddd', marginBottom: '20px' }} />
        
        <Routes>
          {/* Base Route */}
          <Route path="/" element={<Home />} />
          
          {/* Trainers List Route */}
          <Route path="/trainers" element={<TrainersList trainers={trainersMock} />} />
          
          {/* Trainer Details Route with URL Parameter ':id' */}
          <Route path="/trainers/:id" element={<TrainerDetail />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;