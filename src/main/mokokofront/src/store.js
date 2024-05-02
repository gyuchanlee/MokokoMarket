import {combineReducers, configureStore, createSlice} from '@reduxjs/toolkit';
import CartInfo from "./store/CartInfoSlice";
import SessionInfo from "./store/SessionInfoSlice";
import {persistReducer} from "redux-persist";
import storage from "redux-persist/lib/storage";

const reducers = combineReducers({
    CartInfo : CartInfo.reducer,
    SessionInfo : SessionInfo.reducer,
});

const persistConfig = {
    key: 'root',
    storage, // localStorage 기본 할당
    whitelist: [ 'CartInfo', 'SessionInfo' ],
};

const persistedReducers = persistReducer(persistConfig, reducers);

export default configureStore({
    reducer: persistedReducers
})