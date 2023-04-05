import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {errorNotification, successNotification} from "../../services/notification.js";
import {createUser, updateUser} from "../../services/client.js";

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

const UserForm = ({onSuccess, initialValues, customerId}) => {

    const emptyUser = {
        name : "",
        email: "",
        password: "",
        roles: ""
    }

    const formikInitialValues = initialValues ? {...initialValues} : {...emptyUser};

    return (
        <>
            <Formik
                initialValues={formikInitialValues}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    email: Yup.string()
                        .email('Must be 20 characters or less')
                        .required('Required'),
                    password: Yup.string()
                        .min(8, 'Password is too short - should be 8 chars minimum.')
                        .max(20, 'Password is too long - should be 20 chars maximum.')
                        .required(),
                    roles: Yup.string()
                        .oneOf(
                            ['ADMIN', 'DEVICE_OWNER'],
                            'Invalid role'
                        )
                        .required(),
                })}
                onSubmit={(updatedCustomer, {setSubmitting}) => {
                    console.log(emptyUser)
                    console.log(updatedCustomer)
                    const userDto = {
                        ...updatedCustomer,
                        roles: [updatedCustomer.roles]
                    }
                    console.log(userDto)
                    setSubmitting(true);
                    if(userDto.id) {
                        updateUser(userDto)
                            .then(res => {
                                console.log(res);
                                successNotification(
                                    "User updated",
                                    `${userDto.name} was successfully updated`
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
                        createUser(userDto)
                            .then(res => {
                                console.log(res);
                                successNotification(
                                    "User created",
                                    `${userDto.name} was successfully created`
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
                                placeholder="Jane"
                            />

                            <MyTextInput
                                label="Email Address"
                                name="email"
                                type="email"
                                placeholder="jane@formik.com"
                            />

                            <MyTextInput
                                label="Password"
                                name="password"
                                type="password"
                                placeholder="password"
                            />

                            <MySelect label="Roles" name="roles">
                                <option value="">Select role</option>
                                <option value="DEVICE_OWNER">DEVICE OWNER</option>
                                <option value="ADMIN">ADMIN</option>
                            </MySelect>

                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default UserForm;