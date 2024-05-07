import {Container, Row} from "react-bootstrap";
import {useEffect, useState} from "react";
import ItemCard from "../components/ItemCard";
import {useNavigate} from "react-router-dom";
import DefaultLayout from "../layout/DefaultLayout";
import {getItems} from "../api/axios";

const Main = () => {

    let navigate = useNavigate();
    // 메인에 보여줄 리스트 ....
    const [items, setItems] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getItems();
                setItems(data);
            } catch (error) {
                console.error('Failed to fetch item data:', error);
            }
        }
        fetchData();
    }, []);

    return (
        <DefaultLayout>
            <div className='main-bg'></div>
            <div>
                <Container>
                    <Row md={4}>
                        {
                            items.map((item) => {
                                return (
                                    <ItemCard item={item} navigate={navigate} key={item.itemId}></ItemCard>
                                )
                            })
                        }
                    </Row>
                </Container>
            </div>
        </DefaultLayout>
    )
}

export default Main;