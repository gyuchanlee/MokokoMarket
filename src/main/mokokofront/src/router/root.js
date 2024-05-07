import React, {lazy, Suspense} from 'react';
import Login from "../pages/login/Login";
import ItemListBestSeller from "../pages/items/ItemListBestSeller";


const { createBrowserRouter } = require('react-router-dom');

const Loading = () => <>Loading...</>;
const CheckLoginRoute = lazy(() => import('./CheckLoginRoute'));
const Main = lazy(() => import('./../pages/Main'));
const BoardDetails = lazy(() => import('../pages/boards/BoardDetails'));
const BoardWrite = lazy(() => import('../pages/boards/BoardWrite'));
const Cart = lazy(() => import('./../pages/Cart'));
const ItemListPerBrand = lazy(() => import('../pages/items/ItemListPerBrand'));
const ItemDetails = lazy(() => import('../pages/items/ItemDetails'));
const BoardList = lazy(() => import('../pages/boards/BoardList'));
const Error400 = lazy(() => import('./../pages/Error400'));
const MemberJoin = lazy(() => import('./../pages/members/MemberJoin'));
const MyPage = lazy(() => import('./../pages/MyPage'));

const root = createBrowserRouter([
    {
      path:"",
        element:<Suspense fallback={Loading}><Main/></Suspense>
    },
    {
        path:"/login",
        element:<Suspense fallback={Loading}><Login/></Suspense>
    },
    // 나중에 children: communityRouter()로 빼기
    {
        path:"/boards",
        element:<Suspense fallback={Loading}><BoardList/></Suspense>
    },
    {
        path:"/boards/:id",
        element:<Suspense fallback={Loading}><BoardDetails/></Suspense>
    },
    {
        path:"/boards/write",
        element:<Suspense fallback={Loading}><CheckLoginRoute Component={<BoardWrite/>}></CheckLoginRoute></Suspense>
    },
    {
        path:"/cart",
        element:<Suspense fallback={Loading}><CheckLoginRoute Component={<Cart/>}></CheckLoginRoute></Suspense>
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
        element:<Suspense fallback={Loading}><ItemListBestSeller/></Suspense>
    },
    {
        path:"/members/join",
        element:<Suspense fallback={Loading}><MemberJoin/></Suspense>
    },
    {
        path:"/myPage/:id",
        element:<Suspense fallback={Loading}><CheckLoginRoute Component={<MyPage/>}></CheckLoginRoute></Suspense>
    },
    {
        path:"*",
        element: <Suspense fallback={Loading}><Error400/></Suspense>
    },
]);


export default root;