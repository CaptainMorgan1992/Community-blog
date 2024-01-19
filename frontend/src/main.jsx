import React from 'react';
import App from './App';
import ReactDOM from 'react-dom';
import {
    Route,
    RouterProvider,
    createBrowserRouter,
    createRoutesFromElements
} from "react-router-dom";
import HomePage from "./pages/HomePage.jsx";
import ErrorBoundary from "./pages/errorBoundary.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";
import MyPostsPage from "./pages/MyPostsPage.jsx";
import LoginPage from "./pages/LoginPage.jsx"


const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path="/" element={<App/>} errorElement={<ErrorBoundary/>}>
            <Route path="/" index element={<HomePage/>}/>
            <Route path={'register'} element={<RegisterPage/>}/>
            <Route path={'myPosts'} element={<MyPostsPage/>}/>
            <Route path={'login'} element={<LoginPage/>}/>
        </Route>
    )
)


ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
);