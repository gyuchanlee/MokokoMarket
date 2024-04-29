import Container from "react-bootstrap/Container";
import {NavItem} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";

const FooterLayout = (props) => {

    return (
        <Container>
            <footer className="py-3 my-4">
                <ul className="nav justify-content-center border-bottom pb-3 mb-3">
                    <NavItem><Link to={'/'} className="nav-link px-2 text-muted">Home</Link></NavItem>
                    <NavItem><Link to={'/'} className="nav-link px-2 text-muted">Features</Link></NavItem>
                    <NavItem><Link to={'/'} className="nav-link px-2 text-muted">Pricing</Link></NavItem>
                    <NavItem><Link to={'/'} className="nav-link px-2 text-muted">FAQs</Link></NavItem>
                    <NavItem><Link to={'/'} className="nav-link px-2 text-muted">About</Link></NavItem>
                </ul>
                <p className="text-center text-muted">© 2024 DoDoBird Company, Inc</p>
            </footer>
        </Container>
    )
}

export default FooterLayout;