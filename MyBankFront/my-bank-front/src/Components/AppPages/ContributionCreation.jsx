import React, {useState} from "react";
import RangeSlider from 'react-bootstrap-range-slider';
import {Button, Container, Form} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const ContributionCreation = () => {
    let navigate = useNavigate();

    const [ value, setValue ] = React.useState(45000);
    const [ term, setTerm ] = React.useState(8);
    const [ replenishment, setReplenishment] = React.useState(false);

    const [contribution, setContribution] = useState({
        FIO: "",
        username: "",
    })

    const {FIO, username} = contribution;

    const onInputChange = (e) => {
        setContribution({...contribution, [e.target.name]: e.target.value});
    };

    const contributionCreation = async (e) => {
        e.preventDefault();
        console.log(replenishment);
        console.log(contribution.FIO);
        await axios({
            method: 'post',
            url: 'http://localhost:9010/api/contributions/createContribution',
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
            params:{
                FIO: contribution.FIO,
                replenished: JSON.stringify(replenishment),
                username: contribution.username,
                duration: term,
                sum: value
            }
        }).then(r => {
            console.log(r);
        });

        navigate("/");
    }
    return(
        <div style={{display: "flex", flexWrap:"nowrap", width: "100%", height:"100vh", backgroundColor:"black"}}>
            <Container className="bg-dark"
                       style={{
                           width: "57vw", height:"95vh", alignSelf:"center", borderRadius:"10px"}}>
                <Form onSubmit={(e) => contributionCreation(e)} style={{display: "flex", flexWrap: "wrap", flexDirection: "column"}}>
                    <h1 style={{color: "white", alignSelf: "flex-start", marginLeft:"15%", marginTop:"2%"}}>
                        Оформление вклада</h1>
                    <Form.Group className="mb-3" controlId="sum" style={{width: "90%", height:"10vh",
                        paddingTop: "2%", paddingBottom:"1%", paddingLeft:"12%", marginTop:"3%"}}>
                        <Form.Label style={{color:"white", float:"left"}}>Размер вклада</Form.Label>
                        <RangeSlider
                            value={value} min={10000} max={100000}
                            onChange={e => {setValue(e.target.value)}}
                            tooltipPlacement='top'
                            tooltipLabel={currentValue => `${currentValue} руб.`}
                            tooltip='on'
                        />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="duration" style={{width: "90%", height:"10vh",
                        paddingTop: "3%", paddingBottom:"1%", paddingLeft:"12%",  marginTop:"1%"}}>
                        <Form.Label style={{color:"white", float:"left"}}>Срок вклада</Form.Label>
                        <RangeSlider
                            value={term} min={1} max={12}
                            onChange={e => {setTerm(e.target.value)}}
                            tooltipPlacement='top'
                            tooltipLabel={currentValue => `${currentValue} год(лет).`}
                            tooltip='on'
                        />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="replenished" style={{width: "90%", height:"10vh",
                            paddingTop: "3%", paddingBottom:"1%", paddingLeft:"12%",  marginTop:"1%"}}>
                        <Form.Label style={{color:"white", float:"left"}}>Непополняемый вклад</Form.Label>
                        <Form.Check name = "replenishment" defaultValue={replenishment}
                        style = {{float:"right"}} onChange={(e) => {
                            console.log(replenishment);
                            setReplenishment(!replenishment);
                        }}
                            type="switch"
                            id="custom-switch"
                        />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="FIO" style={{width: "90%",paddingBottom: "1%", paddingTop: "4%",
                        paddingLeft:"12%"}}>
                        <Form.Control name = "FIO" defaultValue={FIO}
                            required type={"text"} onChange={e => {onInputChange(e)}}
                                      placeholder="Фамилия, имя и отчество*" />
                    </Form.Group>
                    <Form.Group className="mb-3" controlId="username" style={{width: "90%",paddingBottom: "1%", paddingTop: "3%",
                        paddingLeft:"12%"}}>
                        <Form.Control name = "username" defaultValue={username}
                            required type={"tel"} onChange={e => {onInputChange(e)}}
                                      placeholder="Номер телефона*" />
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

export default ContributionCreation;