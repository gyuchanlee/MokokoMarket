import {Container, Row} from "react-bootstrap";
import {useState} from "react";
import data from "../tempData/data.js"
import ItemCard from "../components/ItemCard";

const Main = (props) => {

    let navigate = props.navigate;
    // 메인에 보여줄 리스트
    const [items, setItems] = useState(data);


    return (
        <>
            <div className='main-bg'></div>
            <div>
                <Container>
                    <Row md={4}>
                        {
                            items.map((item, index) => {
                                return (
                                    <ItemCard item={item} navigate={navigate} key={item.id}></ItemCard>
                                )
                            })

                        }
                    </Row>
                </Container>
            </div>
        </>
    )
 }
 
 export default Main;