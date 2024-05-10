import React, {useEffect, useState} from 'react';
import {Button, Table} from 'react-bootstrap';
import {cancelOrder, getOrderByMemberId} from "../api/axios";
import {useSelector} from "react-redux";

const OrderInfo = ({ memberId }) => {

    const [orderData, setOrderData] = useState([]);
    const accessToken = useSelector((state) => state.SessionInfo).accessToken;


    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await getOrderByMemberId(memberId, accessToken);
                console.log(result);
                setOrderData(result);
            } catch (e) {
                console.error('Failed to fetch item data:', e);
            }
        }

        fetchData();
    }, [memberId]);

    const handleCancelOrder = async (orderId) => {
        // 주문 취소 요청을 서버로 보냅니다.
        const result = await cancelOrder(orderId, accessToken);
        if (result === true) {
            alert('주문을 취소했습니다.');
            // 주문 취소 후, 최신 주문 데이터 가져오기
            const updatedOrderData = await getOrderByMemberId(memberId, accessToken);
            // orderData 상태를 업데이트 -> OrderInfo 재렌더링
            setOrderData(updatedOrderData);
        } else {
            alert('이미 취소된 주문이거나 주문 취소 오류입니다');
        }
    };

    return (
        <>
            <h4>주문 정보 관리</h4>
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
                                        {cart.title} (주문개수: {cart.count}) (가격: {cart.price})
                                    </li>
                                ))}
                            </ul>
                        </td>
                        <td>
                            {order.status !== 'CANCELED' && (
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