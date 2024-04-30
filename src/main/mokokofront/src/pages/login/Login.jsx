import React from 'react';
import LoginForm from "../../components/LoginForm";
import NavLayout from "../../layout/NavLayout";
import FooterLayout from "../../layout/FooterLayout";

const Login = () => {

    return (
        <>
            <NavLayout/>
            <LoginForm/>
            <FooterLayout/>
        </>
    );
};

export default Login;