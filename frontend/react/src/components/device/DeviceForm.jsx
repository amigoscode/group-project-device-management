import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {errorNotification, successNotification} from "../../services/notification.js";
import {createUser, updateUser} from "../../services/userClient.js";
import {createDevice, updateDevice} from "../../services/deviceClient.js";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const MySelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const DeviceForm = ({onSuccess, initialValues, deviceId}) => {

    const emptyDevice = {
        name : "",
        ownerId: "",
        createdAt: "",
        updatedAt: "",
        deletedAt: "",
        updatedBy: ""
    }

    const formikInitialValues = initialValues ? {...initialValues} : {...emptyDevice};

    return (
        <>
            <Formik
                initialValues={formikInitialValues}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(25, 'Must be 25 characters or less')
                        .required('Required'),
                    ownerId: Yup.string()
                        .required('Required'),
                })}
                onSubmit={(updatedDevice, {setSubmitting}) => {
                    console.log(emptyDevice)
                    console.log(updatedDevice)
                    const deviceDto = {
                        ...updatedDevice,
                    }
                    console.log(deviceDto)
                    setSubmitting(true);
                    if(deviceDto.id) {
                        updateDevice(deviceDto)
                            .then(res => {
                                console.log(res);
                                successNotification(
                                    "Device updated",
                                    `${deviceDto.name} was successfully updated`
                                )
                                if(onSuccess) onSuccess();
                            }).catch(err => {
                            console.log(err);
                            errorNotification(
                                err.code,
                                err.response.data.message
                            )
                        }).finally(() => {
                            setSubmitting(false);
                        })
                    } else {
                        createDevice(deviceDto)
                            .then(res => {
                                console.log(res);
                                successNotification(
                                    "Device created",
                                    `${deviceDto.name} was successfully created`
                                )
                                if(onSuccess) onSuccess();
                            }).catch(err => {
                            console.log(err);
                            errorNotification(
                                err.code,
                                err.response.data.message
                            )
                        }).finally(() => {
                            setSubmitting(false);
                        })
                    }


                }}
            >
                {({isValid, isSubmitting, dirty}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Device name"
                            />

                            <MyTextInput
                                label="Owner id"
                                name="ownerId"
                                type="text"
                                placeholder="3a163fb2-6826-459b-9560-0851bae29910"
                            />

                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default DeviceForm;