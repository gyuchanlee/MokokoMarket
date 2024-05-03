import React, {useState} from 'react';
import { Table } from 'react-bootstrap';

const OrderInfo = () => {

    const [orderData, setOrderData] = useState([]);

    return (
        <>
            <h1>주문 정보 및 취소</h1>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>주문 ID</th>
                    <th>지불 정보</th>
                    <th>총 결제 액수</th>
                    <th>요청 사항</th>
                    <th>주문 상태</th>
                    <th>주문자</th>
                    <th>주문 상품</th>
                </tr>
                </thead>
                <tbody>
                {orderData.map((order) => (
                    <tr key={order.orderId}>
                        <td>{order.orderId}</td>
                        <td>{order.paymentMethod}</td>
                        <td>{order.totalPrice}</td>
                        <td>{order.requests}</td>
                        <td>{order.status}</td>
                        <td>{order.userId}</td>
                        <td>
                            <ul>
                                {order.cartList.map((cart) => (
                                    <li key={cart.cartId}>{cart.title} (count: {cart.count}) (price: {cart.price})</li>
                                ))}
                            </ul>
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </>
    );
};

export default OrderInfo;