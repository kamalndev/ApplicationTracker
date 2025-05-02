import {
	BrowserRouter as Router,
	Routes,
	Route,
	Navigate,
} from "react-router-dom";
import { useEffect, useState } from "react";
import Layout from "./Layout";
import Login from "./pages/Login";
import PersonalDashboard from "./pages/PersonalDashboard";
import PublicDashboard from "./pages/PublicDashboard";
import SearchPage from "./pages/SearchPage";
import { useAuth } from "./contexts/Auth";
import "./index.css";

function App() {
	const { userId } = useAuth();
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
					<Route
						path="/dashboard"
						element={
							userId ? (
								<PersonalDashboard />
							) : (
								<Navigate to="/" replace />
							)
						}
					/>
					<Route path="/public" element={<PublicDashboard />} />
					<Route path="/search" element={<SearchPage />} />
				</Route>
			</Routes>
		</Router>
	);
}

export default App;
