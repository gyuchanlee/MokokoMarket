import axios from "axios";

// async - await : 비동기처럼 쓰고 싶을 때 (ES6임) -> 페이지 로딩될때 순서 보장이 필요할때 쓰면 좋을듯 (대부분 이거 쓰면 될듯함)
// 이거 끌어다 쓰는 쪽 BoardList.js 나 BoardDetails.js 이런 쪽에서 적용해서 쓰면 좋을듯
const API_SERVER_HOST = `http://localhost:8080`;


// Board 한 건 조회
export const getBoardOne = (id) => {
    return axios.get(`${API_SERVER_HOST}/boards/${id}`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            throw error; // 에러를 다시 던져서 호출한 쪽에서 처리할 수 있도록 함
        });
}

// Board 리스트 조회
export const getBoardList = () => {
    return axios.get(`${API_SERVER_HOST}/boards`)
        .then((response) => {
            return response.data;
        })
        .catch((e) => {
            console.error(e);
            throw e;
        });
}

// Board 생성
export const writeBoard = (dto) => {
    return axios.post(`${API_SERVER_HOST}/boards`, {
        title: dto.title,
        content: dto.content,
        category: dto.category,
        level: dto.level,
        memberId: dto.memberId,
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
        });
}

// Board 수정
export const updateBoard = (dto, id) => {
    return axios.put(`${API_SERVER_HOST}/boards/${id}`, {
        title: dto.title,
        content: dto.content,
        category: dto.category,
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
        });
}

// Board 삭제
export const deleteBoard = (id) => {
    return axios.delete(`${API_SERVER_HOST}/boards/${id}`)
        .then((response) => {
            return response.data;
        })
        .catch((e) => {
            console.error(e);
            throw e;
        });
}

// login 로직 Post
export const login = (dto) => {
    return axios.post(`${API_SERVER_HOST}/login`, {
        username: dto.username,
        password: dto.password,
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
        });
}