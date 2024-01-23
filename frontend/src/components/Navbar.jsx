import {Link} from "react-router-dom";
import GlobalContext from "../GlobalContext.jsx";
import {useContext} from "react";

export default function Navbar() {

    const {validateResponse, handleLogout} = useContext(GlobalContext)
    return (
        <nav className={'navbar'}>
            {!validateResponse && (
                <Link to={'login'}>
                    <button>Login</button>
                </Link>
            )}

            {validateResponse && (
                <>
                    <Link to={'myPosts'}>
                        <button>My posts</button>
                    </Link>
                    <Link to={'createPost'}>
                        <button>Create post</button>
                    </Link>
                    <Link to={'logout'}>
                        <button onClick={handleLogout}>Logout</button>
                    </Link>
                </>
            )}

            <Link to={'allPosts'}>
                <button>All posts</button>
            </Link>

            {!validateResponse && (
                <>
                    <Link to={'register'}>
                        <button>Register</button>
                    </Link>
                    <Link to={'contactUs'}>
                        <button>Contact us</button>
                    </Link>
                </>
            )}
        </nav>
    );

}