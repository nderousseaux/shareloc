import { useEffect, useState, createContext, useContext } from "react";
import { useHistory } from "react-router-dom";
import { url_prefix, headersApi} from './fetch_utils';


let AuthContext = createContext();

export let AuthProvider = ({ children }) => {
  
    let [user, setUser] = useState(null);
    let [userCheck, setUserCheck] = useState(true);
    let [error, setError] = useState(null);
    let history = useHistory();
   

    let signout = () => {
        setUser(null);
        window.localStorage.removeItem('token');
        history.push('/');
    }

    let signin = (username, password) => {
        return fetch(`${url_prefix}/signin?email=${username}&password=${password}`, {
            headers: headersApi,
            method: "POST"
        })
        .then(checkStatus)
        .then(data => {return data.text()})
        .then(token => {
            window.localStorage.setItem('token', token);
            whoami(token)
            history.push("/") //TODO: rediriger vers la bonne page)
        })
        .catch(() => {
            setError("Votre couple email/password est incorect")
        });
    };

    let signup= (username, firstname, lastname, password) => {
        return fetch(`${url_prefix}/signup?email=${username}&password=${password}&firstname=${firstname}&lastname=${lastname}`, {
            headers: headersApi,
            method: "PUT"
        })
        .then(checkStatus)
        .then(() => {
            history.push("/signin")
        })
        .catch(() => {
            setError("L'email est déjà pris")
        });
    };

    let checkStatus = (response) => {
        if (response.ok) {
            setError(null)
            return response;
        } else {
            return response.text().then((message) => {
                throw new Error(message);
            });
        }
    };

    let whoami = (token) => {
        fetch(`${url_prefix}/whoami`, {
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + token
            }
          })
          .then(checkStatus)
          .then(res => res.json())
          .then(user => {
            setUser(user);
          })
          .catch(() => {
            setUser(null);
          });
    }

    useEffect(() => {
        let tokenStored = window.localStorage.getItem("token");
        if (tokenStored) {
            whoami(tokenStored);
        }
        setUserCheck(false);
    }, []);


  return userCheck ? (
    "Checking authentication..."
  ) : (
    <AuthContext.Provider value={{user, signin, signup, signout, error, history}}>
      {children}
      {error ? <p style={{color:'red'}}>Erreur : {error}</p> : null}
    </AuthContext.Provider>
  );
};

export let useAuth = () => useContext(AuthContext);
