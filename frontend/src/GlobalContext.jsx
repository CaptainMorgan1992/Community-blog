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

    const loadBlogPosts = async () => {
        await fetch("http://localhost:8080/api/blogpost/all")
            .then(response => response.json())
            .then(result => setBlogPosts(result))
            .catch(error => console.error(error))

    }

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
