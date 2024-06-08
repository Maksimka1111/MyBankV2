import {Navigate} from "react-router-dom";
import axios from "axios";
import {useState} from "react";


const check = async () => {
    const jwtAuth = "Bearer " + JSON.parse(localStorage.getItem("accessToken")).value;
    const result = await axios({
        method: 'get',
        url: 'http://158.160.85.202:9000/api/auth/checkAccessAdmin'
        ,
        headers: {
            "Content-type": "application/json; charset=UTF-8",
            "Authorization" : jwtAuth,
        }
    });
    console.log(result)
    return result.data === "status:ok";
}

const AdminRoute = ({ children }) => {
    if (check()) {
        return children;
    }
    else{
        return <Navigate to={"/"} />
    }
};

export default AdminRoute;