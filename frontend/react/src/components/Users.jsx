import { Button, Spinner, StackDivider, Text, VStack} from '@chakra-ui/react';
import React, {useEffect, useState} from "react";
import SidebarWithHeader from "./shared/Sidebar.jsx";
import {getUsers} from "../services/client.js";
import UserDrawer from "./user/UserDrawer.jsx";
import UserCard from "./user/UserCard.jsx";
import { ThemeProvider, Box } from "@highoutput/hds";
import { Pagination } from "@highoutput/hds-pagination";


export default function Devices() {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = React.useState(1);
    const [pageSize, setPageSize] = React.useState(5);

    const fetchUsers = () => {
        setLoading(true);
        getUsers(page - 1, pageSize).then(res => {
            console.log(res.data)
            setUsers(res.data.users)
            setCurrentPage(res.data.currentPage)
            setTotalPages(res.data.totalPages)
            setTotalElements(res.data.totalElements)
            console.log(res)
        }).catch(err => {
            console.log(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchUsers()
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

    if (users.length <= 0) {
        return (
            <UserDrawer
                fetchUsers={fetchUsers}
            />
        )
    }

    return (
        <SidebarWithHeader>
            <VStack divider={<StackDivider borderColor='gray.200'/>} spacing={4} align='stretch'>
                <UserDrawer
                    fetchUsers={fetchUsers}
                />
                {users.map((user, index) => (
                    <UserCard
                        key={index}
                        id={user.id}
                        email={user.email}
                        name={user.name}
                        password={user.password}
                        roles={user.roles}
                    ></UserCard>
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
                        onChange={function ({ page, pageSize }) {
                            setPage(page);
                            setPageSize(pageSize);
                        }}
                    />
                </Box>

            </ThemeProvider>

        </SidebarWithHeader>
    )

}