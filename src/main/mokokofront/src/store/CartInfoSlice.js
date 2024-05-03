import { createSlice } from '@reduxjs/toolkit'

const CartInfo = createSlice({
    name: 'CartInfo',
    initialState : [],
    reducers : {
        addItemToCart: (state, action) => {

            let find = state.findIndex(cart => cart.itemId === action.payload.itemId); // 못찾으면 -1 나오는듯
            console.log('find =', find);
            if (find !== -1) {
                if (state[find].count >= action.payload.quantity) {
                    alert(`재고가 부족합니다.`);
                    return;
                }
                state[find].count += 1;
                console.log(state[find].count);
                console.log(action.payload.quantity);
                alert(`장바구니에 더 담았습니다. 총 개수 = ${state[find].count}`);
                return;
            }

            let newItem = {
                itemId : action.payload.itemId,
                title : action.payload.title,
                content : action.payload.content,
                imageSource : action.payload.imageSource,
                brand : action.payload.brand,
                price: action.payload.price,
                count : 1
            };

            state.push(newItem);
            alert('장바구니에 담았습니다');
        },
        resetCart: () => []
    }
})

export let {addItemToCart, resetCart} = CartInfo.actions;

export default CartInfo;