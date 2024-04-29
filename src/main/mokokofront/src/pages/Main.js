import {Container, Row} from "react-bootstrap";
import {useState} from "react";
import data from "../tempData/data.js"
import ItemCard from "../components/ItemCard";
import {useNavigate} from "react-router-dom";
import NavLayout from "../layout/NavLayout";
import FooterLayout from "../layout/FooterLayout";

const Main = () => {

    let navigate = useNavigate();
    // 메인에 보여줄 리스트
    const [items, setItems] = useState(data);


    return (
        <>
            <NavLayout/>

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

            <FooterLayout/>
        </>
    )
 }
 
 export default Main;