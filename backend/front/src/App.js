// App.js
import './App.css';
import React, {useState} from "react";
import {BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import Home from "./components/Home";
import Login from "./components/Login";
import { Provider } from 'react-redux';
import { store } from './Utils/Rdx';
import { connect } from 'react-redux';
import SideBar from "./components/SideBar";
import CountryListComponent from "./components/CountryListComponent";
import CountryComponent from "./components/CountryComponent";

// Защищенный маршрут, использующий Redux
const ProtectedRoute = ({ user, children }) => {
    console.log("ProtectedRoute user:", user);
    return user ? children : <Navigate to={'/login'} />;
};

// Функция для связывания состояния Redux с props для App компонента
function mapStateToProps(state) {
    const { msg } = state.alert;
    return { error_message: msg };
}

// Функция для связывания состояния Redux с props для ProtectedRoute
function mapStateToPropsForRoute(state) {
    return {
        user: state.authentication.user
    };
}

// Подключаем ProtectedRoute к Redux
const ConnectedProtectedRoute = connect(mapStateToPropsForRoute)(ProtectedRoute);

const App = props => {

    const [exp,setExpanded] = useState(true);
    return (
        <div className="App">
            <BrowserRouter>
                <NavigationBar toggleSideBar={() =>
                    setExpanded(!exp)}/>
                <div className="wrapper">
                    <SideBar expanded={exp} />
                    <div className="container-fluid">
                        { props.error_message &&  <div className="alert alert-danger m-1">{props.error_message}</div>}
                        <Routes>
                            <Route path="/" element={<Navigate to="/home" />} />
                            <Route path="login" element={<Login />}/>
                            <Route path="home" element={<ConnectedProtectedRoute><Home/></ConnectedProtectedRoute>}/>
                            <Route path="countries" element={<ConnectedProtectedRoute><CountryListComponent/></ConnectedProtectedRoute>}/>
                            <Route path="countries/:id" element={<ConnectedProtectedRoute><CountryComponent/></ConnectedProtectedRoute>}/>
                        </Routes>
                    </div>
                </div>
            </BrowserRouter>
        </div>
    );
}


export default connect(mapStateToProps)(App);