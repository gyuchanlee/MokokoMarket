import {Button, Col, Container, Row} from "react-bootstrap";
import {addItemToCart} from "../store/CartInfoSlice";
import {useParams} from "react-router-dom";
import {useDispatch} from "react-redux";
import {useEffect, useState} from "react";
import {getItem} from "../api/axios";

const ItemDetailsDefaultInfo = () => {

    const {id} = useParams();
    // axios -> 아이템 정보 가져오기 대신 data.js 임시
    const [item, setItem] = useState({});

    const addCart = () => {
        if (item.quantity > 0) {
            dispatch(addItemToCart(item));
        } else {
            alert('재고가 부족합니다')
        }
    }
    
    useEffect(() => {
        const fetchData = async () => {
            try {
                const result = await getItem(id);
                setItem(result);
            } catch (error) {
                console.error('Failed to fetch item data:', error);
            }
        };
        fetchData();
    }, [id]);

    let dispatch = useDispatch();

    return (
        <Container>
            <Row md={2}>
                <Col>
                    <img src={item.imageSource} />
                    <p>{item.title}</p>
                </Col>
                <Col>
                    <br/>
                    <br/>
                    <br/>
                    <h1>{item.title}</h1>
                    <h2>{item.brand}</h2>
                    <h4>{item.content}</h4>
                    <p>{item.price} 원</p>
                    <p>{item.quantity} 개 남음</p>
                    <br/>
                    <Button variant="success" onClick={addCart}>
                        주문 담기
                    </Button>{' '}
                </Col>
            </Row>
        </Container>
    );
}

export default ItemDetailsDefaultInfo;