import React from 'react'
import ReactDOM from 'react-dom/client'
import {ChakraProvider} from '@chakra-ui/react'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import './index.css'
import Users from "./components/Users.jsx";
import Devices from "./components/Devices.jsx";
import Login from "./components/login/Login.jsx";
import AboutMe from "./components/user/AboutMe.jsx";
import DeviceSettings from "./components/DeviceSettings.jsx";
import Measurements from "./components/Measurements.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login/>
    },
    {
        path: "about-me",
        element: <AboutMe/>
    },
    {
        path: "users",
        element: <Users/>
    },
    {
        path: "devices",
        element: <Devices/>
    },
    {
        path: "device-settings",
        element: <DeviceSettings/>
    },
    {
        path: "measurements",
        element: <Measurements/>
    }
])

ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <RouterProvider router={router}/>
            </ChakraProvider>
        </React.StrictMode>,
    )
