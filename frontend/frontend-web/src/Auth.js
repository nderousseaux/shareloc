import { useEffect, useState, createContext, useContext } from "react";
import { useHistory } from "react-router-dom";

let AuthContext = createContext();

export let AuthProvider = ({ children }) => {
  
    let [user, setUser] = useState(null);
    let [userCheck, setUserCheck] = useState(true);
    let [error, setError] = useState(null);
    let history = useHistory();
    const url_prefix = "http://localhost:8080/lp1_shareloc_dm/shareloc_api";
    const headersApi = {
        Accept: "application/json",
        "Content-Type": "application/json",
    };

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
          .catch((err) => {
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
    <AuthContext.Provider value={{user, signin, signout, error, history}}>
      {children}
      {error ? <p style={{color:'red'}}>Erreur : {error}</p> : null}
    </AuthContext.Provider>
  );
};

export let useAuth = () => useContext(AuthContext);
