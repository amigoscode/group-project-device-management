import axios from 'axios';
import {user} from "./userService.js";

export const getDevices = async (page, size) => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/devices?page=${page}&size=${size}`, {
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

export const createDevice = async (deviceDto) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/devices`, JSON.stringify(deviceDto),
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

export const deleteDevice = async (deviceId) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/devices/${deviceId}`, {
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

export const updateDevice = async (deviceDto) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/devices/${deviceDto.id}`, JSON.stringify(deviceDto),
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

export const getDeviceSettings = async (deviceId) => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/devices/${deviceId}/settings`, {
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

export const updateDeviceSettings = async (deviceSettingsDto) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/devices/${deviceSettingsDto.deviceId}/settings`, JSON.stringify(deviceSettingsDto),
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

export const getMeasurements = async (deviceId, page, size) => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/devices/${deviceId}/measurements?page=${page}&size=${size}`, {
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