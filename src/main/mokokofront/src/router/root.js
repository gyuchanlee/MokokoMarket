import React, {lazy, Suspense} from 'react';


const { createBrowserRouter } = require('react-router-dom');

const Loading = () => <>Loading...</>;
const Main = lazy(() => import('./../pages/Main'));
const BoardDetails = lazy(() => import('./../pages/community/BoardDetails'));
const BoardWrite = lazy(() => import('./../pages/community/BoardWrite'));
const Cart = lazy(() => import('./../pages/Cart'));
const ItemListPerBrand = lazy(() => import('./../pages/ItemListPerBrand'));
const ItemDetails = lazy(() => import('./../pages/ItemDetails'));
const Community = lazy(() => import('./../pages/community/Community'));

const root = createBrowserRouter([
    {
      path:"",
        element:<Suspense fallback={Loading}><Main/></Suspense>
    },
    {
        path:"/community",
        element:<Suspense fallback={Loading}><Community/></Suspense>
    },
    {
        // 나중에 children: communityRouter()로 빼기
        path:"/community/:id",
        element:<Suspense fallback={Loading}><BoardDetails/></Suspense>
    },
    {
        path:"/community/write",
        element:<Suspense fallback={Loading}><BoardWrite/></Suspense>
    },
    {
        path:"/cart",
        element:<Suspense fallback={Loading}><Cart/></Suspense>
    },
    {
        path:"/items",
        element:<Suspense fallback={Loading}><ItemListPerBrand/></Suspense>
    },
    {
        path:"/items/:id",
        element:<Suspense fallback={Loading}><ItemDetails/></Suspense>
    },
    {
        path:"/items/bestSeller",
        element:<Suspense fallback={Loading}>
            <div>여기 베스트 상품 리스트</div>
        </Suspense>
    },
    {
        path:"*",
        element: <Suspense fallback={Loading}>
            <div>400 Error</div>
        </Suspense>
    },
]);


export default root;