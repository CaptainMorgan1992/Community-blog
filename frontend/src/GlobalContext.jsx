import {createContext, useCallback, useEffect, useState} from "react";


const GlobalContext = createContext(null);

// eslint-disable-next-line react/prop-types
export const GlobalProvider = ({children}) =>  {

    const [blogPosts, setBlogPosts] = useState([])
    const [myPosts, setMyPosts] = useState([])
    const initialValidateResponse = JSON.parse(localStorage.getItem("validateResponse"))
    const [validateResponse, setValidateResponse] = useState(initialValidateResponse)
    const [individualPost, setIndividualPost] = useState(null);
    const [user, setUser] = useState(null);
    const [csrfToken, setCsrfToken] = useState(null);
    const [postTitle, setPostTitle] = useState('');
    const [postContent, setPostContent] = useState('');
    const [postCreated, setPostCreated] = useState(false);


    useEffect(() => {
        setValidateResponse(validateResponse);
        console.log(validateResponse)
        void loadBlogPosts();
        fetchCsrfToken();
    }, [validateResponse]);

    const fetchCsrfToken = async () => {
        try {
            const csrfRes = await fetch("http://localhost:8080/csrf", { credentials: "include" });
            const token = await csrfRes.json();
            setCsrfToken(token.token);
        } catch (error) {
            console.error(error);
        }
    };

    const submitLogin = async (username, password) => {
        try {
            const response = await fetch("http://localhost:8080/api/login", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken,
                },
                body: JSON.stringify({ username, password }),
                credentials: 'include'
            });

            setValidateResponse(response.ok)
            localStorage.setItem("validateResponse", JSON.stringify(response.ok));
        } catch (error) {
            console.error(error);
        }
    };

    const handleLogout = async () => {
        try {
          await fetch("http://localhost:8080/api/logout", {
                method: 'POST',
                headers: {'Content-Type': 'application/json',
                'X-CSRF-TOKEN' : csrfToken,
            },
                credentials: 'include' });

            setValidateResponse(false);
            localStorage.removeItem("validateResponse")
            console.log(validateResponse)
        }

        catch (error) {
            console.error(error);
        }
    }

    const loadMyPosts = useCallback(async () => {
        try {
            const csrfRes = await fetch("http://localhost:8080/csrf", { credentials: "include" });
            const token = await csrfRes.json();

            const response = await fetch(`http://localhost:8080/api/blogpost/myPosts`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': token.token,
                },
                credentials: 'include',
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const result = await response.json();
            setMyPosts(result);
        } catch (error) {
            console.error(error);
        }
    }, []);


    const loadBlogPosts = useCallback(async () => {
        const requestOptions = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
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
    }, []);

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

    const handlePost = async () => {
        try {
            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': csrfToken
                },
                body: JSON.stringify({ title: postTitle, content: postContent }),
                credentials: 'include',
            };

            const response = await fetch('http://localhost:8080/api/blogpost/create', requestOptions);

            if (response.ok) {
                setPostTitle('');
                setPostContent('');
                setPostCreated(true);
                setTimeout(() => setPostCreated(false), 6000);
            } else {
                console.error('Failed to create blog post');
            }
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
                submitLogin,
                validateResponse,
                handleLogout,
                registerUser,
                myPosts,
                setMyPosts,
                loadMyPosts,
                loadBlogPosts,
                handlePost,
                setPostTitle,
                setPostContent,
                postContent,
                postTitle,
                postCreated,
                setPostCreated
            }}
        >

            {children}

        </GlobalContext.Provider>
    )

}

export default GlobalContext;
