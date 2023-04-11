import axios from 'axios';
import {user} from "./userService.js";

export const getUsers = async (page, size) => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users?page=${page}&size=${size}`, {
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

export const aboutMe = async (username, password) => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users/me`, {
            data: {

            },
            params: {

            },
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            auth: {
                username: username,
                password: password
            }
        })
    } catch (e) {
        throw e;
    }
}

export const createUser = async (userDto) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users`, JSON.stringify(userDto),
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                auth: {
                    username: user().auth_username,
                    password: user().auth_password
                }
            }
            )
    } catch (e) {
        throw e;
    }
}

export const deleteUser = async (userId) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${userId}`, {
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

export const updateUser = async (userDto) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users`, JSON.stringify(userDto),
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                auth: {
                    username: user().auth_username,
                    password: user().auth_password
                }
            }
        )
    } catch (e) {
        throw e;
    }
}