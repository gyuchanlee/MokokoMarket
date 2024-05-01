import {Button, Card, Form, Container} from "react-bootstrap";
import React, {useState} from "react";
import './../css/login.css'
import {login} from "../api/axios";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {addSessionInfo} from "../store/SessionInfoSlice";

const LoginForm = message => {

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const dispatch = useDispatch();

    const handleSubmit = async (e) => {
        e.preventDefault();
        // 로그인 axios
        const result = await login({username, password});
        console.log('result = ', result);
        if (result.userId === undefined) {
            alert('로그인 실패');
            window.location.reload();
        } else {
            await dispatch(addSessionInfo(result));
            alert('로그인 성공');
            navigate('/');
        }
    };

    return (
        <Container className="d-flex justify-content-center align-items-center vh-100">
            <Card className="login-card">
                <Card.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="formUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group controlId="formPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Enter password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </Form.Group>

                        <Button variant="primary" type="submit" className="submit-btn">
                            Login
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    )
}

export default LoginForm;