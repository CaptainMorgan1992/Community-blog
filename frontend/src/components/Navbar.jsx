import {Link} from "react-router-dom";
import GlobalContext from "../GlobalContext.jsx";
import {useContext} from "react";

export default function Navbar() {

    const {validateResponse, handleLogout} = useContext(GlobalContext)
    return (
        <nav className={'navbar'}>
            {!validateResponse && (
                // If validateResponse is false, show Login button
                <Link to={'login'}>
                    <button>Login</button>
                </Link>
            )}

            {validateResponse && (
                // If validateResponse is true, show Logout, My posts, and Create post buttons
                <>
                    <Link to={'logout'}>
                        <button onClick={handleLogout}>Logout</button>
                    </Link>
                    <Link to={'myPosts'}>
                        <button>My posts</button>
                    </Link>
                    <Link to={'testNewPost'}>
                        <button>Create post</button>
                    </Link>
                </>
            )}

            {/* Always show the "All posts" button */}
            <Link to={'allPosts'}>
                <button>All posts</button>
            </Link>

            {!validateResponse && (
                // If validateResponse is false, show Register and Contact us buttons
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