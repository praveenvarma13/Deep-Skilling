import React from 'react';
import './App.css';

function App() {
  // 1. Defining data structures matching the lab guidelines
  const element = "Office Space";
  const sr = "https://images.unsplash.com/photo-1497366216548-37526070297c?auto=format&fit=crop&w=400&q=80";
  
  // Single main office item object
  const ItemName = { Name: "DBS", Rent: 50000, Address: 'Chennai' };

  // 2. An array list of office objects to demonstrate looping capabilities
  const offices = [
    { Name: "DBS", Rent: 50000, Address: "Chennai" },
    { Name: "Regus Hub", Rent: 75000, Address: "Bangalore" },
    { Name: "Smartworks", Rent: 55000, Address: "Hyderabad" }
  ];

  // 3. Image JSX attribute definition matching the hint[cite: 6]
  const jsxatt = <img src={sr} width="25%" height="25%" alt="Office Space" />;

  return (
    <div style={{ padding: '40px', fontFamily: 'sans-serif', backgroundColor: '#fff' }}>
      
      {/* Heading elements generated with JavaScript expressions inside JSX */}
      <h1>{element} , at Affordable Range</h1>
      
      {/* Render the image element using custom JSX attributes */}
      {jsxatt}
      
      <br /><br />

      {/* Map through the office spaces to determine color dynamically and display entries */}
      {offices.map((office, index) => {
        
        // Push conditional styling classes/colors based on rent margins[cite: 6]
        let colors = [];
        if (office.Rent <= 60000) {
          colors.push('red'); // Red color if below or equal to 60000[cite: 6]
        } else {
          colors.push('green'); // Green color if above 60000[cite: 6]
        }

        return (
          <div key={index} style={{ marginBottom: '30px' }}>
            <h1 style={{ margin: '5px 0', fontSize: '2.2rem', fontWeight: 'bold' }}>
              Name: {office.Name}
            </h1>
            
            <h3 style={{ margin: '5px 0', color: colors[0], fontSize: '1.3rem', fontWeight: 'bold' }}>
              Rent: Rs. {office.Rent}
            </h3>
            
            <h3 style={{ margin: '5px 0', color: '#333', fontSize: '1.3rem', fontWeight: 'normal' }}>
              Address: {office.Address}
            </h3>
          </div>
        );
      })}

    </div>
  );
}

export default App;