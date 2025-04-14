import { Outlet, Link, useLocation } from 'react-router-dom';
import Navbar from './UI/Navbar';

function Layout() {
    // only show nav after login 
    const location = useLocation();
    const isLoginPage = location.pathname === '/';

    return (
        <>
            {/* navabr */}
            {!isLoginPage && <Navbar/>}
            
            {/* main content */}
            <main>
                <Outlet />
            </main>
        </>
  );
}

export default Layout;
