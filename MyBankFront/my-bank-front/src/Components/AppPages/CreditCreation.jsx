import React, {useState} from "react";
import RangeSlider from 'react-bootstrap-range-slider';
import {Button, Container, Form} from "react-bootstrap";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const CreditCreation = () => {
    const navigate = useNavigate();

    const [ value, setValue ] = React.useState(45000);
    const [ duration, setDuration ] = React.useState(8);

    const [credit, setCredit] = useState({
        FIO: "",
        username: ""
    })

    const {FIO, salary, username} = credit;

    const onInputChange = (e) => {
        setCredit({...credit, [e.target.name]: e.target.value});
    };

    const creditCreation = async (e) => {
        e.preventDefault();
        await axios({
            method: 'post',
            url: 'http://158.160.85.202:9010/api/credits/createCredit',
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
            params:{
                FIO: credit.FIO,
                salary: credit.salary,
                username: credit.username,
                duration: duration,
                sum: value,
            }
        }).then(r => {
            console.log(r);
        });

        navigate("/");
    }

    return(
        <div style={{display: "flex", flexWrap:"nowrap", width: "100vw", height:"95vh"}}>
            <Container className="bg-dark"
                       style={{
                           width: "61vw", height:"86vh", alignSelf:"center", borderRadius:"10px",
                           marginBottom:"2%" ,marginLeft:"18%"}}>
                <Form onSubmit={(e) => creditCreation(e)} style={{display: "flex", flexWrap: "wrap", flexDirection: "column"}}>
                    <h1 style={{color: "white", alignSelf: "flex-start", marginLeft:"15%", marginTop:"3%"}}>
                        Оформление кредита</h1>
                    <Form.Group className="mb-3" controlId="limit" style={{width: "90%", height:"10vh",
                        paddingTop: "1%", paddingBottom:"1%", paddingLeft:"12%", marginTop:"3%"}}>
                        <Form.Label style={{color:"white", float:"left"}}>Сумма кредита</Form.Label>
                        <RangeSlider name = "limit"
                            value={value} min={10000} max={100000}
                            onChange={e => {
                                setValue(e.target.value)
                            }}
                            tooltipPlacement='top'
                            tooltipLabel={currentValue => `${currentValue} руб.`}
                            tooltip='on'
                        />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="duration" style={{width: "90%", height:"10vh",
                        paddingTop: "1%", paddingBottom:"1%", paddingLeft:"12%",  marginTop:"1%"}}>
                        <Form.Label style={{color:"white", float:"left"}}>Срок кредита</Form.Label>
                        <RangeSlider name = "duration"
                            value={duration} min={1} max={30}
                            onChange={e => {
                                setDuration(e.target.value)
                            }}
                            tooltipPlacement='top'
                            tooltipLabel={currentValue => `${currentValue} месяц(ев).`}
                            tooltip='on'
                        />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="salary"
                                style={{width: "90%",paddingBottom: "1%", paddingTop: "1%", paddingLeft:"12%"}}>
                        <Form.Control name = "salary" defaultValue={salary}
                                      min={13500} required type={"number"}
                                      onChange={(e) => {onInputChange(e)}}
                                      placeholder="Ваша заработная плата*" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="FIO" style={{width: "90%",paddingBottom: "1%", paddingTop: "2%",
                        paddingLeft:"12%"}}>
                        <Form.Control name = "FIO" defaultValue={FIO}
                            required type={"text"} onChange={(e) => {onInputChange(e)}}
                                      placeholder="Фамилия, имя и отчество*" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="username" style={{width: "90%",paddingBottom: "1%", paddingTop: "3%",
                        paddingLeft:"12%"}}>
                        <Form.Control  name = "username" defaultValue={username}
                            required type={"tel"} onChange={(e) => {onInputChange(e)}}
                                      placeholder="Номер телефона*" />
                    </Form.Group>
                    <Button variant="success" type="submit"
                            style={{width: "60%", alignSelf:"center"}}>
                        Продолжить
                    </Button>
                </Form>
            </Container>
            {/*<Container className="bg-dark"*/}
            {/*           style={{width: "40vw", marginLeft:"5%", marginRight:"10%",*/}
            {/*               height:"40vh", alignSelf:"center", borderRadius:"10px"}}>*/}
            {/*    <div style={{color: "white", textAlign: "left", marginLeft:"2%", marginTop:"2%"}}>*/}
            {/*        <h2>Дополнительная информация:</h2>*/}
            {/*        Ежемесячный платеж:*/}
            {/*    </div>*/}
            {/*</Container>*/}
        </div>
    )
}

export default CreditCreation;