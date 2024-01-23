import {useNavigate} from "react-router-dom";
import GlobalContext from "../GlobalContext.jsx";
import {useContext, useEffect, useState} from "react";

export default function Login() {

    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')
    const [message, setMessage] = useState('')
    const [isLoggedIn, setLoggedIn] = useState(false);
    const {validateResponse, submitLogin} = useContext(GlobalContext)
    const nav = useNavigate();



    useEffect(() => {
        if (validateResponse) {
            setLoggedIn(true);
            nav('/');
        }
    }, [validateResponse, nav, setLoggedIn]);

    const checkCredentials = async (e) => {
        e.preventDefault();
        await submitLogin(username, password);
        if (!username || !password || !validateResponse) {
            setMessage("You must enter your correct login details");
        }


    }

    return  <form onSubmit={checkCredentials}>
        <label htmlFor="username-input">Username</label>
        <input type="username-input"
               id="username-input"
               value={username}
               onChange={e => setUsername(e.target.value)}/>
        <label htmlFor="password-input">Password</label>
        <input type="password"
               id="password-input"
               value={password}
               onChange={e => setPassword(e.target.value)}/>
        <p id={"error-message"}>{message}</p>
        <button id="login-button">Sign in</button>
    </form>
}