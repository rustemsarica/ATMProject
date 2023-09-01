import axios from "axios";
import { unauthorizedHandler } from "./components/contexts/helpers";

const axiosClient = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL
})
axiosClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('ACCESS_TOKEN');
    config.headers.Authorization = token;
    config.headers.Accept = "*/*";
    return config;
})

axiosClient.interceptors.response.use(
    (response) =>  response,
    (error) => {
        if(error.response.status === 401){
            unauthorizedHandler(error);
        }
        return Promise.reject(error);
        
        
    }
)

export default axiosClient;