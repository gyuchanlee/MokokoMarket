import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import NavLayout from "./layout/NavLayout";
import FooterLayout from "./layout/FooterLayout";
import 'react-bootstrap';
import {Route, Routes, useNavigate} from "react-router-dom";
import {Suspense} from "react";
import Main from "./pages/Main";
import ItemListPerBrand from "./pages/ItemListPerBrand";
import ItemDetails from "./pages/ItemDetails";
import Cart from "./pages/Cart";
import Community from "./pages/community/Community";
import BoardDetails from "./pages/community/BoardDetails";

function App() {

    let navigate = useNavigate();

  return (
    <div className="App">
      <NavLayout navigate={navigate}></NavLayout>

        <div className="App-content wrapper">
            <Suspense fallback={<div>Loading...</div>}>
                <Routes>
                    <Route path="/" element={<Main navigate={navigate}/>}></Route>
                    <Route path="/community" element={<Community/>}></Route>
                    <Route path="/community/:id" element={<BoardDetails/>}></Route>
                    <Route path="/cart" element={<Cart/>}></Route>
                    <Route path="/items" element={<ItemListPerBrand/>}></Route>
                    <Route path="/items/bestSeller" element={<div>여기 베스트 상품 리스트</div>}></Route>
                    <Route path="/items/:id" element={<ItemDetails/>}></Route>
                    <Route path="*" element={<div>400 Error</div>}></Route>
                </Routes>
            </Suspense>
        </div>

        <FooterLayout className='footer' navigate={navigate}></FooterLayout>
    </div>
  );
}

export default App;
