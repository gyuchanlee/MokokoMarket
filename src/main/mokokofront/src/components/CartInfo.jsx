import {Button, Form, Row, Col, ListGroup, Card} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import {resetCart} from "../store/CartInfoSlice";
import {createOrder} from "../api/axios";
import {useState} from "react";

const CartInfo = () => {

    const cartInfo = useSelector((state) => state.CartInfo);
    const sessionInfo = useSelector((state) => state.SessionInfo);
    const dispatch = useDispatch();
    const [requests, setRequests] = useState("");
    const [paymentMethod, setPaymentMethod] = useState("현금");

    const getCartItem = () => {
        let cartList = [];
        for (const cart of cartInfo) {
            let cartCreateDto = {
                memberId : sessionInfo.memberId,
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
            memberId : sessionInfo.memberId,
            paymentMethod : paymentMethod,
            totalPrice: tot,
            requests: requests,
            orderStatus: "READY",
            cartList: cartList,
        };

        const result = await createOrder(dto, sessionInfo.accessToken);

        if (result) {
            alert(`주문해주셔서 감사합니다`);
        } else {
            alert(`주문에 실패했습니다`);
        }
        // redux persist CartInfo 초기화
        dispatch(resetCart());
    }

    return (
        <Row>
            <Col md={8}>
                <Card>
                    <Card.Header>
                        <h4>장바구니</h4>
                    </Card.Header>
                    <ListGroup variant="flush">
                        {cartInfo.map((cart) => (
                            <ListGroup.Item key={cart.itemId}>
                                <Row>
                                    <Col xs={6}>
                                        <strong>{cart.title}</strong>
                                    </Col>
                                    <Col xs={3}>수량: {cart.count}</Col>
                                    <Col xs={3}>
                                        <Button variant="outline-secondary" size="sm">
                                            삭제
                                        </Button>
                                    </Col>
                                </Row>
                            </ListGroup.Item>
                        ))}
                    </ListGroup>
                </Card>
            </Col>
            <Col md={4}>
                <Card>
                    <Card.Header>
                        <h4>주문 정보</h4>
                    </Card.Header>
                    <Card.Body>
                        <Form.Group controlId="requests">
                            <Form.Label>요구사항</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                value={requests}
                                onChange={(e) => setRequests(e.target.value)}
                                placeholder="주문 시 요구사항을 입력해주세요"
                            />
                        </Form.Group>
                        <Form.Group controlId="paymentMethod">
                            <Form.Label>결제수단</Form.Label>
                            <Row>
                                <Col md={{ span: 4, offset: 4 }}>
                                    <Form.Control
                                        style={{ textAlign: "center" }}
                                        as="select"
                                        value={paymentMethod}
                                        onChange={(e) => setPaymentMethod(e.target.value)}
                                    >
                                        <option value="현금">현금</option>
                                        <option value="카드">카드</option>
                                        <option value="계좌이체">계좌이체</option>
                                    </Form.Control>
                                </Col>
                            </Row>
                        </Form.Group>
                        <br/>
                        <Button variant="outline-secondary" block onClick={pay}>
                            결제하기
                        </Button>
                    </Card.Body>
                </Card>
            </Col>
        </Row>
    );
}

export default CartInfo;