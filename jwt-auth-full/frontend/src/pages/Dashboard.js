import React, { useEffect, useState } from 'react';

export default function Dashboard({ token }) {
  const [profile, setProfile] = useState(null);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch('/api/profile', { headers: { 'Authorization': 'Bearer ' + token } })
      .then(r => r.json()).then(setProfile).catch(()=>{});
    fetch('/api/users', { headers: { 'Authorization': 'Bearer ' + token } })
      .then(r => r.json()).then(setUsers).catch(()=>{});
  }, [token]);

  return (
    <div style={{marginTop:20}}>
      <h3>Dashboard</h3>
      <div><strong>Profile:</strong> {profile ? JSON.stringify(profile) : 'loading...'}</div>
      <div style={{marginTop:10}}>
        <strong>All users (admin):</strong>
        <pre>{users.length ? JSON.stringify(users, null, 2) : ' (no data or not admin)'}</pre>
      </div>
    </div>
  );
}
