import Navbar from "./Navbar.jsx";
import {Link} from "react-router-dom";

export default function Header(){
    return (
        <header className="header">
            <Link to="/">
                <h1 className="blog-title"><span className="x-span">X</span> BLOG</h1>
            </Link>
            <Navbar/>
        </header>
    )
}