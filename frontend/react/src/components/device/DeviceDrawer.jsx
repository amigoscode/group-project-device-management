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
import CreateDeviceForm from "./DeviceForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const DeviceDrawer = ({ onSuccess, initialValues }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            leftIcon={<AddIcon/>}
            colorScheme={"teal"}
            onClick={onOpen}
        >
            {!initialValues && 'Create device'}
            {initialValues && 'Update'}
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                {!initialValues && <DrawerHeader>Create new device</DrawerHeader>}
                {initialValues && <DrawerHeader>Update device</DrawerHeader>}

                <DrawerBody>
                    <CreateDeviceForm
                        onSuccess={onSuccess}
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

export default DeviceDrawer;