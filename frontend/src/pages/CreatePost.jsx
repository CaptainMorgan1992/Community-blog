import { useContext } from 'react';
import GlobalContext from '../GlobalContext.jsx';

export default function CreatePost() {
    const { validateResponse, handlePost, postTitle, postContent, setPostTitle, setPostContent, postCreated, setPostCreated} = useContext(GlobalContext);

    const handleTitleChange = (event) => {
        setPostTitle(event.target.value);
        setPostCreated(false);
    };

    const handleContentChange = (event) => {
        setPostContent(event.target.value);
        setPostCreated(false);
    };

    const calculateButtonColor = () => {
        if (postTitle && postContent) {
            return "#007bff";
        } else {
            return "rgba(0, 123, 255, 0.5)";
        }
    };

    return (
        <div className="blogpost-creation-container">
            <div className="blogpost-creation-input-container">
                <input
                    type="text"
                    className="blogpost-creation-title"
                    placeholder="Title"
                    value={postTitle}
                    onChange={handleTitleChange}
                />
                <textarea
                    className="blogpost-creation-text-area"
                    placeholder="What is on your mind?"
                    value={postContent}
                    onChange={handleContentChange}
                />
            </div>
            <div className="blogpost-creation-button-container">
                <button
                    className="blogpost-creation-post-button"
                    onClick={handlePost}
                    disabled={!validateResponse}
                    style={{ backgroundColor: calculateButtonColor() }}
                >
                    Create a Post
                </button>
                {postCreated && (
                    <p className="blogpost-creation-success-message">Post created</p>
                )}
            </div>
        </div>
    );
}

