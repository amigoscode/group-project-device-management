import axios from "axios";
import {user} from "./userService";

export const getRules = async(page, size) => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/rules?page=${page}&size=${size}`, {
            data: {

            },
            params: {

            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            auth: {
                username: user().auth_username,
                password: user().auth_password
            }
        })
    } catch (e) {
        throw e;
    }
}

export const getARule = async(ruleId) => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/rules/${ruleId}`, {
            data: {

            },
            params: {

            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            auth: {
                username: user().auth_username,
                password: user().auth_password
            }
        })
    } catch (e) {
        throw e;
    }
}

export const createRule = async(ruleDto) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/rules`,JSON.stringify(ruleDto),
         {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            auth: {
                username: user().auth_username,
                password: user().auth_password
            }
        })
    } catch (e) {
        throw e;
    }
}

export const deleteRule = async(ruleId) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/rules/${ruleId}`, {
            data: {

            },
            params: {

            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            auth: {
                username: user().auth_username,
                password: user().auth_password
            }
        })
    } catch (e) {
        throw e;
    }
}

export const updateRule = async(ruleDto) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/rules/${ruleDto.id}`, JSON.stringify(ruleDto),
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            auth: {
                username: user().auth_username,
                password: user().auth_password
            }
        })
    } catch (e) {
        throw e;
    }
}