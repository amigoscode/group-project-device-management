import {
    AlertDialog,
    AlertDialogBody,
    AlertDialogContent,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogOverlay,
    Button,
    Card,
    CardBody,
    CardFooter,
    CardHeader,
    Heading,
    Stack,
    Text,
    useDisclosure
} from '@chakra-ui/react';
import {deleteUser} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import UserDrawer from "./UserDrawer.jsx";
import React, {useRef} from "react";

export default function UserCard({id, email, name, password, roles, onSuccess}) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = useRef()

    const user = {
        id: id,
        email :email,
        name: name,
        password: "",
        roles: roles[0]
    }

    return (
        <Card align='center'>
            <CardHeader>
                { name && <Heading size='md'> {name} </Heading> }
                { !name && <Heading size='md'> User </Heading> }
            </CardHeader>
            <CardBody>
                { id && <> <Text fontSize='lg' as='b'>id: {id}</Text> <br /> </> }
                { email && <> <Text fontSize='lg' as='b'>email: {email}</Text> <br /> </> }
                { password && <> <Text fontSize='lg' as='b'>password: {password}</Text> <br /> </> }
                { roles && <> <Text fontSize='lg' as='b'>roles: {roles}</Text> <br /> </> }
            </CardBody>
            <CardFooter>
                <Stack spacing={4} direction='row' align='center'>
                    {/*<Button colorScheme='blue'>Edit</Button>*/}
                    <UserDrawer
                        initialValues={user}
                        fetchUsers={onSuccess}
                    />
                    <Button
                        colorScheme='red'
                        onClick={onOpen}
                    >Delete</Button>
                    <AlertDialog
                        isOpen={isOpen}
                        leastDestructiveRef={cancelRef}
                        onClose={onClose}
                    >
                        <AlertDialogOverlay>
                            <AlertDialogContent>
                                <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                    Delete Customer
                                </AlertDialogHeader>

                                <AlertDialogBody>
                                    Are you sure you want to delete {name}? You can't undo this action afterwards.
                                </AlertDialogBody>

                                <AlertDialogFooter>
                                    <Button ref={cancelRef} onClick={onClose}>
                                        Cancel
                                    </Button>
                                    <Button colorScheme='red' onClick={() => {
                                        deleteUser(id)
                                            .then(res => {
                                                console.log(res);
                                                successNotification(
                                                    "User deleted",
                                                    `${name} was successfully deleted`
                                                )
                                                if(onSuccess) onSuccess();
                                                // fetchCustomers();
                                            }).catch(err => {
                                            console.log(err);
                                            errorNotification(
                                                err.code,
                                                err.response.data.message
                                            )
                                        }).finally(() => {
                                            onClose()
                                        })
                                    }} ml={3}>
                                        Delete
                                    </Button>
                                </AlertDialogFooter>
                            </AlertDialogContent>
                        </AlertDialogOverlay>
                    </AlertDialog>
                </Stack>

            </CardFooter>
        </Card>
    )
}

