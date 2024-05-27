import React from "react";
import axios from "axios";
import {Button} from "react-bootstrap";
import {removeToken} from "../TokenManager";
import {useNavigate} from "react-router-dom";

const LoginButton = (props) => {
    let navigate = useNavigate();

    const logout = async () => {
        const refreshTokenAuth = 'Bearer ' + JSON.parse(localStorage.getItem("refreshToken")).value;
        await axios({
            method: 'post',
            url: 'http://localhost:9000/jwt/logout'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization" : refreshTokenAuth,
            }
        }).then(r => {
            console.log(r);
        });
        removeToken("accessToken");
        removeToken("refreshToken");
        localStorage.setItem("isLogin", "false");

        navigate("/");

        window.location.reload();
    };

    if (localStorage.getItem("isLogin") === "true") {
        return (
            <div>
                <Button onClick = {logout}
                        variant="secondary" className="me-3">Выйти</Button>
            </div>
        );
    } else {
        return (
            <></>
        );
    }
}

export default LoginButton;