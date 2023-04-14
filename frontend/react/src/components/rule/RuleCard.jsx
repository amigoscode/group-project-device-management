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
import {errorNotification, successNotification} from "../../services/notification.js";
import RuleDrawer from "./RuleDrawer.jsx";
import React, {useRef} from "react";
import {deleteRule} from "../../services/ruleClient.js";


export default function RuleCard({id, name, isActive, topicPattern, payloadPattern, method, webhookUrl, onSuccess}) {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = useRef()

    const rule = {
        id: id,
        name: name,
        isActive: isActive,
        topicPattern: topicPattern,
        payloadPattern: payloadPattern,
        method: method,
        webhookUrl: webhookUrl
    }

    return (
        <Card align='center'>
            <CardHeader>
                { name && <Heading size='md'> {name} </Heading> }
                { !name && <Heading size='md'> User </Heading> }
            </CardHeader>
            <CardBody>
                { id && <> <Text fontSize='lg' as='b'>id: {id}</Text> <br /> </> }
                { isActive && <> <Text fontSize='lg' as='b'>Status: {isActive ? "Active": "Inactive"}</Text> <br /> </> }
                { topicPattern && <> <Text fontSize='lg' as='b'>Topic Pattern: {topicPattern}</Text> <br /> </> }
                { payloadPattern && <> <Text fontSize='lg' as='b'>Payload Pattern: {payloadPattern}</Text> <br /> </> }
                { method && <> <Text fontSize='lg' as='b'>Method: {method}</Text> <br /> </> }
                { webhookUrl && <> <Text fontSize='lg' as='b'>Webhook Url: {webhookUrl}</Text> <br /> </> }
            </CardBody>
            <CardFooter>
                <Stack spacing={4} direction='row' align='center'>
                    <RuleDrawer
                        initialValues={rule}
                        onSuccess={onSuccess}
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
                                    Delete Rule
                                </AlertDialogHeader>

                                <AlertDialogBody>
                                    Are you sure you want to delete {name}? You can't undo this action afterwards.
                                </AlertDialogBody>

                                <AlertDialogFooter>
                                    <Button ref={cancelRef} onClick={onClose}>
                                        Cancel
                                    </Button>
                                    <Button colorScheme='red' onClick={() => {
                                        deleteRule(id)
                                            .then(res => {
                                                console.log(res);
                                                successNotification(
                                                    "Rule deleted",
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

