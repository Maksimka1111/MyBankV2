import axios from "axios";

export const setTokens = (tokens) => {
    localStorage.setItem("accessToken", JSON.stringify(
        {
            value: tokens.accessToken,
            timeStamp: tokens.accessTokenExp,
        }
    ));
    localStorage.setItem("refreshToken", JSON.stringify(
        {
            value: tokens.refreshToken,
            timeStamp: tokens.refreshTokenExp,
        }
    ));
}

export const getToken = async () => {
    if (await checkAccessTokenAlive()){
        return JSON.parse(localStorage.getItem("accessToken")).value;
    }
    console.warn("REFRESH TOKEN EXPIRED, NEED TO AUTHORIZATION AGAIN");
    return undefined;
}

export const removeToken = (tokenType) => {
    localStorage.removeItem(tokenType);
}

export const checkAccessTokenAlive = async () => {
    if (localStorage.getItem("accessToken") === null || localStorage.getItem("refreshToken") === null)
        return false;

    const now = new Date().getTime();
    if (JSON.parse(localStorage.getItem("accessToken")).timestamp < now){
        removeToken("accessToken");

        const refreshTokenAuth = 'Bearer ' + JSON.parse(localStorage.getItem("refreshToken")).value;
        let tokens;
        await axios({
            method: 'post',
            url: 'http://158.160.85.202:9000/jwt/refresh'
            ,
            headers: {
                "Content-type": "application/json; charset=UTF-8",
                "Authorization" : refreshTokenAuth,
            }
        }).then(r => {
            tokens = r.data;
        });
        setTokens(tokens);
    }
    if (JSON.parse(localStorage.getItem("refreshToken")).timestamp < now)
        removeToken("refreshToken");
    return (JSON.parse(localStorage.getItem("refreshToken")).timestamp < now);
}