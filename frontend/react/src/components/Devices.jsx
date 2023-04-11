import {Spinner, StackDivider, VStack} from '@chakra-ui/react';
import React, {useEffect, useState} from "react";
import SidebarWithHeader from "./shared/Sidebar.jsx";
import DeviceDrawer from "./device/DeviceDrawer.jsx";
import DeviceCard from "./device/DeviceCard.jsx";
import {Box, ThemeProvider} from "@highoutput/hds";
import {Pagination} from "@highoutput/hds-pagination";
import {getDevices} from "../services/deviceClient.js";

export default function Devices() {

    const [devices, setDevices] = useState([]);
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(5);

    const fetchDevices = () => {
        setLoading(true);
        getDevices(page - 1, pageSize).then(res => {
            setDevices(res.data.devices)
            setCurrentPage(res.data.currentPage)
            setTotalPages(res.data.totalPages)
            setTotalElements(res.data.totalElements)
            if (res.data.totalPages < page) {
                if (res.data.totalPages > 0) {
                    setPage(res.data.totalPages)
                } else {
                    setPage(1)
                }
            }
            console.log(res)
        }).catch(err => {
            console.log(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchDevices()
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

    if (devices.length <= 0) {
        return (
            <SidebarWithHeader>
                <VStack divider={<StackDivider borderColor='gray.200'/>} spacing={4} align='stretch'>
                    <DeviceDrawer
                        onSuccess={fetchDevices}
                    />
                </VStack>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>
            <VStack divider={<StackDivider borderColor='gray.200'/>} spacing={4} align='stretch'>
                <DeviceDrawer
                    onSuccess={fetchDevices}
                />
                {devices.map((device, index) => (
                    <DeviceCard
                        key={index}
                        id={device.id}
                        name={device.name}
                        ownerId={device.ownerId}
                        createdAt={device.createdAt}
                        updatedAt={device.updatedAt}
                        deletedAt={device.deletedAt}
                        updatedBy={device.updatedBy}
                        onSuccess={fetchDevices}
                    ></DeviceCard>
                ))}
            </VStack>

            <ThemeProvider>

                <Box p={4}>
                    <Pagination
                        variant="minimal"
                        page={page}
                        pageSize={pageSize}
                        count={totalElements}
                        hasLegend={false}
                        hasPageControls
                        onChange={function ({page, pageSize}) {
                            setPage(page);
                            setPageSize(pageSize);
                        }}
                    />
                </Box>

            </ThemeProvider>

        </SidebarWithHeader>
    )

}

