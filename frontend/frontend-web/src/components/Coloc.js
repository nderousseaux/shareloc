import React, {useState} from 'react';
import { useQuery, useQueryCache, useMutation} from 'react-query';

import api from '../api';

let Coloc = () => {

  

  let {isLoading, data: coloc } = useQuery(
    ['coloc'],
    () => api.getColocs()
  );

  let setColocId = (id) => {
    window.localStorage.setItem('idColoc', id);
  }  


  return <>
    <h2>Gestion des colocations</h2>
    {isLoading ? 'Loading...' : coloc && <>
      <p>Choisissez une colocation : </p>
      <select onChange={e => setColocId(parseInt(e.target.value))}>
        {coloc.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
      </select>
    </>}      

    <h3>Créer une colocation</h3>
    <CreateColoc />
  </>;
}

let CreateColoc = () => {
	const queryCache = useQueryCache();

    const [name, setName] = useState('');
    const [message, setMessage] = useState(null);
    
    const [mutateCreateColoc] = useMutation((coloc) => api.createColoc(coloc).then(setMessage("Info : Coloc crée !")));


  	const handleSubmit = e => {
      e.preventDefault();
  
    	mutateCreateColoc({name})
    .then(() => queryCache.invalidateQueries('coloc'))
    .then(() => setName(''));
  	}

	const handleChange = e => {
		setName(e.target.value);
  	}

  	return <><form onSubmit={handleSubmit}>
    	<input id='name' type='text' value={name} onChange={handleChange} />
    	<input type='submit' value='Ajouter' />
    </form>
    <p>{message}</p>
    </>;
};

export {Coloc};
