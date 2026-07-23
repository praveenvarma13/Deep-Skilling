import React from 'react';
import styles from './CohortDetails.module.css'; // Importing the CSS Module

function CohortDetails({ cohort }) {
  // Define dynamic style logic for the <h3> element depending on the cohort status
  const headingStyle = {
    color: cohort.status.toLowerCase() === 'ongoing' ? 'green' : 'blue'
  };

  return (
    // Applying the locally scoped 'box' class from the CSS Module
    <div className={styles.box}>
      <h3 style={headingStyle}>
        {cohort.code} - {cohort.program}
      </h3>
      <dl>
        <dt>Started On</dt>
        <dd>{cohort.startDate}</dd>
        
        <dt>Current Status</dt>
        <dd>{cohort.status}</dd>
        
        <dt>Coach</dt>
        <dd>{cohort.coach}</dd>
        
        <dt>Trainer</dt>
        <dd>{cohort.trainer}</dd>
      </dl>
    </div>
  );
}

export default CohortDetails;