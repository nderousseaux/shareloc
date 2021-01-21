import React from 'react';
import { Route, Switch, Redirect } from 'react-router-dom';
import { HashRouter } from 'react-router-dom';
import { QueryCache, ReactQueryCacheProvider } from 'react-query';

import Menu from './Menu';

import { AuthProvider } from "./Auth";

import {Signin} from './components/Signin';
import {Signup} from './components/Signup';
import {Coloc} from './components/Coloc';
import {User} from './components/User';
import { Members } from './components/Members';
import { Service } from './components/Services';

const queryCache = new QueryCache({
    defaultConfig: {
        queries: {
            staleTime: 0, // 5000,
            refetchOnWindowFocus: false
        }
    }
});



const Home = () => <h3>Bienvenu sur Shareloc !</h3>;

const Main = () => {
    return (
        <>
            <AuthProvider>
                <Menu />
                <hr />
                <Switch>
                    <Route exact path="/">
                        <Home />
                    </Route>
                    <Route exact path="/signin">
                        <Signin />
                    </Route>
                    <Route exact path="/signup">
                        <Signup />
                    </Route>
                    <Route exact path="/user">
                        <User />
                    </Route>
                    <Route exact path="/coloc">
                        <Coloc />
                    </Route>
                    <Route exact path="/members">
                        <Members />
                    </Route>
                    <Route exact path="/services">
                        <Service />
                    </Route>
                    <Redirect to="/" />
                </Switch>
            </AuthProvider>
        </>
    );
};

const App = () =>
    <HashRouter>
        <ReactQueryCacheProvider queryCache={queryCache}>
            <Main />
        </ReactQueryCacheProvider>
    </HashRouter>;

export default App;
