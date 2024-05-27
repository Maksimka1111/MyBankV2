import React from "react";
import {Button} from "react-bootstrap";
import userIcon from '../images/userIcon.png'
import {Link} from "react-router-dom";

const LoginButton = (props) => {
    if (localStorage.getItem("isLogin") === "false") {
        return (
            <div>
                <Link to="/login"><Button
                    variant="secondary" className="me-3">Войти</Button></Link>
            </div>
        );
    } else {
        return (
            <div>
                <Link to = "/profile">
                <img style={{width: "10%", height: "100%", float:"right", marginRight: "6%"}}
                    src={userIcon} alt="userProfile"/>
                </Link>
            </div>
        );
    }
}

export default LoginButton;