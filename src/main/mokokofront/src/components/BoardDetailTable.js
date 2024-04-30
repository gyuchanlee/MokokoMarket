import {Button, Col, Form, Row, Table} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {deleteBoard, getBoardOne} from "../api/axios";
import {useNavigate, useParams} from "react-router-dom";

const BoardDetailTable = () => {

    // const isOwnBoard = board.memberName === sessionStorage.getItem('name') && board.memberPk === memberPk;
    const {id} = useParams();
    const navigate = useNavigate();

    const [board, setBoard] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getBoardOne(id); // getBoardList 함수를 호출하여 데이터를 가져옴
                setBoard(data); // 가져온 데이터를 상태에 설정
            } catch (error) {
                console.error('Error fetching board one:', error);
            }
        };
        fetchData();
    }, []);

    const handleDelete = async () => {
        if (window.confirm('정말로 삭제하시겠습니까? 한번 삭제하면 다시 되돌릴 수 없습니다.')) {
            // 삭제 axios 날리기
            const isDeleted = await deleteBoard(id);
            if (isDeleted) {
                alert('삭제되었습니다')
                navigate(`/boards`);
            } else {
                alert('삭제 실패했습니다')
            }
        }
    };

    return (
        <>
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
                    <a href="/boards" className="small" style={{ textDecoration: 'none', color: 'black', fontWeight: 'bold' }}>목록으로</a>
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
        </>
    )
}

export default BoardDetailTable;