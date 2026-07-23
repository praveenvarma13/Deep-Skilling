import React, { Component } from 'react';
import Post from './Post';

class Posts extends Component {
  constructor(props) {
    super(props);
    // Initialize the component state with an empty array of posts
    this.state = {
      postsList: [],
      hasError: false
    };
  }

  // Method responsible for using Fetch API to grab data
  loadPosts() {
    fetch('https://jsonplaceholder.typicode.com/posts')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then((data) => {
        // Map the plain JSON data array into instances of our Post class
        const formattedPosts = data.map(item => new Post(item.id, item.title, item.body));
        this.setState({ postsList: formattedPosts });
      })
      .catch((error) => {
        // Trigger error catching state
        this.setState({ hasError: true });
        alert('Error loading posts: ' + error.message);
      });
  }

  // Lifecycle hook that runs immediately after a component is mounted
  componentDidMount() {
    this.loadPosts();
  }

  // Lifecycle hook responsible for capturing errors in child components
  componentDidCatch(error, info) {
    console.error("ErrorBoundary caught an error", error, info);
    alert("An error occurred in the component: " + error.message);
  }

  render() {
    if (this.state.hasError) {
      return <h2 style={{ color: 'red', textAlign: 'center' }}>Failed to load blog posts.</h2>;
    }

    return (
      <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif', maxWidth: '800px', margin: '0 auto' }}>
        <h1 style={{ textAlign: 'center', color: '#333' }}>Blog Posts</h1>
        <hr />
        {this.state.postsList.length === 0 ? (
          <p style={{ textAlign: 'center' }}>Loading posts...</p>
        ) : (
          this.state.postsList.map((post) => (
            <div key={post.id} style={{ margin: '20px 0', padding: '15px', borderBottom: '1px solid #ccc' }}>
              <h3 style={{ color: '#2c3e50', textTransform: 'capitalize' }}>
                {post.id}. {post.title}
              </h3>
              <p style={{ color: '#555', lineHeight: '1.6' }}>
                {post.body}
              </p>
            </div>
          ))
        )}
      </div>
    );
  }
}

export default Posts;