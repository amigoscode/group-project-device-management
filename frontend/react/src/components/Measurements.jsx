import {Spinner, Table, TableCaption, TableContainer, Tbody, Td, Text, Tfoot, Th, Thead, Tr} from '@chakra-ui/react';
import React, {useEffect, useState} from "react";
import SidebarWithHeader from "./shared/Sidebar.jsx";
import {Pagination} from "@highoutput/hds-pagination";
import {Box, ThemeProvider} from "@highoutput/hds";
import {getMeasurements} from "../services/deviceClient.js";
import { useLocation } from 'react-router-dom';

export default function Measurements() {
    const [measurements, setMeasurements] = useState([]);
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(10);

    const getDeviceIdFromUrl = () => {
        const location = useLocation();
        // return location.pathname;
        return location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
    }

    const deviceId = getDeviceIdFromUrl();

    const fetchMeasurements = () => {
        setLoading(true);
        getMeasurements(deviceId, page - 1, pageSize).then(res => {
            setMeasurements(res.data.measurements)
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
        fetchMeasurements()
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

    if (measurements.length <= 0) {
        return (
            <SidebarWithHeader>
                <Text>Measurements not found</Text>
            </SidebarWithHeader>
        )
    }

    return (
        <SidebarWithHeader>

            <TableContainer>
                <Table variant='striped' colorScheme='teal'>
                    <TableCaption>Measurements from device {deviceId}</TableCaption>
                    <Thead>
                        <Tr>
                            <Th isNumeric>temperature</Th>
                            <Th isNumeric>pressure</Th>
                            <Th isNumeric>humidity</Th>
                            <Th isNumeric>wind speed</Th>
                            <Th isNumeric>wind direction</Th>
                            <Th isNumeric>longitude</Th>
                            <Th isNumeric>latitude</Th>
                            <Th isNumeric>elevation</Th>
                            <Th>timestamp</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        {measurements.map((measurement, index) => (
                            <Tr key={index}>
                                <Td isNumeric>{measurement.temperature}</Td>
                                <Td isNumeric>{measurement.pressure}</Td>
                                <Td isNumeric>{measurement.humidity}</Td>
                                <Td isNumeric>{measurement.wind.speed}</Td>
                                <Td isNumeric>{measurement.wind.direction}</Td>
                                <Td isNumeric>{measurement.location.longitude}</Td>
                                <Td isNumeric>{measurement.location.latitude}</Td>
                                <Td isNumeric>{measurement.location.elevation}</Td>
                                <Td>{measurement.timestamp}</Td>
                            </Tr>

                        ))}
                    </Tbody>
                    <Tfoot>
                        <Tr>
                            <Th isNumeric>temperature</Th>
                            <Th isNumeric>pressure</Th>
                            <Th isNumeric>humidity</Th>
                            <Th isNumeric>wind speed</Th>
                            <Th isNumeric>wind direction</Th>
                            <Th isNumeric>longitude</Th>
                            <Th isNumeric>latitude</Th>
                            <Th isNumeric>elevation</Th>
                            <Th>timestamp</Th>
                        </Tr>
                    </Tfoot>
                </Table>
            </TableContainer>

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

