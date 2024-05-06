import React, {useState} from 'react';
import { Container, Row, Col, Card, ListGroup, ListGroupItem } from 'react-bootstrap';
import './../css/memberInfo.css';

const MemberInfo = () => {

    const [member, setMember] = useState({
        memberId: 99,
        userId: 'dodo96',
        name: '김도도',
        email: 'dodobird@naver.com',
        phone: '010-1234-1234',
        createdDateTime: Date.now()
    });

    return (
        <Container className="user-info-container">
            <Row>
                <Col>
                    <Card>
                        <Card.Header as="h5">회원 정보</Card.Header>
                        <Card.Body>
                            <ListGroup variant="flush">
                                <ListGroupItem>
                                    <strong>ID:</strong> {member.userId}
                                </ListGroupItem>
                                <ListGroupItem>
                                    <strong>이름:</strong> {member.name}
                                </ListGroupItem>
                                <ListGroupItem>
                                    <strong>이메일:</strong> {member.email}
                                </ListGroupItem>
                                <ListGroupItem>
                                    <strong>전화번호:</strong> {member.phone}
                                </ListGroupItem>
                                <ListGroupItem>
                                    <strong>가입일:</strong> {member.createdDateTime}
                                </ListGroupItem>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default MemberInfo;