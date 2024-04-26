import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';

const NavLayout = (props) => {

    let navigate = props.navigate;

    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand onClick={ () => navigate('/') }>Mokoko Market</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link onClick={ () => navigate('/') }>MyPage</Nav.Link>
                        <Nav.Link onClick={ () => navigate('/cart') }>Cart</Nav.Link>
                        <Nav.Link onClick={ () => navigate('/community') }>Community</Nav.Link>
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
                        <Nav.Link onClick={ () => navigate('/') }>회원 가입</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default NavLayout;