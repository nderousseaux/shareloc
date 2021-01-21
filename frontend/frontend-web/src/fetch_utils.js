
export const checkStatus = (res) => {
	if (res.ok) {
		return res;
	} else {
		return res.text()
			.then((msg) => { throw new Error(msg); });
	}
};

export const url_prefix = "http://localhost:8080/lp1_shareloc_dm/shareloc_api";
export const headersApi = {
	Accept: "application/json",
	"Content-Type": "application/json",
};
