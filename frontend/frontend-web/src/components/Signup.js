import React, { useState } from 'react';
import { useParams, useHistory } from 'react-router-dom';
import { useQuery, useQueryCache, useMutation } from 'react-query';

import { useAuth } from "../Auth";

let Signup = () => {
    let { signup } = useAuth()
  
    let [email, setEmail] = useState('');
    let [firstname, setFirstname] = useState('');
    let [lastname, setLastname] = useState('');
    let [password, setPassword] = useState('');
  
    let handleSubmit = (e) => {
      e.preventDefault();
      signin(email, firstname, lastname, password)
    };
  
    return <>
      <h2>Signup form</h2>
      <form onSubmit={handleSubmit}>
        <label>email</label>
        <input type="text" value={email} onChange={(e) => setEmail(e.target.value)}/>
        <label>firstname</label>
        <input type="text" value={firstname} onChange={(e) => setFirstname(e.target.value)}/>
        <label>lastname</label>
        <input type="text" value={lastname} onChange={(e) => setLastname(e.target.value)}/>
        <label>Password</label>
        <input type="text" value={password} onChange={(e) => setPassword(e.target.value)}/>
        <input type="submit" />
      </form>
    </>;
  }

export {Signup};
