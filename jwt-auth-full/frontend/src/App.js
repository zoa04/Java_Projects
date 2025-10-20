import React, { useState, useEffect } from 'react';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';

function App() {
  const [token, setToken] = useState(localStorage.getItem('token'));

  useEffect(() => {
    if (token) localStorage.setItem('token', token);
    else localStorage.removeItem('token');
  }, [token]);

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', padding: 20 }}>
      <h2>Auth Demo (React)</h2>
      {!token ? (
        <div style={{display: 'flex', gap: 20}}>
          <div style={{width: 320}}>
            <h3>Login</h3>
            <Login onLogin={t => setToken(t)} />
          </div>
          <div style={{width: 320}}>
            <h3>Register</h3>
            <Register />
          </div>
        </div>
      ) : (
        <div>
          <button onClick={() => setToken(null)}>Logout</button>
          <Dashboard token={token} />
        </div>
      )}
    </div>
  );
}

export default App;
