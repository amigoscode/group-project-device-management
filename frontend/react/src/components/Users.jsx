import {Button, Spinner, StackDivider, VStack} from '@chakra-ui/react';
import React, {useEffect, useState} from "react";
import {getUsers} from "../services/client.js";
import SidebarWithHeader from "./shared/Sidebar.jsx";
import UserCard from "./user/UserCard.jsx";
import UserDrawer from "./user/UserDrawer.jsx";

export default function Users() {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(false);

    const fetchUsers = () => {
        setLoading(true);
        getUsers().then(res => {
            setUsers(res.data.users)
            console.log(res)
        }).catch(err => {
            console.log(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchUsers()
    }, [])

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
            <SidebarWithHeader>
                <Button colorScheme='teal' variant='outline'>Click me</Button>
            </SidebarWithHeader>
        )
    }

    return (

        <SidebarWithHeader>
            <VStack divider={<StackDivider borderColor='gray.200'/>} spacing={4} align='stretch'>
                <UserDrawer
                    fetchUsers={fetchUsers}
                    // initialValues={{ name, email, age }}
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
        </SidebarWithHeader>
    )
}

