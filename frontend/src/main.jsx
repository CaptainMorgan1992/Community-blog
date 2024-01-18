import React from 'react';
import App from './App';
import ReactDOM from 'react-dom';
import {
    Route,
    RouterProvider,
    createBrowserRouter,
    createRoutesFromElements
} from "react-router-dom";
import './App.css'
import HomePage from "./pages/HomePage.jsx";
import ErrorBoundary from "./pages/errorBoundary.jsx";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path="/" element={<App/>} errorElement={<ErrorBoundary/>}>
            <Route index element={<HomePage/>}/>
        </Route>
    )
)


ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <RouterProvider router={router}/>
    </React.StrictMode>
);