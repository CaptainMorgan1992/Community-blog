import { useContext, useEffect } from 'react';
import GlobalContext from '../GlobalContext';

const MyPostsPage = () => {
    const { myPosts, loadMyPosts } = useContext(GlobalContext);


    useEffect(() => {
        loadMyPosts();
    }, [loadMyPosts]);


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
};

export default MyPostsPage;
