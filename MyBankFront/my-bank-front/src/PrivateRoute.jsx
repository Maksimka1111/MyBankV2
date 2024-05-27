import {Navigate} from "react-router-dom";

const PrivateRoute = ({ children }) => {
    const isAuthenticated = localStorage.getItem("isLogin");
    if (isAuthenticated === "true"){
        return children;
    }
    return <Navigate to="/login" />;
};

export default PrivateRoute;