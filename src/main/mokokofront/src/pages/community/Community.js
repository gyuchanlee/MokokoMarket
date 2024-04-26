import {useEffect, useState} from "react";
import Container from "react-bootstrap/Container";
import {Col, Pagination, Row, Table} from "react-bootstrap";
import {Link} from "react-router-dom";
import axios from "axios";
// import boardList from "../../tempData/boardList";

const Community = () => {

    let [boardList, setBoardList] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/boards')
            .then((res) => {
                console.log(res.data);
                setBoardList(res.data);
            })
    }, [])

    return (
        <Container className="board-container">
            <h1>자유 게시판</h1>
            <br />
            <br />
            <Row className="align-items-center justify-content-between flex-column flex-sm-row">
                <Col xs="auto">
                    <div className="small m-0"></div>
                </Col>
                <Col xs="auto">
                    <span className="mx-1">&middot;</span>
                    <Link to="/boards/insert" className="small" style={{ textDecoration: 'none', color: 'black' }}>
                        글 쓰기
                    </Link>
                    <span className="mx-1">&middot;</span>
                </Col>
            </Row>
            <br />
            <br />
            <Table bordered hover>
                <thead>
                <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>카테고리</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>수정일</th>
                </tr>
                </thead>
                <tbody>
                {
                    boardList.map((board) => (
                        <tr key={board.boardId} className="board-item" onClick={() => window.location.href = `/community/${board.boardId}`} style={{ cursor: 'pointer' }}>
                            <td>{board.boardId}</td>
                            <td>
                                {board.level > 0 && <span>[답글]</span>}
                                <p>{board.title}</p>
                            </td>
                            <td>{board.category}</td>
                            <td>{board.memberId}</td>
                            <td>{new Date(board.createdDateTime).toLocaleDateString('ko-KR')}</td>
                            <td>{new Date(board.modifiedDateTime).toLocaleDateString('ko-KR')}</td>
                        </tr>
                    ))
                }
                </tbody>
            </Table>
            <br/>
            <br/>
            <Pagination>
                <Pagination.Prev/>
                    <Pagination.Item key={1} active={false}>
                        {1}
                    </Pagination.Item>
                    <Pagination.Item key={2} active={false}>
                        {2}
                    </Pagination.Item>
                    <Pagination.Item key={3} active={false}>
                        {3}
                    </Pagination.Item>
                <Pagination.Next/>
            </Pagination>
        </Container>
    );
}

export default Community;