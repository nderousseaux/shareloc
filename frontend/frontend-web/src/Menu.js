import React from 'react';
import { Link } from 'react-router-dom';

import { useAuth } from "./Auth";


const Menu = () => {
	let { user, signout } = useAuth()

	return <ul>
		{user ? <>
			<li>
        		Connected as {user.firstname} {user.lastname} <button onClick={signout}>Signout</button>
      		</li>
		</> : null
		}
		{!user ? <>
			<li><Link to="/signin">Se connecter</Link></li>
		</> : null
		}
		
	</ul>
};

export default Menu;
