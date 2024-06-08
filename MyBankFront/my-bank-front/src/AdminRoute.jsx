import {Navigate} from "react-router-dom";

const AdminRoute = ({ children }) => {
    const isAuthenticated = localStorage.getItem("isLogin");
    const check = localStorage.getItem("isAdmin");
    if (isAuthenticated === "true" && check === "true"){
        return children;
    }
    console.log(check);
    return <Navigate to="/" />;
};

export default AdminRoute;