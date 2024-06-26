import { createSlice } from '@reduxjs/toolkit'
import * as state from "./SessionInfoSlice";

const SessionInfo = createSlice({
    name: 'SessionInfo',
    initialState : {
        memberId: "",
        userId: "",
        name: "",
        email: "",
        phone: "",
        role: "",
        loginType: "",
    },
    reducers : {
        addSessionInfo: (state, action) => {
            state.memberId = action.payload.memberId;
            state.userId = action.payload.userId;
            state.name = action.payload.name;
            state.email = action.payload.email;
            state.phone = action.payload.phone;
            state.role = action.payload.role;
            state.loginType = action.payload.loginType;
        },
        deleteSessionInfo: (state) => ({
            memberId: "",
            userId: "",
            name: "",
            email: "",
            phone: "",
            role: "",
            loginType: "",
        }),
    }
})

export let {addSessionInfo, deleteSessionInfo} = SessionInfo.actions;

export default SessionInfo;