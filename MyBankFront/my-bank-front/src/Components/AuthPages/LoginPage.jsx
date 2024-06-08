import React, { useState } from "react";
import {Button, Container, Form} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import {setTokens} from "../TokenManager";
import { GoogleLogin } from "@react-oauth/google";
import {jwtDecode} from "jwt-decode";

const LoginPage = () => {
    let navigate = useNavigate();

    const [user, setUser] = useState({
        username: "",
        password: "",
    });

    const {username, password} = user;

    const onInputChange = (e) => {
        setUser({...user, [e.target.name]: e.target.value});
    };

    const login = async (e) => {
        e.preventDefault();
        const basicAuth = 'Basic ' + btoa(user.username + ':' + user.password);
        let tokens;
        console.log(user.username);
        console.log(user.password);
        try{
        const result = await axios({
            method: 'post',
            url: 'http://localhost:9000/jwt/tokens'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization" : basicAuth,
            }
        });
        tokens = result.data;
        console.log(tokens);
        const jwtAuth = 'Bearer ' + tokens.accessToken;
        const test = await axios({
            method: 'get',
            url: 'http://localhost:9000/test'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization" : jwtAuth,
            }
        });
        let checkAdmin;
        try {
            checkAdmin = await axios({
                method: 'get',
                url: 'http://localhost:9000/api/auth/checkAccessAdmin'
                ,
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                    "Authorization": jwtAuth,
                }
            });
        } catch (e){
            checkAdmin = "fail";
        }
        console.log(test.data);
        if (test.data === "ok") {
            setTokens(tokens);

            localStorage.setItem("isLogin", "true");
            localStorage.setItem("username", user.username);
            if (checkAdmin.data === 'status: ok')
                localStorage.setItem("isAdmin", "true");
            else{
                localStorage.setItem("isAdmin", "false");
            }
            console.log(localStorage.getItem("isAdmin"))

             navigate("/");
             window.location.reload();
        } else{
            alert("Incorrect username or password!");
        }
        } catch (e){
            console.log(e);
            navigate("/login")
        }
    }

    return (
        <div style={{display: "flex", flexWrap:"nowrap", width: "100vw", height:"90vh"}}>
        <Container className="bg-dark" style={{width: "57vw", height:"81vh", alignSelf:"center", borderRadius:"10px"}}>
            <h1 style={{color:"white", paddingTop:"4%"}}>Авторизация</h1>
            <Form onSubmit={(e) => login(e)}
                  style={{display: "flex", flexWrap: "wrap", flexDirection: "column"}}>
                <Form.Group className="mb-3" controlId="username" style={{width: "90%",paddingBottom: "1%", paddingTop: "4%",
                    paddingLeft:"12%"}}>
                    <Form.Label style={{float: "left", marginLeft: "1%", color: "white"}}>Номер телефона</Form.Label>
                    <Form.Control name = "username" defaultValue={username} onChange={(e) => onInputChange(e)}
                                  required type={"tel"} minLength={11} maxLength={12} placeholder="+79001233211" />
                </Form.Group>
                <Form.Group className="mb-3" controlId="password" style={{width: "90%", paddingBottom: "1%", paddingTop: "1%",
                    paddingLeft:"12%"}}>
                    <Form.Label style={{float: "left", marginLeft: "1%", color: "white"}}>Пароль</Form.Label>
                    <Form.Control name = "password" defaultValue={password} onChange={(e) => onInputChange(e)}
                                  required type={"password"} placeholder="Ваш пароль" />
                </Form.Group>
                <Button variant="success" type="submit" style={{width: "60%", height:"8vh", marginTop:"2%", alignSelf:"center"}}>
                    Войти
                </Button>
                <Button
                    variant="primary" style={{width: "60wh", marginTop:"3%", alignSelf:"center"}}>
                    <GoogleLogin
                        onSuccess={async credentialResponse => {
                            const decode = jwtDecode(credentialResponse.credential);
                            console.log(decode);
                            localStorage.setItem("username", JSON.stringify(decode.email));

                            const usr = {
                                username: JSON.stringify(decode.email),
                                password: JSON.stringify(decode.email + "1"),
                            }
                            await axios.post("http://localhost:9000/api/auth/register", usr).then(r => console.log(r))

                            const basicAuth = 'Basic ' + btoa(usr.username + ':' + usr.password);
                            let tokens;
                            await axios({
                                method: 'post',
                                url: 'http://localhost:9000/jwt/tokens'
                                ,
                                headers: {
                                    "Content-type": "application/json; charset=UTF-8",
                                    "Authorization" : basicAuth,
                                }
                            }).then(r => {
                                tokens = r.data;
                            });

                            setTokens(tokens);
                            console.log(tokens);

                            localStorage.setItem("isLogin", "true");
                            console.log(localStorage.getItem("isLogin"));
                            navigate("/");

                            window.location.reload();
                        }}
                        onError={() => {
                            console.log('Login Failed');
                        }}
                    /></Button>
                <div className="me-3" style={{paddingTop: "2%"}}>
                    <p style={{color:"white", fontSize: "110%"}}>Еще нет аккаунта? <Link to="/register" className="text-light">
                        Зарегистрироваться</Link></p>
                </div>
            </Form>
        </Container>
        </div>);
}

export default LoginPage;