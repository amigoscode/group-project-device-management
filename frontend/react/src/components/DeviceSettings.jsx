import {Spinner, StackDivider, VStack} from '@chakra-ui/react';
import {useEffect, useState} from "react";
import SidebarWithHeader from "./shared/Sidebar.jsx";
import { useLocation } from 'react-router-dom';
import {Text} from '@chakra-ui/react';
import React from "react";

import { getDeviceSettings} from "../services/client.js";

import {Box, ThemeProvider} from "@highoutput/hds";
import {Pagination} from "@highoutput/hds-pagination";

export default function DeviceSettings() {

    const location = useLocation();
    const deviceId = location.state.deviceId;
    const [settings, setDeviceSettings] = useState({
         id: "",
         deviceId: "",
         measurementPeriod: "",
         isMeasurementEnabled: "",
         createdAt:"",
         deletedAt:"",
         updatedAt:"",
         updatedBy:""

        });
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(5);

    const fetchDeviceSettings = () => {
        setLoading(true);
        getDeviceSettings(deviceId, page - 1, pageSize).then(res => {
            setDeviceSettings(res.data)
            setCurrentPage(res.data.currentPage)
            setTotalPages(res.data.totalPages)
            setTotalElements(res.data.totalElements)
            if (res.data.totalPages < page) {setPage(res.data.totalPages)}
            console.log(res)
            console.log(res.data.id)
        }).catch(err => {
            console.log(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
    fetchDeviceSettings()   
    }, [page])

    if (loading) {
        return (
            <SidebarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SidebarWithHeader>
        )
    }


        return (
            <>
            <h1>{settings.id}</h1>
            <p>{settings.deviceId}</p>
            <p>{settings.measurementPeriod}</p>
            <p>{settings.createdAt}</p>
            <p>{settings.deletedAt}</p>
            <p>{settings.updatedAt}</p>
            <p>{settings.updatedBy}</p>
            </>
        )
        


    // return (
    //     <SidebarWithHeader>
    //         <Text>Device settings</Text>
    //     </SidebarWithHeader>
    // )

}

