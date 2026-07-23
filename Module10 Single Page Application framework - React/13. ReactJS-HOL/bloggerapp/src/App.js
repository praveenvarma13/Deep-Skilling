import React, { useState } from 'react';
import './App.css';

// --- MOCK DATA SOURCE LAB ARRAYS ---
const courses = [
  { id: 201, cname: 'Angular', date: '4/5/2021' },
  { id: 202, cname: 'React', date: '6/3/2026' }
];

export const books = [
  { id: 101, bname: 'Master React', price: 670 },
  { id: 102, bname: 'Deep Dive into Angular 11', price: 800 },
  { id: 103, bname: 'Mongo Essentials', price: 450 }
];

const blogs = [
  { id: 301, title: 'React Learning', author: 'Stephen Biz', text: 'Welcome to learning React!' },
  { id: 302, title: 'Installation', author: 'Schewzdenier', text: 'You can install React from npm.' }
];

function App() {
  // Demonstration state variable for conditional filters
  const [viewMode, setViewMode] = useState('ALL'); 

  // --- WAY 1: ELEMENT VARIABLES CONDITIONAL RENDERING ---
  // Course Details Element Variable Block
  const coursedet = (
    <ul className="item-list">
      {courses.map((course) => (
        <li key={course.id} className="item-node">
          <h3>{course.cname}</h3>
          <p>Released: {course.date}</p>
        </li>
      ))}
    </ul>
  );

  // Book Details Element Variable Block (Adhering to requested Hint structural design)
  const bookdet = (
    <ul className="item-list">
      {books.map((book) => (
        <div key={book.id} className="item-node">
          <h3>{book.bname}</h3>
          <h4>₹{book.price}</h4>
        </div>
      ))}
    </ul>
  );

  // Blog Details Element Variable Block
  const content = (
    <ul className="item-list">
      {blogs.map((blog) => (
        <li key={blog.id} className="item-node">
          <h3>{blog.title}</h3>
          <h4>By: {blog.author}</h4>
          <p>{blog.text}</p>
        </li>
      ))}
    </ul>
  );

  return (
    <div className="app-container">
      <h1 className="dashboard-title">Blogger App Console</h1>
      <p className="dashboard-subtitle">Demonstrating Multiple Conditional Rendering Strategies & List Component Mapping</p>

      {/* Filter interface showcasing state toggling logic */}
      <div className="filter-bar">
        <button className={`filter-btn ${viewMode === 'ALL' ? 'active' : ''}`} onClick={() => setViewMode('ALL')}>Show All Columns</button>
        <button className={`filter-btn ${viewMode === 'COURSES' ? 'active' : ''}`} onClick={() => setViewMode('COURSES')}>Courses Only</button>
        <button className={`filter-btn ${viewMode === 'BOOKS' ? 'active' : ''}`} onClick={() => setViewMode('BOOKS')}>Books Only</button>
        <button className={`filter-btn ${viewMode === 'BLOGS' ? 'active' : ''}`} onClick={() => setViewMode('BLOGS')}>Blogs Only</button>
      </div>

      {/* --- WAY 2 & 3: SHORT CIRCUIT (&&) & TERNARY CONDITIONAL OPERATORS --- */}
      <div className="dashboard-grid">
        
        {/* Course Column Render Condition */}
        {(viewMode === 'ALL' || viewMode === 'COURSES') && (
          <div className="column-card mystyle1">
            <h2 className="column-header">Course Details</h2>
            {coursedet}
          </div>
        )}

        {/* Book Column Render Condition */}
        {viewMode === 'ALL' || viewMode === 'BOOKS' ? (
          <div className="column-card st2">
            <h2 className="column-header">Book Details</h2>
            {bookdet}
          </div>
        ) : null}

        {/* Blog Column Render Condition */}
        {(viewMode === 'ALL' || viewMode === 'BLOGS') && (
          <div className="column-card v1">
            <h2 className="column-header">Blog Details</h2>
            {content}
          </div>
        )}

      </div>
    </div>
  );
}

export default App;