import { createContext, useContext, useState } from "react";

const AuthContext = createContext();
export function AuthProvider({ children }) {
	const [userId, setUserId] = useState(() => localStorage.getItem("userid"));
	const [userName, setUserName] = useState(null);

	function login(id, name) {
		localStorage.setItem("userid", id);
		localStorage.setItem("username", name);
		setUserId(id);
		setUserName(name);
	}

	function logout() {
		localStorage.removeItem("userid");
		localStorage.removeItem("username");
		setUserId(null);
		setUserName(null);
	}

	return (
		<AuthContext.Provider value={{ userId, userName, login, logout }}>
			{children}
		</AuthContext.Provider>
	);
}
export const useAuth = () => useContext(AuthContext);
