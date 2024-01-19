import {Link} from "react-router-dom";

export default function Navbar() {
    return <nav className={"navbar"}>
        <Link to={'register'}>
            <button>Register</button>
        </Link>

        <Link to={'myPosts'}>
            <button>My posts</button>
        </Link>
        <Link to={'login'}>
            <button>Login</button>
        </Link>

        <button>Create post</button>
        <button>My profile</button>
        <button>Contact</button>

    </nav>

}