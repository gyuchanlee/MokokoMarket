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
                    <h4>여기는 마이 페이지 - 주문 내역, 주문 취소 기능, 옆에 간단하게 회원 정보 보여주기</h4>
                    <p>{id}</p>
                </div>
                <OrderInfo/>
                <MemberInfo/>
            </>
        </DefaultLayout>
    );
}

export default MyPage;