import DefaultLayout from "../layout/DefaultLayout";
import {useParams} from "react-router-dom";
import OrderInfo from "../components/OrderInfo";
import MemberInfo from "../components/MemberInfo";

const MyPage = () => {

    const {id} = useParams();
    
    return (
        <DefaultLayout>
            <>
                <div>
                    <br/>
                    <h2>마이 페이지</h2>
                    <p>memberId = {id}</p>
                    <br/>
                </div>
                <OrderInfo/>
                <MemberInfo/>
            </>
        </DefaultLayout>
    );
}

export default MyPage;