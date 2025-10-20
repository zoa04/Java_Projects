import React, { useState } from 'react';

export default function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState(null);

  const submit = async (e) => {
    e.preventDefault();
    setMsg(null);
    try {
      const res = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      if (!res.ok) {
        const txt = await res.text();
        setMsg(txt || 'Registration failed');
        return;
      }
      setMsg('Registered. You can now login.');
      setUsername(''); setPassword('');
    } catch (err) {
      setMsg(err.message);
    }
  }

  return (
    <form onSubmit={submit}>
      <div><input placeholder='username' value={username} onChange={e=>setUsername(e.target.value)} /></div>
      <div><input type='password' placeholder='password' value={password} onChange={e=>setPassword(e.target.value)} /></div>
      <div><button type='submit'>Register</button></div>
      {msg && <div style={{marginTop:8}}>{msg}</div>}
    </form>
  );
}
