import React from 'react';

class Cart extends React.Component {
  render() {
    return (
      <table style={{ margin: '0 auto', borderCollapse: 'collapse', border: '2px solid #bbb', fontFamily: 'sans-serif' }}>
        <thead>
          <tr style={{ borderBottom: '2px solid #bbb' }}>
            <th style={{ padding: '8px 25px', border: '1px solid #bbb', color: 'green' }}>Name</th>
            <th style={{ padding: '8px 25px', border: '1px solid #bbb', color: 'green' }}>Price</th>
          </tr>
        </thead>
        <tbody>
          {this.props.item.map((item, index) => {
            return (
              <tr key={index}>
                <td style={{ padding: '8px 25px', border: '1px solid #bbb', color: '#666', textAlign: 'center' }}>
                  {item.itemname}
                </td>
                <td style={{ padding: '8px 25px', border: '1px solid #bbb', color: '#666', textAlign: 'center' }}>
                  {item.price}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    );
  }
}

export default Cart;