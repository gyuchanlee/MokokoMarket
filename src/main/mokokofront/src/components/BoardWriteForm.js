import {Button, Col, Form, Row} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import React, {useRef} from "react";
import {writeBoard} from "../api/axios";
import './../css/BoardWriteForm.css';
import TuiEditor from "./TuiEditor";

const BoardWriteForm = () => {

    const boardCategory = useRef('');
    const boardTitle = useRef('');
    const boardContent = useRef('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        // Handle form submission, e.g., send data to the server
        console.log('Board Data:', boardCategory.current, boardTitle.current, boardContent.current );
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
            navigate(`/boards`);
        } else {
            alert('글 작성 실패');
        }
    };

    const handleEditorChange = (value) => {
        boardContent.current = value;
    }

    return (
        <div className="board-form-container">
            <Form onSubmit={handleSubmit} className="board-form">
                <Form.Group controlId="boardCategory">
                    <Form.Label>카테고리</Form.Label>
                    <Form.Control
                        type="text"
                        ref={boardCategory}
                        onChange={(e) => {
                            boardCategory.current = e.target.value
                        }}
                    />
                </Form.Group>

                <Form.Group controlId="boardTitle">
                    <Form.Label>제목</Form.Label>
                    <Form.Control
                        type="text"
                        ref={boardTitle}
                        onChange={(e) => {
                            boardTitle.current = e.target.value
                        }}
                    />
                </Form.Group>

                <Form.Group controlId="boardContent">
                    <Form.Label>내용</Form.Label>
                    <TuiEditor
                        boardContent={boardContent}
                        onChange={handleEditorChange}
                    />
                </Form.Group>
                <Row className="justify-content-center mt-4">
                    <Col className="d-flex justify-content-between">
                        <Button variant="dark" type="submit" className="submit-btn">
                            등록 완료
                        </Button>
                        <Button onClick={() => {navigate("/boards")}} className="btn btn-secondary back-btn">
                            뒤로 가기
                        </Button>
                    </Col>
                </Row>
            </Form>
        </div>
    );
}

export default BoardWriteForm;