import React, {useEffect, useState} from 'react';
import {Button, Table} from 'react-bootstrap';
import axios from "axios";

const OrderInfo = () => {

    const [orderData, setOrderData] = useState([]);

    // useEffect(() => {
    //     // 서버에서 사용자의 주문 정보를 가져옵니다.
    //     axios.get(`/api/orders/${userId}`)
    //         .then(response => {
    //             setOrderData(response.data);
    //         })
    //         .catch(error => {
    //             console.error('Error fetching order data:', error);
    //         });
    // }, [userId]);

    const handleCancelOrder = (orderId) => {
        // 주문 취소 요청을 서버로 보냅니다.
        alert('주문 취소해주세요')
    };

    return (
        <>
            <h4>주문 정보 및 취소</h4>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>주문 ID</th>
                    <th>지불 정보</th>
                    <th>총 결제 액수</th>
                    <th>요청 사항</th>
                    <th>주문 상태</th>
                    <th>주문 상품</th>
                    <th>취소</th>
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
                        <td>
                            <ul>
                                {order.cartList.map((cart) => (
                                    <li key={cart.cartId}>
                                        {cart.title} (count: {cart.count}) (price: {cart.price})
                                    </li>
                                ))}
                            </ul>
                        </td>
                        <td>
                            {order.status !== '취소됨' && (
                                <Button variant="danger" onClick={() => handleCancelOrder(order.orderId)}>
                                    주문 취소
                                </Button>
                            )}
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </>
    );
};

export default OrderInfo;