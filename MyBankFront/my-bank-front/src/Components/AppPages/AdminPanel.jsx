import React, {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import axios from "axios";

const AdminPanel = () => {
    const [user, setUser] = useState({
        username: "",
        fio: "",
    })
    const [account, setAccount] = useState({
        number: "",
        money: "",
    })

    const onInputChange = (e) => {
        setUser({...user, [e.target.name]: e.target.value});
    }
    const onInputChange2 = (e) => {
        setAccount({...account, [e.target.name]: e.target.value});
    }

    const showUser = async (e) => {
        e.preventDefault()

        const result = await axios({
            method: 'get',
            url: 'http://localhost:9010/api/admin/getUser'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
            ,
            params: {
                username: user.username,
            }
        });
        console.log(result.data);
        if (result.data !== "Nothing")
            setUser(result.data);
        else{
            alert("No such user");
        }
    }
    const removeUser = async () => {
        const result1 = await axios({
            method: 'delete',
            url: 'http://localhost:9010/api/admin/delUser'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
            ,
            params: {
                username: user.username,
            }
        });
        const jwtAuth = "Bearer " + JSON.parse(localStorage.getItem("accessToken")).value;
        const result2 = await axios({
            method: 'delete',
            url: 'http://localhost:9000/api/auth/delUser'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization" : jwtAuth,
            }
            ,
            params: {
                username: user.username,
            }
        });
        setUser({
            username: "",
            fio: "",
        })
    }

    const addMoney = async (e) => {
        e.preventDefault();
        const result = await axios({
            method: 'post',
            url: 'http://localhost:9010/api/admin/addMoney'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
            ,
            params: {
                number: account.number,
                money: account.money,
            }
        });

        if (result.data === "status: ok")
            alert("Success")
        else{
            alert("No such account");
        }
    }

    return (
        <div style={{display: "flex", marginBottom:"6%"}}>
            <Container>
                <Form onSubmit={(e) => showUser(e)}
                    style={{ flexDirection:"row", flexWrap:"wrap", borderRadius:"10px",
                    display:"flex", backgroundColor:"darkcyan", marginTop:"2%", width:"65%"}}>
                    <Form.Group className="mb-3" controlId="username" style={{paddingBottom: "1%", paddingTop: "4%",
                        alignSelf:"center", paddingLeft:"12%", paddingRight:"3%"}}>
                        <Form.Label style={{float: "left", marginLeft: "1%", color: "white"}}>Найти пользователя</Form.Label>
                        <Form.Control name = "username" defaultValue={user.username}
                                      onChange={(e) => onInputChange(e)}
                                      required type={"text"} placeholder="+79001233211" />
                    </Form.Group>
                    <Button type={"submit"} variant="secondary" style={{marginLeft:"2%", marginTop:"5%",
                        width:"10vw", height:"10vh", float:"right"}}>Поиск</Button>
                </Form>
                <Container className="bg-dark"
                           style={{ marginTop:"3%", marginLeft:"5%",
                               width: "35vw", height:"34vh", alignSelf:"flex-start", borderRadius:"10px", color:"white"}}>
                    <h3>Данные пользователя</h3>
                    <p>Имя пользователя: {user.username} </p>
                    <p>ФИО: {user.fio}</p>
                    <Button variant={"danger"} style={{ marginRight:"5%", float:"right"}}
                            onClick={() => removeUser()}>
                        Удалить пользователя</Button>
                </Container>
            </Container>
            <Container>
                <Form onSubmit={(e) => addMoney(e)}
                      style={{ flexDirection:"row", flexWrap:"wrap", borderRadius:"10px",
                          display:"flex", backgroundColor:"darkcyan", marginTop:"2%", width:"65%"}}>
                    <Form.Group className="mb-3" controlId="username" style={{paddingBottom: "1%", paddingTop: "4%",
                        alignSelf:"center", paddingLeft:"12%"}}>
                        <Form.Label style={{float: "left", marginLeft: "1%", color: "white"}}>Пополнить счет</Form.Label>
                        <Form.Control name = "number" defaultValue={account.number}
                                      onChange={(e) => onInputChange2(e)}
                                      required type={"text"} placeholder="Номер счета" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="username" style={{paddingBottom: "1%", paddingTop: "4%",
                        alignSelf:"center", paddingLeft:"12%"}}>
                        <Form.Control name = "money" defaultValue={account.money}
                                      onChange={(e) => onInputChange2(e)}
                                      required type={"number"} placeholder="Сумма" />
                    </Form.Group>
                    <Button type={"submit"} variant="primary" style={{marginLeft:"2%", marginTop:"5%",
                        width:"10vw", height:"10vh", float:"right"}}>Пополнить</Button>
                </Form>
            </Container>
        </div>
    )
}

export default AdminPanel;