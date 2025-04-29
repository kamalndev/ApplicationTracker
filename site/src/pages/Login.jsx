import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

const API_URL = import.meta.env.VITE_API_URL;

export default function LoginPage() {
	const [form, setForm] = useState({
		email: "",
		password: "",
		confirmpassword: "",
	});
	const navigate = useNavigate();

	const [doRegister, setDoRegister] = useState(false);
	const [formError, setFormError] = useState(null);

	const handleChange = (e) => {
		const { name, value } = e.target;
		setForm((f) => ({ ...f, [name]: value }));
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		setFormError(null);
		console.log("form", form);
		if (
			(doRegister && (!form.email || !form.password)) ||
			(!doRegister &&
				(!form.email || !form.password || !form.confirmpassword))
		) {
			setFormError("Please fill in all fields");
			return;
		}

		if (doRegister && form.password !== form.confirmpassword) {
			setFormError("Passwords do not match");
			return;
		}

		try {
			const req_url = doRegister
				? `${API_URL}/api/register`
				: `${API_URL}/api/login`;
			const resForm = {
				email: form.email,
				password: form.password,
			};
			console.log("resForm", resForm);
			const res = await fetch(req_url, {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify(resForm),
			});

			const data = await res.json();

			if (!res.ok) {
				setFormError(res.statusText);
				return;
			}

			if (data.success) {
				localStorage.setItem("userid", data.user_id);
				navigate("/dashboard", { replace: true });
			} else {
				if (doRegister)
					setFormError(data.message || "Registration failed");
				else
					setFormError(
						data.message || "Email or password is incorrect"
					);
				return;
			}
		} catch (err) {
			console.error("Error in login/register:", err);
			setFormError("An error occurred. Please try again.");
		}
	};

	return (
		<div className="min-h-screen flex flex-col items-center justify-center bg-[#1e1e2f]">
			<h1 className="text-2xl font-bold text-white mb-6 text-center">
				Job Application Tracker
			</h1>
			<div className="bg-[#2a2a3f] p-8 rounded-lg shadow-md w-full max-w-sm">
				<h2 className="text-2xl font-bold text-white mb-6 text-center">
					{doRegister ? "Register" : "Login"}
				</h2>
				<form onSubmit={handleSubmit} className="space-y-4">
					<div>
						<label
							htmlFor="email"
							className="block text-sm text-gray-300 mb-1"
						>
							Username
						</label>
						<input
							type="string"
							name="email"
							id="email"
							value={form.email}
							onChange={handleChange}
							required
							className="w-full px-4 py-2 bg-transparent border border-white rounded text-white placeholder-gray-400 focus:outline-none focus:border-blue-500"
							placeholder="example_user"
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
							aria-invalid={!!formError}
						/>
					</div>
					{doRegister && (
						<div>
							<label
								htmlFor="confirmpassword"
								className="block text-sm text-gray-300 mb-1"
							>
								Confirm Password
							</label>
							<input
								type="password"
								name="confirmpassword"
								id="confirmpassword"
								value={form.confirmpassword}
								onChange={handleChange}
								required
								className="w-full px-4 py-2 bg-transparent border border-white rounded text-white placeholder-gray-400 focus:outline-none focus:border-blue-500"
								placeholder="••••••••"
							/>
						</div>
					)}
					{formError && (
						<p className="mt-1 text-red-400 text-sm">{formError}</p>
					)}
					<button
						type="submit"
						className="w-full bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded shadow"
					>
						{doRegister ? "Register" : "Login"}
					</button>
				</form>
				<p className="mt-4 text-sm text-gray-400 text-center">
					{doRegister ? (
						<div>
							Already registered?{" "}
							<a
								className="cursor-pointer text-blue-500 hover:underline"
								onClick={() => setDoRegister(false)}
							>
								Login
							</a>
						</div>
					) : (
						<div>
							Don't have an account?{" "}
							<a
								className="cursor-pointer text-blue-500 hover:underline"
								onClick={() => setDoRegister(true)}
							>
								Register
							</a>
						</div>
					)}
				</p>
			</div>
		</div>
	);
}
