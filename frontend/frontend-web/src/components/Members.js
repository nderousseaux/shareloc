import React, {useState} from 'react';
import { useQuery, useQueryCache, useMutation} from 'react-query';
import { useHistory} from 'react-router-dom';
import { useAuth } from "../Auth";

import api from '../api';

let Members = () => {
  return <>
    <h2>Gestion des membres de la colocation</h2>

    <List />

    <AddUser />
  </>;
}

let List = () => {
  const queryCache = useQueryCache();

  let history = useHistory();

  let { user } = useAuth()

  let idColoc = window.localStorage.getItem("idColoc");
  let {isLoading, data: member } = useQuery(
    ['member'],
    () => api.getMembers(idColoc)
  );

  let {isLoadingM, data: manager } = useQuery(
    ['manager'],
    () => api.getManagers(idColoc)
  );

  const [mutateQuitColoc] = useMutation((id) => api.quitColoc(id));

  let quitColoc = () => {
    mutateQuitColoc(idColoc)
    .then(queryCache.invalidateQueries('coloc'))
    .then(history.push("/coloc"))
  }

  const [mutateQuitManager] = useMutation((id) => api.quitManager(id));

  let quitManager = () => {
    mutateQuitManager(idColoc)
    .then(queryCache.invalidateQueries(['manager']))
  }

  const [mutateVirer] = useMutation((u) => api.virer(u));

  const vire = (u) => {
    mutateVirer(u.email)
    .then(queryCache.invalidateQueries(['member']))
  }

  const [mutatePromote] = useMutation((u) => api.promote(u));

  const promote = (u) => {
    mutatePromote(u.email)
    .then(queryCache.invalidateQueries(['manager']))
  }

  const [mutateDestroy] = useMutation(() => api.destro());

  const destroyColoc = () => {
    mutateDestroy()
    .then(queryCache.invalidateQueries('coloc'))
    .then(history.push("/coloc"))

  } 

  return <>
    <h3>Liste des users</h3>
    {isLoading ? "Loading..." : member && <>
      <ul>
        {member?.map(m => <>
          <li key={m.email}>{m.firstname} {m.lastname}
            {isLoadingM ? "Loading..." : manager && <>
                {manager?.find(u => u.email === m.email) ? "     Est un manager" : "     N'est pas un manager"}


                {/* Quitter la coloc si tu n'es pas le dernier manager */}
                {m.email === user.email ? <>
                  Toi {(manager?.find(u => u.email === m.email) && manager.length > 1) ? <>
                        <button onClick={quitColoc}> Quitter la coloc</button>
                        <button onClick={quitManager}> Quitter les managers</button>
                      </> : '' }

                </> : ''
                }

                {/* Si ce n'est pas toi et que tu es manager  */}
                {m.email !== user.email && (manager?.find(u => u.email === user.email) && !(manager?.find(u => u.email === m.email)))? <>
                  
                  <button onClick={() => vire(m)}> Virer</button>
                  <button onClick={() => promote(m)}> Promote manager</button>


                </> : ''}

                
              </>
            }
            
          </li>

        </>)}
      </ul>
          
      {manager?.find(u => u.email === user.email) ? <>
        <button onClick={destroyColoc}> Supprimer la coloc</button>
      </> : ''
      
      }
          

    </>}
  </>
}

let AddUser = () => {
  return <>
    <h3>Ajouter un user</h3>
  </>
}

export {Members};
