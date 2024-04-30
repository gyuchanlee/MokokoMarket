import React from 'react';
import BoardWriteForm from "../../components/BoardWriteForm";
import DefaultLayout from "../../layout/DefaultLayout";

const BoardWrite = () => {

    return (
        <DefaultLayout>
            <h1 className="text-center mb-4">글 등록</h1>
            <BoardWriteForm/>
        </DefaultLayout>
    );
};

export default BoardWrite;