import React, {useState} from 'react';
import { useAuth } from "../Auth";

import api from '../api';

let User = () => {

  let { user } = useAuth()
  return <>
    <h2>Gestion du profil</h2>
    <p>Email : {user.email}</p>  
    <p>Nom : {user.lastname}</p>  
    <p>Prénom : {user.firstname}</p>  

    <Modify />
  </>;
}

let Modify = () => {  

  let { user } = useAuth()

  let [password, setPassword] = useState('');
  const [message, setMessage] = useState(null);

  let handleSubmit = (e) => {
    e.preventDefault();
    api.setUser({firstname: user.firstname, lastname:user.lastname, password})
    .then(setMessage("Info : Mot de passe modifié !"))
  };

  return <>
    <h2>Modifier le mot de passe : </h2>
    <form onSubmit={handleSubmit}>
      <label>Password</label>
      <input type="text" value={password} onChange={(e) => setPassword(e.target.value)}/>
      <input type="submit" />
    </form>

    <p>{message}</p>
  </>;
}

export {User};
