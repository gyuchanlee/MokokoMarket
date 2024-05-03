import {Button, Table} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {resetCart} from "../store/CartInfoSlice";
import {createOrder} from "../api/axios";

const CartInfo = () => {

    const cartInfo = useSelector((state) => state.CartInfo);

    const dispatch = useDispatch();

    const getCartItem = () => {
        let cartList = [];
        for (const cart of cartInfo) {
            let cartCreateDto = {
                memberId : 1, // 임시
                itemId : cart.itemId,
                count : cart.count,
            };
            cartList.push(cartCreateDto);
        }
        return cartList;

    }

    const getTotalPrice = () => {
        let result = 0;
        for (const cart of cartInfo) {
            result += cart.price;
        }
        return result;
    }

    const pay = async () => {

        // axios 장바구니 상품 결제 및 주문 넣기 (일단 주문 생성 axios 먼저)
        // 주문 등록 post axios

        let tot = getTotalPrice();
        let cartList = getCartItem();
        const dto = {
            memberId : 1, // 임시
            paymentMethod : 1,
            totalPrice: tot,
            requests: "신속 배달 부탁",
            orderStatus: "READY",
            cartList: cartList,
        };

        const result = await createOrder(dto);

        console.log(`결제 및 주문 넣기 수행 여부 = ${result}`);
        alert(`결제 및 주문 넣기 수행 여부 = ${result}`);

        // redux persist CartInfo 초기화
        dispatch(resetCart());
    }

    return (
        <>
            <Table>
                <thead>
                <tr>
                    <th>번호</th>
                    <th>상품명</th>
                    <th>수량</th>
                    <th>변경하기</th>
                </tr>
                </thead>
                <tbody>
                {
                    cartInfo.map((cart) => {
                        return (
                            <tr key={cart.itemId}>
                                <td>{cart.itemId}</td>
                                <td>{cart.title}</td>
                                <td>{cart.count}</td>
                                <td>
                                    <button onClick={() => {alert("반갑다")}}>
                                        그냥버튼
                                    </button>
                                </td>
                            </tr>
                        )
                    })
                }
                </tbody>
            </Table>
            <Button onClick={pay}>
                결제하기
            </Button>
        </>
    );
}

export default CartInfo;