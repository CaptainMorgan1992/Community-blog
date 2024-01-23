import React, { useState, useContext } from 'react';
import GlobalContext from '../GlobalContext.jsx';

export default function TestNewPost() {
    const [postTitle, setPostTitle] = useState('');
    const [postContent, setPostContent] = useState('');
    const { validateResponse } = useContext(GlobalContext);
    const [postCreated, setPostCreated] = useState(false);


    const handlePost = async () => {
        try {
            const csrfRes = await fetch('http://localhost:8080/csrf', { credentials: 'include' });
            const token = await csrfRes.json();

            const requestOptions = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': token.token,
                },
                body: JSON.stringify({ title: postTitle, content: postContent }),
                credentials: 'include',
            };

            const response = await fetch('http://localhost:8080/api/blogpost/create', requestOptions);

            if (response.ok) {
                // Handle success, e.g., clear input fields and provide visual feedback
                setPostTitle('');
                setPostContent('');
                setPostCreated(true);
                console.log('Blogpost created!');
                // Hide the success message after a short delay (e.g., 2.5 seconds)
                setTimeout(() => setPostCreated(false), 3000);
            } else {
                // Handle failure, e.g., display an error message
                console.error('Failed to create blog post');
            }
        } catch (error) {
            console.error(error);
        }
    };

    const handleTitleChange = (event) => {
        setPostTitle(event.target.value);
        // Show the success message when typing in the input fields
        setPostCreated(false);
    };

    const handleContentChange = (event) => {
        setPostContent(event.target.value);
        // Show the success message when typing in the input fields
        setPostCreated(false);
    };

    const calculateButtonColor = () => {
        if (postTitle && postContent) {
            return "#007bff"; // Full color when both fields are filled
        } else {
            return "rgba(0, 123, 255, 0.5)"; // Faded color when one or both fields are empty
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

