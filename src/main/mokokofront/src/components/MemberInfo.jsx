import React, {useEffect, useState} from 'react';
import { Container, Row, Col, Card, ListGroup, ListGroupItem } from 'react-bootstrap';
import './../css/memberInfo.css';
import axios from "axios";
import {getMember} from "../api/axios";

const MemberInfo = ({ memberId }) => {

    const [member, setMember] = useState({
        // memberId: 99,
        // userId: 'dodo96',
        // name: '김도도',
        // email: 'dodobird@naver.com',
        // phone: '010-1234-1234',
        // createdDateTime: Date.now()
    });

    useEffect(() => {
        /**
         * - useEffect에 바로 async를 걸어야 하는 가??
         * React에서 useEffect 내부에서 직접 async/await를 사용하는 것은 권장되지 않습니다.
         * useEffect 훅은 함수를 반환해야 하지만, async 함수는 암묵적으로 promise를 반환하기 때문입니다.
         * @returns {Promise<void>}
         */
        const fetchData = async () => {
            try {
                const result = await getMember(memberId);
                console.log(result);
                setMember(result);
            } catch (e) {
                console.error('Failed to fetch member:', e);
            }
        };
        fetchData();
    }, []);

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
                                    <strong>가입일:</strong>
                                    {new Date(member.createdDateTime).toLocaleDateString('ko-KR', {year: 'numeric', month: 'numeric', day: 'numeric'})}
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