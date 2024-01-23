import { useContext, useEffect } from 'react';
import GlobalContext from '../GlobalContext';

const MyPostsPage = () => {
    const { myPosts, loadMyPosts, validateResponse } = useContext(GlobalContext);


    useEffect(() => {
        loadMyPosts();
    }, [loadMyPosts]);

if (validateResponse) {
    return (
        <div>
            <h2>HEY My Posts</h2>
            {myPosts.map(post => (
                <div key={post.id}>
                    <h3>{post.title}</h3>
                    <p>{post.content}</p>
                </div>
            ))}
        </div>
    );
} else {
    return (
        <div>
            <h2>You are not logged in.</h2>
        </div>
    )
}

}


export default MyPostsPage;
