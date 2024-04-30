import {useLocation, useSearchParams} from "react-router-dom";

const ItemListPerBrand = () => {

    const [searchParams, setSearchParams] = useSearchParams();
    const brand = searchParams.get('brand');
    
    return (
        <>
            <div>
                <h1>여기 브랜드별 상품 상세 및 상품들</h1>
                <h4>{brand} 브랜드의 리스트</h4>
            </div>
        </>
    )
}

export default ItemListPerBrand;