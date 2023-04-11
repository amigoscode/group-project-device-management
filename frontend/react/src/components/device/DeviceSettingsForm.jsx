import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {errorNotification, successNotification} from "../../services/notification.js";
import {createUser, getUsers, updateUser} from "../../services/userClient.js";
import {createDevice, getDeviceSettings, updateDevice, updateDeviceSettings} from "../../services/deviceClient.js";
import {useEffect, useState} from "react";

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

const DeviceSettingsForm = ({onSuccess, initialValues, deviceId}) => {
    const [deviceSettings, setDeviceSettings] = useState([]);
    const [loading, setLoading] = useState(false);
    const [dataLoaded, setDataLoaded] = useState(false);

    const fetchDeviceSettings = () => {
        setLoading(true);
        getDeviceSettings(deviceId).then(res => {
            setDeviceSettings(res.data)
            setDataLoaded(true)
        }).catch(err => {
            console.log(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchDeviceSettings()
    }, [])

    const emptyDeviceSettings = {
        id: "",
        deviceId: "",
        measurementPeriod: "",
        isMeasurementEnabled: "",
        createdAt: "",
        updatedAt: "",
        deletedAt: "",
        updatedBy: ""
    }

    const formikInitialValues = deviceSettings ? {...deviceSettings} : {...emptyDeviceSettings};

    return (
        <>
            {dataLoaded &&
            <Formik
                initialValues={formikInitialValues}
                validationSchema={Yup.object({
                    measurementPeriod: Yup.number()
                        .positive()
                        .integer()
                        .required('Required'),
                    isMeasurementEnabled: Yup.string()
                        .oneOf(
                            ['true', 'false'],
                            'Invalid value'
                        )
                        .required(),
                })}
                onSubmit={(updatedDeviceSettings, {setSubmitting}) => {
                    console.log(emptyDeviceSettings)
                    console.log(updatedDeviceSettings)
                    const deviceSettingsDto = {
                        ...updatedDeviceSettings,
                    }
                    console.log(deviceSettingsDto)
                    setSubmitting(true);

                    updateDeviceSettings(deviceSettingsDto)
                        .then(res => {
                            console.log(res);
                            successNotification(
                                "Device settings updated",
                                `${deviceSettingsDto.deviceId} was successfully updated`
                            )
                            if (onSuccess) onSuccess();
                        }).catch(err => {
                        console.log(err);
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                    }).finally(() => {
                        setSubmitting(false);
                    })


                }}
            >
                {({isValid, isSubmitting, dirty}) => (
                    <Form>
                        <Stack spacing={"24px"}>
                            <MyTextInput
                                label="Measurement period"
                                name="measurementPeriod"
                                type="number"
                                placeholder="5"
                            />

                            <MySelect label="Is measurement enabled" name="isMeasurementEnabled">
                                <option value="">Is measurement enabled?</option>
                                <option value="true">true</option>
                                <option value="false">false</option>
                            </MySelect>

                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>

            }
        </>
    );
};

export default DeviceSettingsForm;