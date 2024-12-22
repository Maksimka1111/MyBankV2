import React, {useState} from "react";
import RangeSlider from 'react-bootstrap-range-slider';
import {Button, Container, Form} from "react-bootstrap";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const CreditCardCreation = () => {
    const navigate = useNavigate();

    const [ value, setValue ] = React.useState(45000);

    const [creditCard, setCreditCard] = useState({
        FIO: "",
        username: "",
        salary: "",
        val: "RUB"
    })

    const {FIO, salary, val, username} = creditCard;

    const onInputChange = (e) => {
        setCreditCard({...creditCard, [e.target.name]: e.target.value});
    };

    const creditCardCreation = async (e) => {
        e.preventDefault();
        await axios({
            method: 'post',
            url: 'http://51.250.77.153:9010/api/cards/createCreditCard',
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
            params:{
                FIO: creditCard.FIO,
                salary: creditCard.salary,
                username: creditCard.username,
                val: creditCard.val,
                limit: value,
            }
        }).then(r => {
            console.log(r);
        });

        navigate("/");
    }

    return(
        <div style={{display: "flex", flexWrap:"nowrap", width: "100vw", height:"90vh"}}>
            <Container className="bg-dark"
                       style={{
                           width: "57vw", height:"88vh", alignSelf:"center", borderRadius:"10px"}}>
                <Form onSubmit={(e) => {creditCardCreation(e)}} style={{display: "flex", flexWrap: "wrap", flexDirection: "column"}}>
                    <h1 style={{color: "white", alignSelf: "flex-start", marginLeft:"15%", marginTop:"1%"}}>
                        Кредитная карта</h1>
                    <Form.Group className="mb-3" controlId="FIO" style={{width: "90%",paddingBottom: "1%", paddingTop: "3%",
                        paddingLeft:"12%"}}>
                        <Form.Control name = "FIO" defaultValue={FIO}
                            required type={"text"} onChange={(e) => {onInputChange(e)}}
                                      placeholder="Фамилия, имя и отчество*" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="username" style={{width: "90%",paddingBottom: "1%", paddingTop: "2%",
                        paddingLeft:"12%"}}>
                        <Form.Control  name = "username" defaultValue={username}
                            required type={"tel"} onChange={(e) => {onInputChange(e)}}
                                      placeholder="Номер телефона*" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="salary" style={{width: "90%",paddingBottom: "1%", paddingTop: "2%",
                        paddingLeft:"12%"}}>
                        <Form.Control name = "salary" defaultValue={salary}
                            min={13500} required type={"number"} onChange={(e) => {onInputChange(e)}}
                                      placeholder="Ваша заработная плата*" />
                    </Form.Group>
                    <Form.Group  className="mb-3" controlId="val" style={{width: "90%",paddingBottom: "1%", paddingTop: "1%",
                        paddingLeft:"12%"}}>
                        <Form.Label style={{color:"white", float:"left"}}>Выберите валюту</Form.Label>
                        <Form.Select aria-label="val" name = "val" defaultValue={val}
                                     onChange={(e) => {onInputChange(e)}}>
                            <option value="RUB">Рубли</option>
                            <option value="USD">Доллары</option>
                            <option value="EUR">Евро</option>
                        </Form.Select>
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="limit" style={{width: "90%", height:"10vh",
                        paddingTop: "1%", paddingLeft:"12%"}}>
                        <Form.Label style={{color:"white", float:"left"}}>Максимальная сумма на карте</Form.Label>
                        <RangeSlider name = "limit"
                            value={value} min={5000} max={80000}
                                    onChange={e => {
                                        setValue(e.target.value)
                                    }}
                                     tooltipPlacement='top'
                                     tooltipLabel={currentValue => `${currentValue} руб.`}
                                     tooltip='on'
                        />
                    </Form.Group>
                    <Button variant="success" type="submit"
                            style={{width: "60%", height:"8vh", marginTop:"2%", alignSelf:"center"}}>
                        Продолжить
                    </Button>
                </Form>
            </Container>
        </div>
    )
}

export default CreditCardCreation;
