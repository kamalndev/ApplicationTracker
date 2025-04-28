import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

const API_URL = import.meta.env.VITE_API_URL;

export default function LoginPage() {
	const [form, setForm] = useState({ email: "", password: "" });
	const navigate = useNavigate();

	const [loginError, setLoginError] = useState(null);

	const handleChange = (e) => {
		const { name, value } = e.target;
		setForm((f) => ({ ...f, [name]: value }));
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		setLoginError(null);
		if (!form.email || !form.password) {
			setLoginError("Please fill in all fields");
			return;
		}

		try {
			const res = await fetch(`${API_URL}/api/login`, {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify(form),
			});

			const data = await res.json();

			if (!res.ok) {
				setLoginError(res.statusText);
				return;
			}

			if (data.success) {
				localStorage.setItem("userid", data.user_id);
				navigate("/dashboard", { replace: true });
			} else {
				setLoginError(data.message || "Email or password is incorrect");
				return;
			}
		} catch (err) {
			console.error("Error logging in:", err);
			setLoginError("An error occurred. Please try again.");
		}
	};

	return (
		<div className="min-h-screen flex flex-col items-center justify-center bg-[#1e1e2f]">
			<h1 className="text-2xl font-bold text-white mb-6 text-center">
				Job Application Tracker
			</h1>
			<div className="bg-[#2a2a3f] p-8 rounded-lg shadow-md w-full max-w-sm">
				<h2 className="text-2xl font-bold text-white mb-6 text-center">
					Sign In
				</h2>
				<form onSubmit={handleSubmit} className="space-y-4">
					<div>
						<label
							htmlFor="email"
							className="block text-sm text-gray-300 mb-1"
						>
							Email
						</label>
						<input
							type="email"
							name="email"
							id="email"
							value={form.email}
							onChange={handleChange}
							required
							className="w-full px-4 py-2 bg-transparent border border-white rounded text-white placeholder-gray-400 focus:outline-none focus:border-blue-500"
							placeholder="you@example.com"
						/>
					</div>
					<div>
						<label
							htmlFor="password"
							className="block text-sm text-gray-300 mb-1"
						>
							Password
						</label>
						<input
							type="password"
							name="password"
							id="password"
							value={form.password}
							onChange={handleChange}
							required
							className="w-full px-4 py-2 bg-transparent border border-white rounded text-white placeholder-gray-400 focus:outline-none focus:border-blue-500"
							placeholder="••••••••"
							// Add password error when login fails
							aria-invalid={!!loginError}
						/>
						{loginError && (
							<p className="mt-1 text-red-400 text-sm">
								{loginError}
							</p>
						)}
					</div>
					<button
						type="submit"
						className="w-full bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded shadow"
					>
						Log In
					</button>
				</form>
				<p className="mt-4 text-sm text-gray-400 text-center">
					Don't have an account?{" "}
					<Link
						to="/register"
						className="text-blue-500 hover:underline"
					>
						Register
					</Link>
				</p>
			</div>
		</div>
	);
}
