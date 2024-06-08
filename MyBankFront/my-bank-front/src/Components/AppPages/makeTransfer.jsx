import React, {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const MakeTransfer = () =>{
    let navigate = useNavigate();
    const [transferInfo, setTransferInfo] = useState([{
        number: "",
        money: "",
    }])

    const onInputChange = (e) => {
        setTransferInfo({...transferInfo, [e.target.name]: e.target.value});
    };
    const transfer = async () => {
        const result = await axios({
            method: 'post',
            url: 'http://localhost:9010/api/cards/makeTransfer'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            },
            params: {
                this_number: localStorage.getItem("accountNumber"),
                number: transferInfo.number,
                money: transferInfo.money
            }
        });

        console.log(result.data);
        if (result.data === "status: ok")
        {
            console.log(1);

            navigate("/");
            
            window.location.reload();
        }
        else{
            alert("Something wrong :(");
        }
    }

        return (
            <div style={{display:"flex"}}>
                <Container style={{display:"flex", alignSelf:"center", width:"100vw" , height:"65vh"}}>
                    <Form onSubmit={() => transfer()}
                          style={{ alignSelf:"center", flexDirection:"row", flexWrap:"wrap", borderRadius:"10px",
                              display:"flex", backgroundColor:"gray"}}>
                        <Form.Group className="mb-3" controlId="number" style={{paddingBottom: "1%", paddingTop: "4%",
                            alignSelf:"center", paddingLeft:"12%", paddingRight:"3%"}}>
                            <Form.Label style={{float: "left", marginLeft: "1%", color: "white"}}>
                                Ваш номер счета: {localStorage.getItem("accountNumber")}</Form.Label>
                            <Form.Control name = "number" defaultValue={transferInfo.number}
                                          onChange={(e) => onInputChange(e)}
                                          required type={"text"} placeholder="Номер счета для перевода" />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="money" style={{paddingBottom: "1%", paddingTop: "4%",
                            alignSelf:"center", paddingLeft:"12%", paddingRight:"3%"}}>
                            <Form.Control name = "money" defaultValue={transferInfo.money}
                                          onChange={(e) => onInputChange(e)}
                                          required type={"number"} placeholder="Сумма" />
                        </Form.Group>
                        <Button type={"submit"} variant="secondary" style={{
                            marginBottom:"2%", marginLeft:"2%", marginTop:"5%", float:"right"}}>Перевести</Button>
                    </Form>
                </Container>
            </div>
        );
}

export default MakeTransfer;