import React, {useRef, useState} from 'react';
import { Container, Row, Col, Form, Button } from 'react-bootstrap';
import {Link, useNavigate} from "react-router-dom";
import {writeBoard} from "../../service/Axios";

const BoardWrite = () => {
    const boardCategory = useRef('');
    const boardTitle = useRef('');
    const boardContent = useRef('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        // Handle form submission, e.g., send data to the server
        console.log('Board Data:', { boardCategory, boardTitle, boardContent });
        let dto = {
            title : boardTitle.current,
            content: boardContent.current,
            category: boardCategory.current,
            level: 0,
            // 테스트용 회원
            memberId: 1,
        }
        const complete = await writeBoard(dto);
        console.log(complete);
        if (complete) {
            alert('글 작성 성공');
            navigate(`/community`);
        } else {
            alert('글 작성 실패');
        }
    };

    return (
        <Container className="board-container">
            <h1 className="text-center mb-4">문의 글 등록</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="boardCategory">
                    <Form.Label>카테고리</Form.Label>
                    <Form.Control
                        type="text"
                        ref={boardCategory}
                        onChange={(e) => { boardCategory.current = e.target.value}}
                    />
                </Form.Group>

                <Form.Group controlId="boardTitle">
                    <Form.Label>제목</Form.Label>
                    <Form.Control
                        type="text"
                        ref={boardTitle}
                        onChange={(e) => { boardTitle.current = e.target.value}}
                    />
                </Form.Group>

                <Form.Group controlId="boardContent">
                    <Form.Label>내용</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={6}
                        ref={boardContent}
                        onChange={(e) => { boardContent.current = e.target.value}}
                    />
                </Form.Group>

                <Row className="justify-content-center mt-4">
                    <Col className="d-flex justify-content-between">
                        <Button variant="primary" type="submit">
                            등록 완료
                        </Button>
                        <Link to="/community" className="btn btn-secondary">
                            뒤로 가기
                        </Link>
                    </Col>
                </Row>
            </Form>
        </Container>
    );
};

export default BoardWrite;