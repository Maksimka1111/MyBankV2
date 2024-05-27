import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import Footer from './Components/Footer';
import Navbar from './Components/Navigation';
import LoginPage from './Components/AuthPages/LoginPage';
import RegisterPage from './Components/AuthPages/RegisterPage';
import MainPage from './Components/AppPages/MainPage';
import 'bootstrap/dist/css/bootstrap.min.css'
import Profile from "./Components/AppPages/Profile";
import DebitCardCreation from "./Components/AppPages/DebitCardCreation";
import {GoogleOAuthProvider} from "@react-oauth/google";
import CreditCardCreation from "./Components/AppPages/CreditCardCreation";
import ContributionCreation from "./Components/AppPages/ContributionCreation";
import CreditCreation from "./Components/AppPages/CreditCreation";
import AdminPanel from "./Components/AppPages/AdminPanel";
import PrivateRoute from "./PrivateRoute";
import AdminRoute from "./AdminRoute";
import MakeTransfer from "./Components/AppPages/makeTransfer";

function App() {
  return (
    <div className="App">
        <GoogleOAuthProvider clientId="81805074188-folanqjgke0m4a8p7jne410fs3q2pptk.apps.googleusercontent.com">
            <BrowserRouter>
            <Navbar />
              <Routes>
                  <Route path = "*" element={<MainPage />} />

                  <Route path = "/login" element={<LoginPage />} />
                  <Route path = "/register" element={<RegisterPage />} />

                  <Route path = "/debitCard" element={<DebitCardCreation />}/>
                  <Route path = "/creditCard" element={<CreditCardCreation />}/>
                  <Route path = "/contribution" element={<ContributionCreation />}/>
                  <Route path = "/credit" element={<CreditCreation />}/>
                  <Route path = "/makeTransfer" element={<MakeTransfer />}/>

                  <Route
                      path="/profile"
                      element={
                          <PrivateRoute>
                              <Profile />
                          </PrivateRoute>
                      }
                  />
                  <Route
                      path="/adminPanel"
                      element={
                          <AdminRoute>
                              <AdminPanel />
                          </AdminRoute>
                      }
                  />
              </Routes>
              <Footer />
            </BrowserRouter>
        </GoogleOAuthProvider>
    </div>
  );
}

export default App;
