import GlobalContext from "../GlobalContext.jsx";
import {useContext} from "react";
import {Link} from "react-router-dom";

export default function AllBlogPostsPage () {
    const {blogPosts} = useContext(GlobalContext)
    return<>
        <div className={"blogpost"}>
            {blogPosts.map((post) => (
                <div key={post.id}>
                    <Link to={`/blog/${post.id}`}>
                        <h2>{post.title}</h2>
                    </Link>
                    <p>{post.content}</p>
                    <p>Author: {post.author}</p>
                </div>
            ))}
        </div>
    </>

}