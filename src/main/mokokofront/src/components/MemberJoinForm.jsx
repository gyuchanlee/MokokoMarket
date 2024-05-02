import React, {useRef, useState} from 'react';
import Container from "react-bootstrap/Container";
import {Button, Card, Form} from "react-bootstrap";
import './../css/signup.css';
import {useNavigate} from "react-router-dom";
import {joinMember} from "../api/axios";

const MemberJoinForm = () => {

    const userId = useRef('');
    const name = useRef('');
    const email = useRef('');
    const phone = useRef('');
    const password = useRef('');
    const navigate = useNavigate();

    // 유효성 검사 에러 변수
    const [userIdError, setUserIdError] = useState('');
    const [nameError, setNameError] = useState('');
    const [emailError, setEmailError] = useState('');
    const [phoneError, setPhoneError] = useState('');
    const [passwordError, setPasswordError] = useState('');
    
    
    // 유효성 검사 함수들
    const validateUserId = (value) => {
        const regex = /^[a-zA-Z0-9]{4,20}$/;
        const isValid = regex.test(value);
        setUserIdError(isValid ? '' : '아이디는 4~20자리의 영문자와 숫자로 이루어져야 합니다.');
        return isValid;
    }

    const validateName = (value) => {
        const regex = /^[가-힣a-zA-Z]+$/;
        const isValid = regex.test(value);
        setNameError(isValid ? '' : '이름은 한글과 영문자만 사용할 수 있습니다.');
        return isValid;
    }

    const validateEmail = (value) => {
        const regex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        const isValid = regex.test(value);
        setEmailError(isValid ? '' : '유효한 이메일 주소를 입력해주세요.');
        return isValid;
    }

    const validatePhone = (value) => {
        const regex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
        const isValid = regex.test(value);
        setPhoneError(isValid ? '' : '유효한 전화번호를 입력해주세요.');
        return isValid;
    }

    const validatePassword = (value) => {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;
        const isValid = regex.test(value);
        setPasswordError(isValid ? '' : '비밀번호는 8~20자리의 영문자 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.');
        return isValid;
    }
    
    // 회원 가입 제출 로직
    const handleSubmit = async (e) => {
        e.preventDefault();

        // axios 호출 전, 유효성 검사 함수 호출
        const isUserIdValid = validateUserId(userId.current.value);
        const isNameValid = validateName(name.current.value);
        const isEmailValid = validateEmail(email.current.value);
        const isPhoneValid = validatePhone(phone.current.value);
        const isPasswordValid = validatePassword(password.current.value);

        // 유효성 검사 결과에 따른 처리
        if (!isUserIdValid || !isNameValid || !isEmailValid || !isPhoneValid || !isPasswordValid) {
            return;
        }


        // 회원가입 axios 호출
        let dto = {
            userId : userId.current.value,
            name: name.current.value,
            email: email.current.value,
            phone: phone.current.value,
            password: password.current.value,
        }
        const result = await joinMember(dto);

        // console.log('dto: ', dto)
        // console.log('result:', result);

        if (result === true) {
            // 회원가입 성공 시 로그인 페이지로 이동
            alert('회원 가입하였습니다');
            navigate('/login');
        } else if (Array.isArray(result)) {
            let errorMessages = '';
            for (let i = 0; i < result.length; i++) {
                errorMessages += `- ${result[i]}\n`;
            }
            alert(`회원 가입 실패\n\n${errorMessages}`);
        } else {
            alert(`회원 가입 실패\n${result}`);
        }

    };

    return (
        <Container className="d-flex justify-content-center align-items-center vh-100">
            <Card className="signup-card">
                <Card.Body>
                    <h2 className="text-center mb-4">회원가입</h2>
                    <Form onSubmit={handleSubmit}>
                        <Form.Group controlId="formUserId">
                            <Form.Label>아이디</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="아이디를 입력하세요"
                                ref={userId}
                                onChange={() => validateUserId(userId.current.value)}
                                isInvalid={!!userIdError}
                                required
                            />
                            <Form.Control.Feedback type="invalid">{userIdError}</Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="formName">
                            <Form.Label>이름</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="이름을 입력하세요"
                                ref={name}
                                onChange={() => validateName(name.current.value)}
                                isInvalid={!!nameError}
                                required
                            />
                            <Form.Control.Feedback type="invalid">{nameError}</Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="formEmail">
                            <Form.Label>이메일</Form.Label>
                            <Form.Control
                                type="email"
                                placeholder="이메일을 입력하세요"
                                ref={email}
                                onChange={() => validateEmail(email.current.value)}
                                isInvalid={!!emailError}
                                required
                            />
                            <Form.Control.Feedback type="invalid">{emailError}</Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="formPhone">
                            <Form.Label>전화번호</Form.Label>
                            <Form.Control
                                type="tel"
                                placeholder="전화번호를 입력하세요"
                                ref={phone}
                                onChange={() => validatePhone(phone.current.value)}
                                isInvalid={!!phoneError}
                                required
                            />
                            <Form.Control.Feedback type="invalid">{phoneError}</Form.Control.Feedback>
                        </Form.Group>

                        <Form.Group controlId="formPassword">
                            <Form.Label>비밀번호</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="비밀번호를 입력하세요"
                                ref={password}
                                onChange={() => validatePassword(password.current.value)}
                                isInvalid={!!passwordError}
                                required
                            />
                            <Form.Control.Feedback type="invalid">{passwordError}</Form.Control.Feedback>
                        </Form.Group>

                        <Button variant="primary" type="submit" className="submit-btn">
                            회원가입
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default MemberJoinForm;