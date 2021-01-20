import { checkStatus, url_prefix } from './fetch_utils';

let api = {

	getUsers: () => {
		return fetch(`${url_prefix}/user`)
			.then(checkStatus)
			.then(res => res.json());
	},
};

export default api;
