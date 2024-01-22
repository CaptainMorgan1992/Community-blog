import {createContext, useCallback, useEffect, useState} from "react";


const GlobalContext = createContext(null);

// eslint-disable-next-line react/prop-types
export const GlobalProvider = ({children}) =>  {

    //useStates for all variables
    const [blogPosts, setBlogPosts] = useState([])
    const [validateResponse, setValidateResponse] = useState(false)
    const [individualPost, setIndividualPost] = useState(null);
    //imports from database

    useEffect(() => {
        setValidateResponse(validateResponse);
        //setBlogPosts(blogPosts)
        void loadBlogPosts()
    }, [validateResponse]);



 /*   const loadBlogPosts = async () => {
        await fetch("http://localhost:8080/api/blogpost/all")
            .then(response => response.json())
            .then(result => setBlogPosts(result))
            .catch(error => console.error(error))

    }*/

    const submitLogin = async (username, password) => {
        try {
            // Fetch CSRF token
            const csrfRes = await fetch("http://localhost:8080/csrf", { credentials: "include" });
            const token = await csrfRes.json();

            // Submit login request
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
            await fetch("http://localhost:8080/api/logout", {
                method: 'POST',
                headers: {'Content-Type': 'application/json'}
            })
            setValidateResponse(false);
            console.log(validateResponse)
        }

        catch (error) {
            console.error(error);
        }
    }

    const loadBlogPosts = async () => {
      // const csrfRes = await fetch("http://localhost:8080/csrf", {credentials: "include"});
      //const token = await csrfRes.json()
        const requestOptions = {
            method: 'GET', // or any other HTTP method you want to use
            headers: {
                'Content-Type': 'application/json' // Example header, adjust as needed
              //'X-CSRF-TOKEN': token.token
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
        //const csrfRes = await fetch("http://localhost:8080/csrf", { credentials: "include" });
        //const token = await csrfRes.json();
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
                //'X-CSRF-TOKEN': token.token
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



    return (
        <GlobalContext.Provider
            value={{
                blogPosts,
                setBlogPosts,
                individualPost,
                setIndividualPost,
                loadIndividualPost,
                submitLogin,
                validateResponse,
                handleLogout
            }}
        >

            {children}

        </GlobalContext.Provider>
    )

}

export default GlobalContext;
