import { createContext, useContext, useState } from "react";

const AuthContext = createContext();
export function AuthProvider({ children }) {
	const [userId, setUserId] = useState(() => localStorage.getItem("userid"));

	function login(id) {
		localStorage.setItem("userid", id);
		setUserId(id);
	}

	function logout() {
		localStorage.removeItem("userid");
		setUserId(null);
	}

	return (
		<AuthContext.Provider value={{ userId, login, logout }}>
			{children}
		</AuthContext.Provider>
	);
}
export const useAuth = () => useContext(AuthContext);
