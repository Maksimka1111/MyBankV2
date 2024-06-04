import React, {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import axios from "axios";
import {setTokens} from "../TokenManager";
import {useNavigate} from "react-router-dom";
import DebitCardConfirm from "./Confirms/DebitCardConfirm";

const DebitCardCreation = () => {
    const navigate = useNavigate();

    const [card, setCard] = useState({
        FIO: "",
        val: "RUB",
        username: ""
    })

    const {FIO, val, username} = card;

    const onInputChange = (e) => {
        setCard({...card, [e.target.name]: e.target.value});
    };

    const cardCreation = async (e) => {
        e.preventDefault();
        await axios({
            method: 'post',
            url: 'http://158.160.85.202/api/cards/createDebitCard',
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
            params:{
                username: card.username,
                FIO: card.FIO,
                val: card.val
            }
        }).then(r => {
            console.log(r);
        });

        navigate("/");
    }

    return(
        <div style={{display: "flex", flexDirection: "row",
            flexWrap:"nowrap", width: "100vw", height:"90vh"}}>
            <Container className="bg-dark"
                       style={{width: "65vw", height:"65vh", alignSelf:"center", borderRadius:"10px", marginLeft:"15%"}}>
                <Form onSubmit={e => cardCreation(e)}
                    style={{display: "flex", flexWrap: "wrap", flexDirection: "column"}}>
                    <h1 style={{color: "white", alignSelf: "flex-start", marginLeft:"15%", marginTop:"2%"}}>
                        Дебетовая карта</h1>
                    <Form.Group className="mb-3" controlId="FIO" style={{width: "90%",paddingBottom: "1%", paddingTop: "4%",
                        paddingLeft:"12%"}}>
                        <Form.Control name = "FIO" defaultValue={FIO} onChange={(e) => onInputChange(e)}
                            required type={"text"} placeholder="Фамилия, имя и отчество*" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="username" style={{width: "90%",paddingBottom: "1%", paddingTop: "3%",
                        paddingLeft:"12%"}}>
                        <Form.Control name = "username" defaultValue={username} onChange={(e) => onInputChange(e)}
                            required type={"tel"} placeholder="Номер телефона*" />
                    </Form.Group>
                    <Form.Group  className="mb-3" controlId="val" style={{width: "90%",paddingBottom: "1%", paddingTop: "2%",
                        paddingLeft:"12%"}}>
                        <Form.Label style={{color:"white", float:"left"}}>Выберите валюту</Form.Label>
                        <Form.Select name = "val" defaultValue={val} onChange={(e) => onInputChange(e)}
                            aria-label="val">
                            <option value="RUB">Рубли</option>
                            <option value="USD">Доллары</option>
                            <option value="EUR">Евро</option>
                        </Form.Select>
                    </Form.Group>
                    <Button  variant="success" type="submit"
                            style={{width: "60%", height:"8vh", marginTop:"2%", alignSelf:"center"}}>
                        Оформить
                    </Button>
                </Form>
            </Container>
            <Container className="bg-dark"
                       style={{width: "40vw", marginLeft:"5%", marginRight:"10%",
                           height:"40vh", alignSelf:"center", borderRadius:"10px"}}>
                <DebitCardConfirm val = {card.val} />
            </Container>
        </div>
    )
}

export default DebitCardCreation;