import React, { useState } from 'react';

export default function Login({ onLogin }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);

  const submit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      const res = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      if (!res.ok) {
        const txt = await res.text();
        setError(txt || 'Login failed');
        return;
      }
      const data = await res.json();
      onLogin(data.token);
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <form onSubmit={submit}>
      <div><input placeholder='username' value={username} onChange={e=>setUsername(e.target.value)} /></div>
      <div><input type='password' placeholder='password' value={password} onChange={e=>setPassword(e.target.value)} /></div>
      <div><button type='submit'>Login</button></div>
      {error && <div style={{color:'red'}}>{error}</div>}
    </form>
  );
}
