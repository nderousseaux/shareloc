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
    }

};

export default api;
