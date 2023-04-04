import React from "react";
import SidebarWithHeader from "../shared/Sidebar.jsx";
import {user} from "../../services/userService.js";
import UserCard from "./UserCard.jsx";

export default function AboutMe() {

    console.log(user())

    return (
        <SidebarWithHeader>
            <UserCard
                id={user().id}
                email={user().email}
                name={user().name}
                password={user().password}
                roles={user().roles}
            ></UserCard>
        </SidebarWithHeader>
    )

}

