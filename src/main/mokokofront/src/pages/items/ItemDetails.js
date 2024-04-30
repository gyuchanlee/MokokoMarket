import {useParams} from "react-router-dom";
import {Button, Col, Container, Row} from "react-bootstrap";
import data from "../../tempData/data.js";
import {useDispatch} from "react-redux";
import {addItemToCart} from "../../store/CartInfoSlice";

const ItemDetails = () => {

    const {id} = useParams();
    // axios -> 아이템 정보 가져오기 대신 data.js 임시
    const item = data.find(item => item.id == id);

    let dispatch = useDispatch();

    return (
        <>
            <div>
                <h1>상품 상세 보기</h1>
            </div>
            <Container>
                <Row md={2}>
                    <Col>
                        <img src={item.src} />
                        <p>{item.title}</p>
                    </Col>
                    <Col>
                        <br/>
                        <br/>
                        <br/>
                        <h1>{item.title}</h1>
                        <h4>{item.content}</h4>
                        <p>{item.price} 원</p>
                        <br/>
                        <Button variant="success" onClick={() => dispatch(addItemToCart(item))}>
                            주문 담기
                        </Button>{' '}
                    </Col>
                </Row>
            </Container>
        </>
    )
}

export default ItemDetails;