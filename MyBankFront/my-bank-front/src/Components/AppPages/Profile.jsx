import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import {Button, Container} from "react-bootstrap";

const Profile = () => {
    let navigate = useNavigate();

    const [userInfo, setUserInfo] = useState({
        username: "",
        fio: "",
        debitCards: [],
        creditCards: [],
        anyTargetCreditList: [],
        contributions: []
    });
    const getUserInfo = async () => {
        const result = await axios({
            method: 'get',
            url: 'http://localhost:9010/api/user/getUserInfo'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
            ,
            params: {
                username: localStorage.getItem("username"),
            }
        });
        console.log(1);
        console.log(result.data);
        setUserInfo(result.data);
    };
    const removeCard = async (number) => {
        await axios({
            method: 'delete',
            url: 'http://localhost:9010/api/cards/removeCard'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
            ,
            params: {
                number: number
            }
        }).then(r => console.log(r));
        window.location.reload();
    }

    const setCurrentActivityAccount = (number) =>{
        localStorage.setItem("accountNumber", number);
    }

    const arrayDataItems1 = (objects) => objects.map(object =>
        <li style={{ width:"95%", marginTop:"1%" , marginBottom:"1%",
            float:"left", listStyleType:"none", backgroundColor:"darkviolet"}} key={object.id}>
            <p style={{float:"left", paddingLeft:"5%", marginTop:"1%"}} >Номер карты: {object.number}</p> <br/>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Дата окончания поддержки карты: {object.date}</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Секретный код: {object.secretCode}</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Счет: {object.money} {object.currency}.</p>
            <Link to={"/makeTransfer"}><Button onClick={() => setCurrentActivityAccount(object.number)}
                style={{marginBottom:"2%"}} variant={"dark"}>
                Сделать перевод</Button></Link>
            <Button onClick={() => removeCard(object.number)} variant={"danger"}>
                Удалить</Button>
        </li>
    )
    const arrayDataItems2 = (objects) => objects.map(object =>
        <li style={{ width:"95%",
            float:"left", listStyleType:"none", backgroundColor:"darkviolet"}} key={object.id}>
            <h3 style={{marginRight:"10%"}}>Карта</h3>
            <p style={{float:"left", paddingLeft:"5%"}} >Номер карты: {object.number}</p> <br/>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Дата окончания поддержки карты: {object.date}</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Секретный код: {object.secretCode}</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Текущий долг: {object.duty}руб.</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Максимальная сумма: {object.limit}руб.</p>
            <Button onClick={() => removeCard(object.number)} variant={"danger"}>
                Удалить</Button>
        </li>
    )
    const arrayDataItems3 = (objects) => objects.map(object =>
        <li style={{ width:"95%",
            float:"left", listStyleType:"none", backgroundColor:"darkviolet"}} key={object.id}>
            <h3 style={{marginRight:"10%"}}>Кредит</h3>
            <p style={{float:"left", paddingLeft:"5%"}} >Номер кредита: {object.number}</p> <br/>
            <p style={{float:"left", marginRight:"80%", paddingLeft:"5%"}}>Остаток: {object.sum} руб.</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Срок кредита: {object.term} месяц(ев)</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Месячная палата: {object.monthPayment} руб.</p>
        </li>
    )
    const arrayDataItems4 = (objects) => objects.map(object =>
        <li style={{ width:"95%",
            float:"left", listStyleType:"none", backgroundColor:"darkviolet"}} key={object.id}>
            <h3 style={{marginRight:"10%"}}>Вклад</h3>
            <p style={{float:"left", paddingLeft:"5%"}} >Номер вклада: {object.number}</p> <br/>
            <p style={{float:"left", marginRight: "80%", paddingLeft:"5%"}}>Срок вклада: {object.term} лет</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Процент: {object.percent}%</p>
            <p style={{float:"left", marginRight:"50%", paddingLeft:"5%"}}>Счет: {object.sum} руб.</p>
            {/*<Button onClick={() => removeCard(object.number)} variant={"danger"}>*/}
            {/*    Закрыть вклад</Button>*/}
        </li>
    )

    useEffect( () => {
        getUserInfo();
    }, []);

    return (
        <div style={{display: "flex", flexWrap:"wrap", backgroundColor:"black"}}>
            <Container className="bg-dark"
                       style={{ marginTop:"3%", marginLeft:"5%",
                           width: "35vw", height:"24vh", alignSelf:"flex-start", borderRadius:"10px", color:"white"}}>
                <h3>Данные пользователя</h3>
                <p>Имя пользователя: {userInfo.username}, </p>
                <p>ФИО: {userInfo.fio}</p>
            </Container>
            <Container className="bg-dark"
                       style={{  marginRight:"5%",
                           marginTop:"3%", marginLeft:"5%", alignSelf:"flex-start", borderRadius:"10px", color:"white"}}>
                <div>
                    <h3>Дебетовые карты</h3>
                    <ul style={{paddingBottom:"25%", paddingTop:"2%"}}>
                        {arrayDataItems1(userInfo.debitCards)}</ul>
                    <Link to="/debitCard"><Button style={{marginBottom:"1%", marginRight:"5%",
                        marginTop:"1%", float:"right"}}>
                        Создать карту</Button></Link>
                </div>
            </Container>
            <Container className="bg-dark"
                       style={{  marginRight:"5%",
                           marginTop:"3%", marginLeft:"5%", alignSelf:"flex-start", borderRadius:"10px", color:"white"}}>
                <div>
                    <h3>Кредитные карты</h3>
                    <ul style={{paddingBottom:"25%", paddingTop:"2%"}}>
                        {arrayDataItems2(userInfo.creditCards)}</ul>
                    <Link to="/creditCard"><Button style={{marginBottom:"1%", marginRight:"5%",
                        marginTop:"1%", float:"right"}}>
                        Создать карту</Button></Link>
                </div>
            </Container>
            <Container className="bg-dark"
                       style={{  marginRight:"5%",
                           marginTop:"3%", marginLeft:"5%", alignSelf:"flex-start", borderRadius:"10px", color:"white"}}>
                <div>
                    <h3>Кредиты</h3>
                    <ul style={{paddingBottom:"25%", paddingTop:"2%"}}>
                        {arrayDataItems3(userInfo.anyTargetCreditList)}</ul>
                    <Link to="/credit"><Button style={{marginBottom:"1%", marginRight:"5%",
                        marginTop:"1%", float:"right"}}>
                        Оформить кредит</Button></Link>
                </div>
            </Container>
            <Container className="bg-dark"
                       style={{  marginRight:"5%", marginBottom:"5%",
                           marginTop:"3%", marginLeft:"5%", alignSelf:"flex-start", borderRadius:"10px", color:"white"}}>
                <div>
                    <h3>Вклады</h3>
                    <ul style={{paddingBottom:"25%", paddingTop:"2%"}}>
                        {arrayDataItems4(userInfo.contributions)}</ul>
                    <Link to="/contribution"><Button style={{marginBottom:"1%", marginRight:"5%",
                        marginTop:"1%", float:"right"}}>
                        Создать вклад</Button></Link>
                </div>
            </Container>
        </div>
        )
}

export default Profile;