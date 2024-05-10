import axios from "axios";

const jwtAxios = axios.create();

// before request
const beforeReq = (config) => {
    console.log('before request ...');
    return config;
}

// fail request
const requestFail = (error) => {
    console.log('fail request ...');
    return Promise.reject(error);
}

// before return response
const beforeRes = async (res) => {
    console.log('before return response ...');
    return res;
}

// fail response
const responseFail = (err) => {
    console.log('response fail error ...');
    return Promise.reject(err);
}

jwtAxios.interceptors.request.use(beforeReq, requestFail);
jwtAxios.interceptors.response.use(beforeRes, responseFail);

export default jwtAxios;