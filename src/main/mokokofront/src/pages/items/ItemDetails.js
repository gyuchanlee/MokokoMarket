import DefaultLayout from "../../layout/DefaultLayout";
import ItemDetailsDefaultInfo from "../../components/ItemDetailsDefaultInfo";

const ItemDetails = () => {

    return (
        <>
            <DefaultLayout>
                <div>
                    <h1>상품 상세 보기</h1>
                </div>
                <ItemDetailsDefaultInfo/>
            </DefaultLayout>
        </>
    )
}

export default ItemDetails;