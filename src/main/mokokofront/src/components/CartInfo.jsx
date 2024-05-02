import {Table} from "react-bootstrap";
import {useSelector} from "react-redux";

const CartInfo = () => {

    let cartInfo = useSelector((state) => state.CartInfo);

    return (
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
                        <tr key={cart.id}>
                            <td>{cart.id}</td>
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
    );
}

export default CartInfo;