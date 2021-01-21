import { checkStatus, url_prefix, headersApi} from './fetch_utils';

let api = {

	getColocs : () => {
        let tokenStored = window.localStorage.getItem("token");
        return fetch(`${url_prefix}/colocation`, {
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
        .then((res) => {
            return res.json()
        })
    },

    createColoc : (coloc) => {
        let tokenStored = window.localStorage.getItem("token");
        return fetch(`${url_prefix}/colocation`, {
            method: 'PUT',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            },
            body: JSON.stringify(coloc)
        })
        .then(checkStatus)
    },

    setUser : (user) => {
        let tokenStored = window.localStorage.getItem("token");
        return fetch(`${url_prefix}/user`, {
            method: 'POST',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            },
            body: JSON.stringify(user)
        })
        .then(checkStatus)
        .catch((err) => {console.log(err)})
    },

    getMembers : (idColoc) => {
        let tokenStored = window.localStorage.getItem("token");
        return fetch(`${url_prefix}/colocation/${idColoc}/user`, {
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
        .then((res) => {
            return res.json()
        })
    },

    getManagers : (idColoc) => {
        let tokenStored = window.localStorage.getItem("token");
        return fetch(`${url_prefix}/colocation/${idColoc}/manager`, {
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
        .then((res) => {
            return res.json()
        })
    },

    quitColoc : (idColoc) => {
        let tokenStored = window.localStorage.getItem("token");
        return fetch(`${url_prefix}/colocation/${idColoc}/quit`, {
            method: 'POST',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
    },

    quitManager : (idColoc) => {
        let tokenStored = window.localStorage.getItem("token");
        return fetch(`${url_prefix}/colocation/${idColoc}/manager`, {
            method: 'DELETE',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
    },

    virer : (user) => {
        let tokenStored = window.localStorage.getItem("token");
        let idColoc = window.localStorage.getItem("idColoc");
        return fetch(`${url_prefix}/colocation/${idColoc}/user/${user}`, {
            method: 'DELETE',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
    },

    promote : (user) => {
        let tokenStored = window.localStorage.getItem("token");
        let idColoc = window.localStorage.getItem("idColoc");
        return fetch(`${url_prefix}/colocation/${idColoc}/manager/${user}`, {
            method: 'PUT',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
    },

    destro : () => {
        let tokenStored = window.localStorage.getItem("token");
        let idColoc = window.localStorage.getItem("idColoc");
        return fetch(`${url_prefix}/colocation/${idColoc}`, {
            method: 'DELETE',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
    },

    getTasks : () => {
        let tokenStored = window.localStorage.getItem("token");
        let idColoc = window.localStorage.getItem("idColoc");
        return fetch(`${url_prefix}/colocation/${idColoc}/task/state?state=ACTIVE`, {
            method: 'GET',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
        .then((res) => {
            return res.json()
        })
    },

    getServices : () => {
        let tokenStored = window.localStorage.getItem("token");
        let idColoc = window.localStorage.getItem("idColoc");
        return fetch(`${url_prefix}/service/colocation/${idColoc}`, {
            method: 'GET',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            }
        })
        .then(checkStatus)
        .then((res) => {
            return res.json()
        })
    },

    postService : (idTask) => {

        let tokenStored = window.localStorage.getItem("token");
        return fetch(`${url_prefix}/task/${idTask}`, {
            method: 'POST',
            headers: {
                ...headersApi,
                'Authorization': 'Bearer ' + tokenStored
            },
        })
        .then(checkStatus)
    },


};

export default api;
