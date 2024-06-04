import {Link} from "react-router-dom";
import React, {useEffect, useState} from 'react';
import {Navbar, Nav, Button, NavDropdown} from "react-bootstrap";
import {getToken} from "./TokenManager";
import LoginButton from "./AuthPages/LoginButton";
import LogoutButton from "./AuthPages/LogoutButton";

const Navigation = () => {
    useEffect(() => {
        localStorage.clear();
        if (localStorage.getItem("accessToken") === null)
            localStorage.setItem("isLogin", "false");
        else {
            localStorage.setItem("isLogin", "true");
        }
    });

    return (
        <>
            <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
                <Navbar.Brand className="ms-3">MyBank</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        <Nav.Link ><Link style={{color: "lightgray", textDecoration: "none"}} to="/">Главная</Link></Nav.Link>
                        <NavDropdown title="Карты" id="navbarCards">
                            <NavDropdown.Item><Link style={{color: "black", textDecoration: "none"}}
                                                    to = "/debitCard">
                                Дебетовая карта</Link></NavDropdown.Item>
                            <NavDropdown.Item><Link style={{color: "black", textDecoration: "none"}}
                                                    to = "/creditCard">
                                Кредитная карта</Link></NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="Кредиты" id="navbarCredits">
                            <NavDropdown.Item><Link style={{color: "black", textDecoration: "none"}}
                                                    to = "/credit">
                                Оформить кредит</Link></NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="Вклады" id="navbarCredits">
                            <NavDropdown.Item><Link style={{color: "black", textDecoration: "none"}}
                                                    to = "/contribution">
                                Сделать вклад</Link></NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    <Nav>
                        <LoginButton />
                        <LogoutButton />
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        </>
    );
}

export default Navigation;