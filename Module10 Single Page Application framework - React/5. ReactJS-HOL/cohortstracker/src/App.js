import React from 'react';
import CohortDetails from './CohortDetails';

function App() {
  // Dashboard mock array data mirroring Cognizant Academy specifications
  const cohortsData = [
    {
      id: 1,
      code: 'INTADMDF10',
      program: '.NET FSD',
      startDate: '22-Feb-2022',
      status: 'Scheduled',
      coach: 'Aathma',
      trainer: 'Jojo Jose'
    },
    {
      id: 2,
      code: 'ADM21JF014',
      program: 'Java FSD',
      startDate: '10-Sep-2021',
      status: 'Ongoing',
      coach: 'Apoorv',
      trainer: 'Elisa Smith'
    },
    {
      id: 3,
      code: 'CDBJF21025',
      program: 'Java FSD',
      startDate: '24-Dec-2021',
      status: 'Ongoing',
      coach: 'Aathma',
      trainer: 'John Doe'
    }
  ];

  return (
    <div style={{ padding: '30px', fontFamily: 'Arial, sans-serif' }}>
      <h1 style={{ textAlign: 'left', marginBottom: '20px' }}>Cohorts Details</h1>
      <div>
        {cohortsData.map((cohort) => (
          <CohortDetails key={cohort.id} cohort={cohort} />
        ))}
      </div>
    </div>
  );
}

export default App;