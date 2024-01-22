import Header from "./components/Header.jsx";
import {Outlet} from "react-router-dom";
import Footer from "./components/Footer.jsx";
import "./styles/Footer.css"
import "./styles/Header.css"
import "./styles/BlogPost.css"
import "./styles/Navbar.css"
import "./styles/RegisterPage.css"
import "./styles/errorBoundary.css"
import "./App.css"
import "./styles/HomePage.css"
import "./styles/IndividualPostPage.css"
import {GlobalProvider} from "./GlobalContext.jsx";

function App() {
  return (
      <GlobalProvider>
        <Header/>
        <Outlet/>
        <Footer/>
      </GlobalProvider>

  );
}
export default App
