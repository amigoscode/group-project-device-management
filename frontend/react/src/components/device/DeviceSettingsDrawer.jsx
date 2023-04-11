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
import DeviceSettingsForm from "./DeviceSettingsForm.jsx";

const AddIcon = () => "#";
const CloseIcon = () => "x";

const DeviceSettingsDrawer = ({onSuccess, deviceId}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        <Button
            leftIcon={<AddIcon/>}
            colorScheme={"teal"}
            onClick={onOpen}
        >
            Settings
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Device Settings</DrawerHeader>

                <DrawerBody>
                    <DeviceSettingsForm
                        deviceId={deviceId}
                        // onSuccess={onSuccess}
                        // initialValues={initialValues}
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

export default DeviceSettingsDrawer;