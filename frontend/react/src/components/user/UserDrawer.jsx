import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import CreateCustomerForm from "./UserForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const UserDrawer = ({ fetchUsers, initialValues }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            leftIcon={<AddIcon/>}
            colorScheme={"teal"}
            onClick={onOpen}
        >
            {!initialValues && 'Create user'}
            {initialValues && 'Update'}
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                {!initialValues && <DrawerHeader>Create new user</DrawerHeader>}
                {initialValues && <DrawerHeader>Update user</DrawerHeader>}

                <DrawerBody>
                    <CreateCustomerForm
                        onSuccess={fetchUsers}
                        initialValues={initialValues}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        leftIcon={<CloseIcon/>}
                        colorScheme={"teal"}
                        onClick={onClose}>
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>

}

export default UserDrawer;