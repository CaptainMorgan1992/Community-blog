import {createContext, useCallback, useEffect, useState} from "react";


const GlobalContext = createContext(null);

// eslint-disable-next-line react/prop-types
export const GlobalProvider = ({children}) =>  {

    //useStates for all variables
    const [blogPosts, setBlogPosts] = useState([])
    const [individualPost, setIndividualPost] = useState(null);
    const [user, setUser] = useState(null);
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

    const loadIndividualPost = useCallback(async (postId) => {
        const csrfRes = await fetch("http://localhost:8080/csrf", { credentials: "include" });
        const token = await csrfRes.json();
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token.token
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
        const csrfRes = await fetch('http://localhost:8080/csrf', { credentials: 'include' });
        const token = await csrfRes.json();
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': token.token,
            },
            body: JSON.stringify(userData),
        };

        try {
            const response = await fetch('http://localhost:8080/api/register', requestOptions);

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const result = await response.json();
            setUser(result); // Update user state after successful registration
        } catch (error) {
            console.error(error);
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
                loadBlogPosts,
                registerUser,
            }}
        >

            {children}

        </GlobalContext.Provider>
    )

}

export default GlobalContext;
