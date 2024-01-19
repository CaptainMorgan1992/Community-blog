import GlobalContext from "../GlobalContext.jsx";
import {useContext} from "react";

export default function BlogPost () {
    const {blogPosts,setBlogPosts} = useContext(GlobalContext)
    return<>
        <div className={"blogpost"}>
            {blogPosts.map((post, index) => (
                <div key={index}>
                    <h2>{post.title}</h2>
                    <p>{post.content}</p>
                    <p>Author: {post.author}</p>
                </div>
            ))}
        </div>
    </>

}