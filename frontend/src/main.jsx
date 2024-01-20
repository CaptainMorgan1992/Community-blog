import React from 'react';
import App from './App';
import { createRoot } from 'react-dom/client';
import {
    Route,
    RouterProvider,
    createBrowserRouter,
    createRoutesFromElements
} from "react-router-dom";
import HomePage from "./pages/HomePage.jsx";
import ErrorBoundary from "./pages/errorBoundary.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";
import IndividualPostPage from "./pages/IndividualPostPage";
import AllBlogPostsPage from "./pages/AllBlogPostsPage.jsx";


const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path="/" element={<App/>} errorElement={<ErrorBoundary/>}>
            <Route path="/" index element={<HomePage />} />
            <Route path={'register'} element={<RegisterPage />} />
            <Route path={'allPosts'} element={<AllBlogPostsPage />} />
            <Route path={"blog/:id"} element={<IndividualPostPage />} />
        </Route>
    )
)


createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
);