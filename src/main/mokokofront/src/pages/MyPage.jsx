import DefaultLayout from "../layout/DefaultLayout";
import {useParams} from "react-router-dom";
import OrderInfo from "../components/OrderInfo";
import MemberInfo from "../components/MemberInfo";
import {useSelector} from "react-redux";

const MyPage = () => {

    const {id} = useParams();
    const memberId = useSelector((state) => state.SessionInfo).memberId;
    
    return (
        <DefaultLayout>
            <>
                <div>
                    <br/>
                    <h2>마이 페이지</h2>
                    <br/>
                </div>
                <OrderInfo memberId={id} />
                <MemberInfo memberId={id} />
            </>
        </DefaultLayout>
    );
}

export default MyPage;