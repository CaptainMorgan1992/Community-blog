import {createContext, useCallback, useEffect, useState} from "react";


const GlobalContext = createContext(null);

// eslint-disable-next-line react/prop-types
export const GlobalProvider = ({children}) =>  {

    const [blogPosts, setBlogPosts] = useState([])
    const [validateResponse, setValidateResponse] = useState(false)
    const [individualPost, setIndividualPost] = useState(null);
    const [user, setUser] = useState(null);

    //imports from database

    useEffect(() => {
        setValidateResponse(validateResponse);
        console.log(validateResponse)
        void loadBlogPosts()
    }, [validateResponse]);


    const submitLogin = async (username, password) => {
        try {
            // Fetch CSRF token
            const csrfRes = await fetch("http://localhost:8080/csrf", { credentials: "include" });
            const token = await csrfRes.json();
            const response = await fetch("http://localhost:8080/api/login", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': token.token
                },
                body: JSON.stringify({ username, password }),
                credentials: 'include'  // Include credentials in the login request
            });

            setValidateResponse(response.ok);
        } catch (error) {
            console.error(error);
        }
    };

    const handleLogout = async () => {
        try {
            const csrfRes = await fetch("http://localhost:8080/csrf", { credentials: "include" });
            const token = await csrfRes.json();

            await fetch("http://localhost:8080/api/logout", {
                method: 'POST',
                headers: {'Content-Type': 'application/json',
                'X-CSRF-TOKEN' : token.token
            },
                credentials: 'include' });

            setValidateResponse(false);
            console.log(validateResponse)
        }

        catch (error) {
            console.error(error);
        }
    }

    const loadBlogPosts = async () => {

        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        };

        try {
            const response = await fetch("http://localhost:8080/api/blogpost/all", requestOptions);

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const result = await response.json();
            setBlogPosts(result);
        } catch (error) {
            console.error(error);
        }
    };

    const loadIndividualPost = useCallback(async (postId) => {

        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        };

        try {
            const response = await fetch(`http://localhost:8080/api/blogpost/${postId}`, requestOptions);

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const result = await response.json();
            setIndividualPost(result);
        } catch (error) {
            console.error(error);
        }

    }, []);

    const registerUser = async (userData) => {
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData),
        };

        try {
            const response = await fetch('http://localhost:8080/api/register', requestOptions);

            return response; // Return the entire response object
        } catch (error) {
            console.error(error);
            return null; // Return null in case of an error
        }
    };


    return (
        <GlobalContext.Provider
            value={{
                blogPosts,
                setBlogPosts,
                individualPost,
                setIndividualPost,
                user,
                setUser,
                loadIndividualPost,
                submitLogin,
                validateResponse,
                handleLogout,
                registerUser,
            }}
        >

            {children}

        </GlobalContext.Provider>
    )

}

export default GlobalContext;
