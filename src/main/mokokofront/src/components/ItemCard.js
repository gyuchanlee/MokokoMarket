import {Col} from "react-bootstrap";

const ItemCard = (props) => {
    return (
        <Col>
            <img src={props.item.imageSource} width="80%" alt="dd" onClick={() => props.navigate('/items/'+props.item.itemId)} />
            <h4>{props.item.title}</h4>
            <p style={{fontWeight : "bold"}}>{props.item.content}</p>
            <p>가격 : {props.item.price}</p>
            <p>수량 : {props.item.quantity}</p>
            <p>브랜드 : {props.item.brand}</p>
        </Col>
    )
}

export default ItemCard;