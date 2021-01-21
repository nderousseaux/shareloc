import React, {useState} from 'react';
import { useAuth } from "../Auth";
import { useQuery, useQueryCache, useMutation} from 'react-query';


import api from '../api';

let Service = () => {
  let {isLoading, data: service } = useQuery(
      ['service'],
      () => api.getService()
    );

    let validService = (id) => {
      api.validService(id)
    }

  return <>
  <h1>Service</h1>
    <Task />

    <h2>Services en attente de validation</h2> 

    <ul>
    
    </ul>

  </>
 
}

let Task = () => {

  let {isLoading, data: task } = useQuery(
    ['task'],
    () => api.getTasks()
  );

  let declareService = (id) => {
    api.postService(id)
  }


  return <>
    <ul>
    {isLoading ? 'Loading...' : task && <>

    {task?.map(t => <>
          <li key={t.name}>{t.name} {t.description} {t.cost}</li><button onClick={() => declareService(t.id)}>Déclarer un service</button>
      </>)}
    
    </> }
    </ul>
  </>
}

export {Service};
