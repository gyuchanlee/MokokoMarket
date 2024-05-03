import axios from "axios";

// async - await : 비동기처럼 쓰고 싶을 때 (ES6임) -> 페이지 로딩될때 순서 보장이 필요할때 쓰면 좋을듯 (대부분 이거 쓰면 될듯함)
// 이거 끌어다 쓰는 쪽 BoardList.js 나 BoardDetails.js 이런 쪽에서 적용해서 쓰면 좋을듯
const API_SERVER_HOST = `http://localhost:8080`;
const defaultHeaders = {
    "Content-Type": "application/x-www-form-urlencoded",
    "Access-Control-Allow-Origin": `http://localhost:3000`,
    'Access-Control-Allow-Credentials':"true",
}

const jsonHeaders = {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": `http://localhost:3000`,
    'Access-Control-Allow-Credentials':"true",
}

// Axios 기본 설정 -> login 요청 후, session 값 받아오기 (세션 값 받아오기 위한 설정)
axios.defaults.withCredentials = true; // withCredentials 전역 설정
axios.defaults.baseURL = API_SERVER_HOST;

// Board 한 건 조회
export const getBoardOne = (id) => {
    return axios.get(`${API_SERVER_HOST}/boards/${id}`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// Board 리스트 조회
export const getBoardList = () => {
    return axios.get(`${API_SERVER_HOST}/boards`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
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
            return error.response.data;
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
            return error.response.data;
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
            return e.response.data;
        });
}

// login 로직 Post
export const login = (dto) => {
    return axios.post(`${API_SERVER_HOST}/login`, {
        username: dto.username,
        password: dto.password,
    }, {
        headers : defaultHeaders
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// logout 로직 post
export const logout = () => {
    return axios.post(`${API_SERVER_HOST}/logout`, {}, {
        headers : defaultHeaders
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// 회원 가입 POST -> 기본 사용자 가입 페이지용
export const joinMember = (dto) => {
    return axios.post(`${API_SERVER_HOST}/members`, {
        userId: dto.userId,
        name: dto.name,
        email: dto.email,
        phone: dto.phone,
        password: dto.password,
        role: 'USER',
        loginType: 'BASIC',
    }, {
        headers : jsonHeaders
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// 회원 수정 PUT
export const updateMember = (dto, id) => {
    return axios.put(`${API_SERVER_HOST}/members/${id}`, {
        name: dto.name,
        email: dto.email,
        phone: dto.phone,
        password: dto.password,
    }, {
        headers : jsonHeaders
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// 회원 탈퇴 DELETE
export const deleteMember = (id) => {
    return axios.delete(`${API_SERVER_HOST}/members/${id}`,{
        headers : jsonHeaders
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// 주문 리스트 조회
export const getOrder = () => {
    return axios.get(`${API_SERVER_HOST}/orders`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// 결제 완료 -> 주문 한 건 생성
export const createOrder = (dto) => {
    return axios.post(`${API_SERVER_HOST}/orders`, {
        memberId: dto.memberId,
        paymentMethod: dto.paymentMethod,
        totalPrice: dto.totalPrice,
        requests: dto.requests,
        orderStatus: dto.orderStatus,
        cartList: dto.cartList, // CartCreateDto 배열 형식이여야 함
    }, {
        headers : jsonHeaders
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// 회원 주문 한 건 취소
export const cancelOrder = (id) => {
    return axios.delete(`${API_SERVER_HOST}/orders/${id}`,{
        headers : jsonHeaders
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// 상품 리스트 전체 조회
export const getItems = () => {
    return axios.get(`${API_SERVER_HOST}/items`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}

// 상품 한건 조회
export const getItem = (id) => {
    return axios.get(`${API_SERVER_HOST}/items/${id}`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error(error);
            return error.response.data;
        });
}