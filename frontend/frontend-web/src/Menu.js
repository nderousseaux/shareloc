import React from 'react';
import { Link, useHistory} from 'react-router-dom';
import { useAuth } from "./Auth";


const Menu = () => {

	let { user, signout } = useAuth()
	let history = useHistory();

	let redirect = () =>{
		history.push('/user')
	} 

	return <ul>
		{user ? <>
			<li>
        		Connected as {user.firstname} {user.lastname} <button onClick={redirect}>Gestion</button> <button onClick={signout}>Signout</button>
      		</li>
			<li><Link to="/coloc">Changer de coloc</Link></li>
			<li><Link to="/members">Gestion des membres</Link></li>
			<li><Link to="/services">Services</Link></li>
		</> : null
		}
		{!user ? <>
			<li><Link to="/signin">Se connecter</Link></li>
			<li><Link to="/signup">Créer un compte</Link></li>
		</> : null
		}
		
	</ul>
};

export default Menu;
