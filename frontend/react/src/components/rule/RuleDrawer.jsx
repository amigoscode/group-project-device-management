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
import CreateRuleForm from "./RuleForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const RuleDrawer = ({ onSuccess, initialValues }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            leftIcon={<AddIcon/>}
            colorScheme={"teal"}
            onClick={onOpen}
        >
            {!initialValues && 'Create rule'}
            {initialValues && 'Update'}
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                {!initialValues && <DrawerHeader>Create new rule</DrawerHeader>}
                {initialValues && <DrawerHeader>Update rule</DrawerHeader>}

                <DrawerBody>
                    <CreateRuleForm
                        // onSuccess={onSuccess}
                        onSuccess={() => {
                            if (onSuccess) {
                                onSuccess();
                            }
                            onClose();
                        }}
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

export default RuleDrawer;