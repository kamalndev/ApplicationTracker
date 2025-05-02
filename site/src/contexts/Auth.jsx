import { createContext, useContext, useState } from "react";

const AuthContext = createContext();
export function AuthProvider({ children }) {
	const [userId, setUserId] = useState(() => localStorage.getItem("userid"));
	const [userName, setUserName] = useState(null);

	function login(id, name) {
		localStorage.setItem("userid", id);
		setUserId(id);
		setUserName(name);
	}

	function logout() {
		localStorage.removeItem("userid");
		setUserId(null);
	}

	return (
		<AuthContext.Provider value={{ userId, userName, login, logout }}>
			{children}
		</AuthContext.Provider>
	);
}
export const useAuth = () => useContext(AuthContext);
