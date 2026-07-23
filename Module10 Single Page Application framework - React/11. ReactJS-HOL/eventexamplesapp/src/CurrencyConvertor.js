import React, { useState } from 'react';

export default function CurrencyConvertor() {
  const [amount, setAmount] = useState('');
  const [currency, setCurrency] = useState('');

  // Handle click to trigger form validation/conversion logic
  const handleSubmit = (e) => {
    e.preventDefault(); // Prevent standard browser reload logic
    
    // Simple conversion math mapping to the lab criteria logic
    const numericAmount = parseFloat(amount) || 0;
    const convertedValue = numericAmount * 80; 

    alert(`Converting to  Euro Amount is ${convertedValue}`);
  };

  return (
    <div className="card">
      <h2 className="section-title">Currency Convertor!!!</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Amount:</label>
          <input 
            type="text" 
            className="form-input"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label className="form-label">Currency:</label>
          <input 
            type="text" 
            className="form-input"
            value={currency}
            onChange={(e) => setCurrency(e.target.value)}
          />
        </div>
        <button type="submit" className="btn btn-submit">Submit</button>
      </form>
    </div>
  );
}