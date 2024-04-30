import { createSlice } from '@reduxjs/toolkit'

let CartInfo = createSlice({
    name: 'CartInfo',
    initialState : [],
    reducers : {
        addItemToCart: (state, action) => {

            let find = state.findIndex(cart => cart.id === action.payload.id); // 못찾으면 -1 나오는듯
            console.log('find =', find);
            if (find !== -1) {
                state[find].count += 1;
                alert('장바구니에 더 담았수');
                return;
            }

            let newItem = {
                id : action.payload.id,
                title : action.payload.title,
                count : 1
            };

            state.push(newItem);
            alert('장바구니에 담았소');
        }
    }
})

export let {addItemToCart} = CartInfo.actions;

export default CartInfo;