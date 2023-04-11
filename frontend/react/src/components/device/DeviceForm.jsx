import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {errorNotification, successNotification} from "../../services/notification.js";
import {createDevice, updateDevice} from "../../services/client.js";
import React, { useState, useEffect } from "react";

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



const DeviceForm = ({onSuccess, initialValues, userId}) => {

    const [myData, setMyData] = useState("");

    useEffect(() => {
      const data = localStorage.getItem("user");
      if (data) {
        setMyData(data);
      }
    }, []);

    const deviceOwner = JSON.parse(localStorage.getItem('user'));

    const emptyDevice = {
        name : "",
        ownerId:deviceOwner.id,
        updatedBy:deviceOwner.name

    }

    const formikInitialValues = initialValues ? {...initialValues} : {...emptyDevice};

    return (
        <>
            <Formik
                initialValues={formikInitialValues}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                })}
                onSubmit={(updatedDevice, {setSubmitting}) => {
                    const user = JSON.parse(localStorage.getItem('user'));
                    const ownerId = user && user.id;
                    const updatedBy = user && user.name;
                    let deviceDto;
                    if (updatedDevice.id) {
                        deviceDto = {
                            ...updatedDevice,
                            updatedBy
                        };
                      } else {
                        deviceDto = {
                          name: updatedDevice.name,
                          ownerId
                        };
                      }
                    console.log(deviceDto)
                    setSubmitting(true);
                    if(updatedDevice.id) {
                        updateDevice(updatedDevice.id,deviceDto)
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
                                console.log(deviceDto);
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
                                placeholder="Weather-Station-1"
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