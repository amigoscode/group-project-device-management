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
import {deleteUser} from "../../services/userClient.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import DeviceDrawer from "./DeviceDrawer.jsx";
import React, {useRef} from "react";
import {deleteDevice} from "../../services/deviceClient.js";
import DeviceSettingsDrawer from "./DeviceSettingsDrawer.jsx";
import {useNavigate} from "react-router-dom";

export default function DeviceCard({id, name, ownerId, createdAt, updatedAt, deletedAt, updatedBy, onSuccess}) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = useRef()
    const navigate = useNavigate();

    const device = {
        id: id,
        name: name,
        ownerId: ownerId,
        createdAt: createdAt,
        updatedAt: updatedAt,
        deletedAt: deletedAt,
        updatedBy: updatedBy
    }

    return (
        <Card align='center'>
            <CardHeader>
                { name && <Heading size='md'> {name} </Heading> }
                { !name && <Heading size='md'> User </Heading> }
            </CardHeader>
            <CardBody>
                { id && <> <Text fontSize='lg' as='b'>id: {id}</Text> <br /> </> }
                { ownerId && <> <Text fontSize='lg' as='b'>ownerId: {ownerId}</Text> <br /> </> }
                { createdAt && <> <Text fontSize='lg' as='b'>createdAt: {createdAt}</Text> <br /> </> }
                { updatedAt && <> <Text fontSize='lg' as='b'>updatedAt: {updatedAt}</Text> <br /> </> }
                { deletedAt && <> <Text fontSize='lg' as='b'>deletedAt: {deletedAt}</Text> <br /> </> }
                { updatedBy && <> <Text fontSize='lg' as='b'>updatedBy: {updatedBy}</Text> <br /> </> }
            </CardBody>
            <CardFooter>
                <Stack spacing={4} direction='row' align='center'>
                    <DeviceDrawer
                        initialValues={device}
                        onSuccess={onSuccess}
                    />
                    <DeviceSettingsDrawer
                        deviceId={id}
                    />
                    <Button
                        colorScheme='blue'
                        onClick={() => {
                            console.log(id)
                            navigate(`/measurements/${id}`);
                        } }
                    >Measurements</Button>
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
                                    Are you sure you want to delete {name}? You can't undo this action afterwards.
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

