import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import NavLayout from "./layout/NavLayout";
import FooterLayout from "./layout/FooterLayout";
import 'react-bootstrap';
import {Route, RouterProvider, Routes, useNavigate} from "react-router-dom";
import {Suspense} from "react";
import Main from "./pages/Main";
import ItemListPerBrand from "./pages/ItemListPerBrand";
import ItemDetails from "./pages/ItemDetails";
import Cart from "./pages/Cart";
import Community from "./pages/community/Community";
import BoardDetails from "./pages/community/BoardDetails";
import BoardWrite from "./pages/community/BoardWrite";
import root from "./router/root";

function App() {

  return (
    <div className="App">
        <div className="App-content wrapper">
            <RouterProvider router={root}/>
        </div>
    </div>
  );
}

export default App;
