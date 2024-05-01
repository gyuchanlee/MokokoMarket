import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import {useNavigate} from "react-router-dom";
import {logout} from "../api/axios";

const NavLayout = () => {

    const navigate = useNavigate();

    const logoutLogic = async () => {
        const result = await logout();
        alert(result);
        // 로그 아웃 성공 -> 세션으로 받았던 정보들 초기화하기
    }
    
    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand onClick={ () => navigate('/') }>Mokoko Market</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link onClick={ () => navigate('/') }>MyPage</Nav.Link>
                        <Nav.Link onClick={ () => navigate('/cart') }>Cart</Nav.Link>
                        <Nav.Link onClick={ () => navigate('/boards') }>Community</Nav.Link>
                        <NavDropdown title="Shopping" id="basic-nav-dropdown">
                            <NavDropdown.Item onClick={ () => navigate('/items?brand=mokoko') }>모코코</NavDropdown.Item>
                            <NavDropdown.Item onClick={ () => navigate('/items?brand=manggom') }>
                                망그러진 곰
                            </NavDropdown.Item>
                            <NavDropdown.Item onClick={ () => navigate('/items?brand=ompangi') }>옴팡이</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item onClick={ () => navigate('/items/bestSeller') }>
                                Best Seller
                            </NavDropdown.Item>
                        </NavDropdown>
                        <Nav.Link onClick={ () => navigate('/login') }>Log In</Nav.Link>
                        <Nav.Link onClick={ () => logoutLogic() }>Log Out</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default NavLayout;