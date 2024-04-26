import {configureStore, createSlice} from '@reduxjs/toolkit';
import CartInfo from "./store/CartInfoSlice";

let example = createSlice({
    name: 'example',
    initialState: '예시 기본 string',
    reducers : {
        setExample: (state, action) => {
            state.example = action.payload;
        }
    }
});

export let { setExample }  = example.actions;

export default configureStore({
    reducer: {
        example : example.reducer,
        CartInfo : CartInfo.reducer
    }
})