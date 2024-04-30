import React from 'react';
import {Container} from 'react-bootstrap';
import DefaultLayout from "../../layout/DefaultLayout";
import BoardDetailTable from "../../components/BoardDetailTable";

const BoardDetails = () => {

    return (
        <DefaultLayout>
            <Container className="board-container">
                <h1>글 상세 내용 보기</h1>
                <br />
                <br />
                <BoardDetailTable/>
            </Container>
        </DefaultLayout>
    );
};

export default BoardDetails;