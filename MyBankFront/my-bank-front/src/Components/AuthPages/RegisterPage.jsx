import React, {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const RegisterPage = () => {
    let navigate = useNavigate();

    const [user, setUser] = useState({
        name: "",
        FIO: "",
        password: "",
    });

    const {name, FIO, password} = user;

    const onInputChange = (e) => {
        setUser({...user, [e.target.name]: e.target.value});
    };

    const register = async (e) => {
        e.preventDefault();
        const result = await axios({
            method: 'post',
            url: 'http://158.160.85.202:9000/api/auth/register'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
            params: {
                username: user.name,
                password: user.password
            }
        });
        if (result.data === "Exists")
            console.log(1);
        else{
            await axios({
                method: 'post',
                url: 'http://158.160.85.202:9010/api/user/fillUserInfo'
                ,
                headers: {
                    "Content-type": "application/json; charset=UTF-8",
                },
                params: {
                    username: user.name,
                    FIO: user.FIO
                }
            }).then(r => {
                console.log(r);
            });
            navigate("/");
        }
    }

    return (
        <div style={{display: "flex", flexWrap:"nowrap", width: "100vw", height:"90vh"}}>
            <Container className="bg-dark" style={{width: "60vw", height:"81vh", alignSelf:"center", borderRadius:"10px"}}>
                <h1 style={{color:"white", paddingTop:"4%"}}>Регистрация</h1>
                <Form onSubmit={(e) => register(e)} style={{display: "flex", flexWrap: "wrap", flexDirection: "column"}}>
                    <Form.Group className="mb-3" controlId="username" style={{width: "90%",paddingBottom: "1%", paddingTop: "2%",
                        paddingLeft:"12%"}}>
                        <Form.Label style={{float: "left", marginLeft: "1%", color: "white"}}>Номер телефона</Form.Label>
                        <Form.Control name = "name" defaultValue={name} onChange={(e) => onInputChange(e)}
                            required type={"tel"} minLength={11} maxLength={12} placeholder="+79001233211" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="FIO" style={{width: "90%",paddingBottom: "1%", paddingTop: "2%",
                        paddingLeft:"12%"}}>
                        <Form.Label style={{float: "left", marginLeft: "1%", color: "white"}}>ФИО</Form.Label>
                        <Form.Control name = "FIO" defaultValue={FIO} onChange={(e) => onInputChange(e)}
                                      required type={"text"} placeholder="Фамилия, имя, отчество*" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="password" style={{width: "90%", paddingBottom: "1%", paddingTop: "1%",
                        paddingLeft:"12%"}}>
                        <Form.Label style={{float: "left", marginLeft: "1%", color: "white"}}>Пароль</Form.Label>
                        <Form.Control name = "password" defaultValue={password} onChange={(e) => onInputChange(e)}
                            required type={"password"} placeholder="Ваш пароль" />
                    </Form.Group>
                    <Button
                        variant="success" type="submit" style={{width: "80%", height:"8vh", marginTop:"2%", alignSelf:"center"}}>
                        Подтвердить
                    </Button>
                </Form>
            </Container>
        </div>);
}

export default RegisterPage;