import React, {useEffect, useState} from 'react';
import { Container, Row, Col, Table, Form, Button } from 'react-bootstrap';
import axios from "axios";
import {useParams} from "react-router-dom";

const BoardDetails = () => {

    // const isOwnBoard = board.memberName === sessionStorage.getItem('name') && board.memberPk === memberPk;

    let {id} = useParams();

    const [board, setBoard] = useState({});

    useEffect(() => {
        axios.get('http://localhost:8080/boards/'+id)
            .then((response) => {
                setBoard(response.data);
            });
    }, []);

    const handleDelete = () => {
        if (window.confirm('정말로 삭제하시겠습니까? 한번 삭제하면 다시 되돌릴 수 없습니다.')) {
            // 삭제 axios 날리기
            alert("삭제 했다 쳐")
        }
    };

    return (
        <Container className="board-container">
            <h1>글 상세 내용 보기</h1>
            <br />
            <br />

            <Table>
                <tbody>
                <tr>
                    <th>작성일</th>
                    <td>{new Date(board.createdDateTime).toLocaleDateString('ko-KR')}</td>
                    <th>수정일</th>
                    <td>{new Date(board.modifiedDateTime).toLocaleDateString('ko-KR')}</td>
                </tr>
                <tr>
                    <th>글번호</th>
                    <td>{board.boardId}</td>
                    <th>카테고리</th>
                    <td>{board.category}</td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td colSpan="3" style={{ textAlign: 'center' }}>{board.title}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td colSpan="3" style={{ textAlign: 'center' }}>{board.memberId}</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colSpan="3">
                        <Form.Control as="textarea" value={board.content} readOnly style={{ height: '300px' }} />
                    </td>
                </tr>
                </tbody>
            </Table>
            <br />
            <br />

            <Row>
                <Col>
                    <a href="/community" className="small" style={{ textDecoration: 'none', color: 'black', fontWeight: 'bold' }}>목록으로</a>
                    {/*{isOwnBoard && (*/}
                        <>
                            <span className="mx-1">&middot;</span>
                            <a href="/" className="small" style={{ textDecoration: 'none', color: 'black', fontWeight: 'bold' }}>글 수정</a>
                            <span className="mx-1">&middot;</span>
                            <Button variant="link" className="small" style={{ textDecoration: 'none', color: 'black', fontWeight: 'bold' }} onClick={handleDelete}>글 삭제</Button>
                        </>
                    {/*)}*/}
                </Col>
            </Row>
        </Container>
    );
};

export default BoardDetails;