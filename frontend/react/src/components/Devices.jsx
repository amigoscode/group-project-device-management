import {Spinner, StackDivider, VStack} from '@chakra-ui/react';
import {useEffect, useState} from "react";
import SidebarWithHeader from "./shared/Sidebar.jsx";
import {getDevices, getSingleDevice} from "../services/client.js";
import DeviceDrawer from "./device/DeviceDrawer.jsx";
import DeviceCard from "./device/DeviceCard.jsx";
import {Box, ThemeProvider} from "@highoutput/hds";
import {Pagination} from "@highoutput/hds-pagination";


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
            if (res.data.totalPages < page) {setPage(res.data.totalPages)}
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
            <DeviceDrawer
                onSuccess={fetchDevices}
            />
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
                        ownerId={device.ownerId}
                        name={device.name}
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