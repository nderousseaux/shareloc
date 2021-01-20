import React, { useState } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { useQuery, useQueryCache, useMutation } from 'react-query';

import { useAuth } from "../Auth";

let Signin = () => {
    let { signin } = useAuth()
  
    let [username, setUsername] = useState('');
    let [password, setPassword] = useState('');
  
    let handleSubmit = (e) => {
      e.preventDefault();
      signin(username, password)
    };
  
    return <>
      <h2>Signin form</h2>
      <form onSubmit={handleSubmit}>
        <label>Username</label>
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}/>
        <label>Password</label>
        <input type="text" value={password} onChange={(e) => setPassword(e.target.value)}/>
        <input type="submit" />
      </form>
    </>;
  }

export {Signin};
