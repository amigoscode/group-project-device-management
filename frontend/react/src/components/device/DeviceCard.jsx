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
import {deleteDevice} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import DeviceDrawer from "./DeviceDrawer.jsx";
import React, {useRef} from "react";
import { useNavigate } from 'react-router-dom';

export default function DeviceCard({id, name,ownerId,updatedBy, onSuccess}) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = useRef()

    const device = {
        id: id,
        name: name,
        ownerId: ownerId,
        updatedBy:updatedBy
    }

    const navigate = useNavigate();
    function handleClick() {
        navigate('/device-settings',  { state: { deviceId: id}});
      }

    return (
        <Card align='center'>
            <CardHeader>
                { name && <Heading size='md'> {name} </Heading> }
                { !name && <Heading size='md'> Device </Heading> }
            </CardHeader>
            <CardBody>
                { id && <> <Text fontSize='lg' as='b'>id: {id}</Text> <br /> </> }
                { name && <> <Text fontSize='lg' as='b'>id: {name}</Text> <br /> </> }
                { ownerId && <> <Text fontSize='lg' as='b'>id: {ownerId}</Text> <br /> </> }
                { updatedBy && <> <Text fontSize='lg' as='b'>id: {updatedBy}</Text> <br /> </> }
            </CardBody>
            <CardFooter>
                <Stack spacing={4} direction='row' align='center'>
                    <DeviceDrawer
                        initialValues={device}
                        onSuccess={onSuccess}
                    />
                     <Button
                        colorScheme='blue'
                        onClick={handleClick}
                    >Settings</Button>
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
                                    Delete Device
                                </AlertDialogHeader>

                                <AlertDialogBody>
                                    Are you sure you want to delete device {name}? You can't undo this action afterwards.
                                </AlertDialogBody>

                                <AlertDialogFooter>
                                    <Button ref={cancelRef} onClick={onClose}>
                                        Cancel
                                    </Button>
                                    <Button colorScheme='red' onClick={() => {
                                        deleteDevice(id)
                                            .then(res => {
                                                console.log(res);
                                                successNotification(
                                                    "Device deleted",
                                                    `${name} was successfully deleted`
                                                )
                                                if(onSuccess) onSuccess();
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

