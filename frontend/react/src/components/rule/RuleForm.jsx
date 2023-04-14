import {Form, Formik, useField} from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {errorNotification, successNotification} from "../../services/notification.js";
import {createRule, updateRule} from "../../services/ruleClient.js";

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
    const [field,meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                 <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon />
                 </Alert>
            ): null}
        </Box>
    )
};


const RuleForm = ({onSuccess, initialValues, ruleId}) => {

    const emptyRule = {
        name : "",
        isActive: "",
        topicPattern: "",
        payloadPattern: "",
        method: "",
        webhookUrl: ""
    }

    const formikInitialValues = initialValues ? {...initialValues} : {...emptyRule};

    return (
        <>
            <Formik
                initialValues={formikInitialValues}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(25, 'Must be 25 characters or less')
                        .required('Required'),
                    isActive: Yup.mixed()
                        .oneOf([true, false], 'Status is required')
                        .required('Status is required'),
                    topicPattern: Yup.string()
                        .max(25, 'Must be 25 characters or less')
                        .required('Required'),
                    payloadPattern: Yup.string()
                        .max(25, 'Must be 25 characters or less')
                        .required('Required'),
                    method: Yup.string()
                        .oneOf(['GET','POST'], 'Invalid Method') 
                        .required('Required'), 
                    webhookUrl: Yup.string()
                        .max(25, 'Must be 25 characters or less')
                        .required('Required'),  
                })}
                onSubmit={(updatedRule, {setSubmitting}) => {
                    console.log(emptyRule)
                    console.log(updatedRule)
                    const ruleDto = {
                        ...updatedRule,
                    }
                    console.log(ruleDto)
                    setSubmitting(true);
                    if(ruleDto.id) {
                        updateRule(ruleDto)
                            .then(res => {
                                console.log(res);
                                successNotification(
                                    "Rule updated",
                                    `${ruleDto.name} was successfully updated`
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
                        createRule(ruleDto)
                            .then(res => {
                                console.log(res);
                                successNotification(
                                    "Rule created",
                                    `${ruleDto.name} was successfully created`
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
                                placeholder="Wind Rule "
                            />
                           
                            <MyTextInput
                                label="Topic Pattern"
                                name="topicPattern"
                                type="text"
                                placeholder="/wind/"
                            />

                            <MyTextInput
                                label="Payload Pattern"
                                name="payloadPattern"
                                type="text"
                                placeholder=""
                            />

                            <MyTextInput
                                label="Method"
                                name="method"
                                type="text"
                                placeholder="GET"
                            />

                            <MyTextInput
                                label="Webhook Url"
                                name="webhookUrl"
                                type="text"
                                placeholder="https://www.callback.com"
                            />

                            <MySelect label="Status" name="status">
                                <option value="">Select status</option>
                                <option value={true}>Active</option>
                                <option value={false}>Inactive</option>
                            </MySelect>


                            <Button disabled={!(isValid && dirty) || isSubmitting} type="submit">Submit</Button>
                        </Stack>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default RuleForm;