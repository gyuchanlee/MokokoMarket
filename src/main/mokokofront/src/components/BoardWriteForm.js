import {Button, Col, Form, Row} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import React, {useRef} from "react";
import {writeBoard} from "../api/axios";
import './../css/BoardWriteForm.css';
import TuiEditor from "./TuiEditor";
import {useSelector} from "react-redux";

const BoardWriteForm = () => {

    const boardCategory = useRef('');
    const boardTitle = useRef('');
    const boardContent = useRef('');
    const navigate = useNavigate();
    const memberId = useSelector(state => state.SessionInfo).memberId;

    const handleSubmit = async (e) => {
        e.preventDefault();

        let dto = {
            title : boardTitle.current,
            content: boardContent.current,
            category: boardCategory.current,
            level: 0,
            memberId: memberId,
        }

        const complete = await writeBoard(dto);

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