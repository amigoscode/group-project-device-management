import {Spinner, StackDivider, VStack} from '@chakra-ui/react';
import React, {useEffect, useState} from "react";
import SidebarWithHeader from "./shared/Sidebar.jsx";
import RuleDrawer from "./rule/RuleDrawer.jsx";
import RuleCard from "./rule/RuleCard.jsx";
import {Box, ThemeProvider} from "@highoutput/hds";
import {Pagination} from "@highoutput/hds-pagination";
import {getRules} from "../services/ruleClient.js";



export default function Rules() {
    
    const [rules, setRules] = useState([]);
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(5);


    const fetchRules = () => {
        setLoading(true);
        getRules(page - 1, pageSize).then(res => {
            setRules(res.data.rules)
            setCurrentPage(res.data.currentPage)
            setTotalPages(res.data.totalPages)
            setTotalElements(res.data.totalElements)
            if (res.data.totalPages < page) {
                if (res.data.totalPages > 0) {
                    setPage(res.data.totalPages)
                } else {
                    setPage(1)
                }
            }
            console.log(res)
        }).catch(err => {
            console.log(err)
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchRules()
    }, [page])


    if (rules.length <= 0) {
        return (
            <SidebarWithHeader>
                <VStack divider={<StackDivider borderColor='gray.200'/>} spacing={4} align='stretch'>
                    <RuleDrawer
                        onSuccess={fetchRules}
                    />
                </VStack>
            </SidebarWithHeader>
        )
    }
  return (
    <SidebarWithHeader>
    <VStack divider={<StackDivider borderColor='gray.200'/>} spacing={4} align='stretch'>
        <RuleDrawer
            onSuccess={fetchRules}
        />
        {rules.map((rule, index) => (
            <RuleCard
                key={index}
                id={rule.id}
                name={rule.name}
                isActive={rule.isActive}
                topicPattern={rule.topicPattern}
                payloadPattern={rule.payloadPattern}
                method={rule.method}
                webhookUrl={rule.webhookUrl}
                onSuccess={fetchRules}
            ></RuleCard>
        ))}
    </VStack>

    <ThemeProvider>

        <Box p={4}>
            <Pagination
                variant="minimal"
                page={page}
                pageSize={pageSize}
                count={totalElements}
                hasLegend={false}
                hasPageControls
                onChange={function ({page, pageSize}) {
                    setPage(page);
                    setPageSize(pageSize);
                }}
            />
        </Box>

    </ThemeProvider>

</SidebarWithHeader>
  )
}
