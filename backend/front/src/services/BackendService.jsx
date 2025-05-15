// BackendService.js
import axios from 'axios'
import Utils from "../Utils/Utils";
import {alertActions, store, userConstants} from "../Utils/Rdx";

const API_URL = 'http://localhost:8089/api/v1'
const AUTH_URL = 'http://localhost:8089/auth'

function showError(msg) {
    store.dispatch(alertActions.error(msg))
}

axios.interceptors.request.use(
    config => {
        store.dispatch(alertActions.clear())
        let token = Utils.getToken();
        if (token)
            config.headers.Authorization = token;
        return config;
    },
    error => {
        showError(error.message)
        return Promise.reject(error);
    })

axios.interceptors.response.use(undefined,
    error => {
        // Получаем URL запроса, который вызвал ошибку
        const requestUrl = error.config ? error.config.url : '';

        // Проверяем, связан ли запрос с countries
        const isCountriesRequest = requestUrl.includes('/countries') || requestUrl.includes('/deletecountries');

        if (error.response && error.response.status && [401, 403].indexOf(error.response.status) !== -1) {
            // Показываем сообщение об ошибке всегда
            showError("Ошибка авторизации");

            // Разлогиниваем только если это не запрос к countries
            if (!isCountriesRequest) {
                store.dispatch({ type: userConstants.LOGOUT });
            }
        }
        else if (error.response && error.response.data && error.response.data.message)
            showError(error.response.data.message)
        else
            showError(error.message)
        return Promise.reject(error);
    })

class BackendService {
    login(login, password) {
        return axios.post(`${AUTH_URL}/login`, {login, password})
    }

    logout() {
        return axios.get(`${AUTH_URL}/logout`);
    }

    /* Countries */

    retrieveAllCountries(page, limit) {
        return axios.get(`${API_URL}/countries`);
    }

    retrieveCountry(id) {
        return axios.get(`${API_URL}/countries/${id}`);
    }
    createCountry(country) {
        console.log("BackendService.createCountry:", country);
        // Используем GET запрос с параметрами в URL
        return axios.post(`${API_URL}/country/create?name=${encodeURIComponent(country.name)}`);
    }

    updateCountry(country) {
        console.log("BackendService.updateCountry:", country);
        // Используем GET запрос с параметрами в URL
        return axios.put(`${API_URL}/country/update/${country.id}?name=${encodeURIComponent(country.name)}`);
    }

    deleteCountries(countries) {
        const countryIds = countries.map(country => country.id);
        const params = new URLSearchParams();
        countryIds.forEach(id => {
            params.append('ids', id);
        });

        return axios.delete(`${API_URL}/country/delete-by-id?${params.toString()}`);
    }
}

export default new BackendService()