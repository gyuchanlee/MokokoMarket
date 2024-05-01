import {Container, Row} from "react-bootstrap";
import {useState} from "react";
import data from "../tempData/data.js"
import ItemCard from "../components/ItemCard";
import {useNavigate} from "react-router-dom";
import NavLayout from "../layout/NavLayout";
import FooterLayout from "../layout/FooterLayout";
import DefaultLayout from "../layout/DefaultLayout";
import {useSelector} from "react-redux";

const Main = () => {

    let navigate = useNavigate();
    // 메인에 보여줄 리스트
    const [items, setItems] = useState(data);
    const sessionInfo = useSelector((state) => {
        console.log('state = ', state)
        console.log('state.session = ', state.SessionInfo);
        return state.SessionInfo;
    });
    console.log(sessionInfo);

    return (
        <DefaultLayout>
            <div className='main-bg'></div>
            <div>
                <h1>세션 확인용</h1>
                <h4>{sessionInfo.name}</h4>
                <h4>{sessionInfo.memberId}</h4>
                <h4>{sessionInfo.userId}</h4>
                <h4>{sessionInfo.email}</h4>
            </div>
            <div>
                <Container>
                    <Row md={4}>
                        {
                            items.map((item) => {
                                return (
                                    <ItemCard item={item} navigate={navigate} key={item.id}></ItemCard>
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