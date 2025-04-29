import {
	BrowserRouter as Router,
	Routes,
	Route,
	Navigate,
} from "react-router-dom";
import { useState } from "react";
import Layout from "./Layout";
import Login from "./pages/Login";
import PersonalDashboard from "./pages/PersonalDashboard";
import PublicDashboard from "./pages/PublicDashboard";
import SearchPage from "./pages/SearchPage";
import "./index.css";

function App() {
	const [count, setCount] = useState(0);
	const userId = localStorage.getItem("userid");

	return (
		<Router>
			<Routes>
				<Route
					path="/"
					element={
						userId ? (
							<Navigate to="/dashboard" replace />
						) : (
							<Login />
						)
					}
				/>
				<Route element={<Layout />}>
					<Route path="/dashboard" element={<PersonalDashboard />} />
					<Route path="/public" element={<PublicDashboard />} />
					<Route path="/search" element={<SearchPage />} />
				</Route>
			</Routes>
		</Router>
	);
}

export default App;
