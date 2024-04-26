import {Col} from "react-bootstrap";

const ItemCard = (props) => {
    return (
        <Col>
            <img src={props.item.src} width="80%" alt="dd" onClick={() => props.navigate('/items/'+props.item.id)} />
            <h4>{props.item.title}</h4>
            <p style={{fontWeight : "bold"}}>{props.item.content}</p>
            <p>{props.item.price}</p>
        </Col>
    )
}

export default ItemCard;