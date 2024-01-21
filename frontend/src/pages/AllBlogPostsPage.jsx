import GlobalContext from "../GlobalContext.jsx";
import {useContext, useState} from "react";
import {Link} from "react-router-dom";
export default function AllBlogPostsPage() {
    const { blogPosts } = useContext(GlobalContext);
    const [searchTerm, setSearchTerm] = useState("");

    const filteredBlogPosts = blogPosts.filter((post) =>
        post.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <>
            <div className="searchBar">
                <input
                    type="text"
                    placeholder="Search..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </div>
            <div className={"blogpost"}>
                {filteredBlogPosts.map((post) => (
                    <div key={post.id}>
                        <Link to={`/blog/${post.id}`}>
                            <h2>{post.title}</h2>
                        </Link>
                        <p className={"content"}>{post.content}</p>
                        <p>Author: {post.author}</p>
                    </div>
                ))}
            </div>
        </>
    );
}