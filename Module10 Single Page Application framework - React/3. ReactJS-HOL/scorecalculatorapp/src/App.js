import React from 'react';
import CalculateScore from './Components/CalculateScore';

function App() {
  return (
    <div className="App">
      {/* Passing Name, School, Total, and goal to the functional component */}
      <CalculateScore 
        Name="John Doe" 
        School="Cognizant Public School" 
        Total={450} 
        goal={500} 
      />
    </div>
  );
}

export default App;