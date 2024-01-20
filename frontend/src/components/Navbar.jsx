import {Link} from "react-router-dom";

export default function Navbar() {
    return <nav className={"navbar"}>
        <Link to={'register'}>
            <button>Register</button>
        </Link>

        <Link to={'allPosts'}>
            <button>All posts</button>
        </Link>


        <button>Create post</button>
        <button>My profile</button>
        <button>Contact</button>

    </nav>

}