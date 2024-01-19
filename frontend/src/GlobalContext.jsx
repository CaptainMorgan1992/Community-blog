import {createContext, useEffect, useState} from "react";


const GlobalContext = createContext(null);

// eslint-disable-next-line react/prop-types
export const GlobalProvider = ({children}) =>  {

    //useStates for all variables
    const [blogPosts, setBlogPosts] = useState([])
    //imports from database

    useEffect(() => {
        setBlogPosts(blogPosts)
        void loadBlogPosts()
    }, [blogPosts]);

 /*   const loadBlogPosts = async () => {
        await fetch("http://localhost:8080/api/blogpost/all")
            .then(response => response.json())
            .then(result => setBlogPosts(result))
            .catch(error => console.error(error))

    }*/

    const loadBlogPosts = async () => {
        const csrfRes = await fetch("http://localhost:8080/csrf", {credentials: "include"});
        const token = await csrfRes.json()
        const requestOptions = {
            method: 'GET', // or any other HTTP method you want to use
            headers: {
                'Content-Type': 'application/json', // Example header, adjust as needed
                'X-CSRF-TOKEN': token.token
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
                setBlogPosts
            }}
        >

            {children}

        </GlobalContext.Provider>
    )

}

export default GlobalContext;
