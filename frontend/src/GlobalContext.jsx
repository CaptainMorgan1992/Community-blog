import {createContext, useEffect, useState} from "react";


const GlobalContext = createContext(null);

// eslint-disable-next-line react/prop-types
export const GlobalProvider = ({children}) =>  {

    //useStates for all variables
    const [blogPosts, setBlogPosts] = useState([])
    const [validateResponse, setValidateResponse] = useState(false)
    //imports from database

    useEffect(() => {
        setValidateResponse(validateResponse);
        setBlogPosts(blogPosts)
        void loadBlogPosts()
    }, [blogPosts, validateResponse]);



 /*   const loadBlogPosts = async () => {
        await fetch("http://localhost:8080/api/blogpost/all")
            .then(response => response.json())
            .then(result => setBlogPosts(result))
            .catch(error => console.error(error))

    }*/

    const submitLogin = async (username, password) => {
        const csrfRes = await fetch("http://localhost:8080/csrf", {credentials: "include"});
       const token = await csrfRes.json()
        try {
        const response = await fetch("http://localhost:8080/api/login", {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
                'X-CSRF-TOKEN': token.token,
            body: JSON.stringify({username, password})
        })
        setValidateResponse(response.ok);
        } catch (error) {
            console.error(error);
        }
    }

    const loadBlogPosts = async () => {
       // const csrfRes = await fetch("http://localhost:8080/csrf", {credentials: "include"});
      //  const token = await csrfRes.json()
        const requestOptions = {
            method: 'GET', // or any other HTTP method you want to use
            headers: {
                'Content-Type': 'application/json' // Example header, adjust as needed
               // 'X-CSRF-TOKEN': token.token
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

    return (
        <GlobalContext.Provider
            value={{
                blogPosts,
                setBlogPosts,
                submitLogin,
                validateResponse
            }}
        >

            {children}

        </GlobalContext.Provider>
    )

}

export default GlobalContext;
