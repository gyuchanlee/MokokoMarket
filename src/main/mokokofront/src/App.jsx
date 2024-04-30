import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-bootstrap';
import {RouterProvider} from "react-router-dom";
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
