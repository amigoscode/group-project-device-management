import { createStandaloneToast } from '@chakra-ui/toast'

const { toast } = createStandaloneToast()

const notification = (title, description, status) => {
    toast({
        title,
        description,
        status,
        isClosable: true,
        duration: 4000,
        position:'top',
    })
}

export const successNotification = (title, description) => {
    notification(
        title,
        description,
        "success"
    )
}

export const errorNotification = (title, description) => {
    notification(
        title,
        description,
        "error"
    )
}