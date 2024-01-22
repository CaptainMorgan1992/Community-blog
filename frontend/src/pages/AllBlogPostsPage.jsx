import {useContext, useState} from "react";
import {Link} from "react-router-dom";
import GlobalContext from "../GlobalContext.jsx";
import SearchBar from "../components/SearchBar";

export default function AllBlogPostsPage() {
    const {blogPosts} = useContext(GlobalContext);
    const [searchTerm, setSearchTerm] = useState("");

    const filteredBlogPosts = blogPosts.filter((post) =>
        post.title.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <>
            <SearchBar searchTerm={searchTerm} onChange={setSearchTerm}/>
            <div className={"blogpost"}>
                {filteredBlogPosts.map((post) => (
                    <div className="post" key={post.id}>
                        <Link to={`/blog/${post.id}`}>
                            <h2>{post.title}</h2>
                        </Link>
                        <p className={"content"}>
                            {truncateContent(post.content, 100)} {/* Limit to 100 characters */}
                        </p>
                        <p className="author">
                            Author: {post.blogpostUsername} |{" "}
                            <Link to={`/blog/${post.id}`}>
                                <span className="readMore">Read more</span>
                            </Link>
                        </p>
                    </div>
                ))}
            </div>
        </>
    );
}

function truncateContent(content, maxLength) {
    if (content.length > maxLength) {
        return content.substring(0, maxLength) + " ...";
    }
    return content;
}
