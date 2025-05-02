import { useState, useEffect } from "react";
import ApplicationModal from "../UI/ApplicationModal";
import { useAuth } from "../contexts/Auth";

const API_URL = import.meta.env.VITE_API_URL;

export default function PersonalDashboard() {
	const [showModal, setShowModal] = useState(false);
	const [applications, setApplications] = useState([]);
	const { userId } = useAuth();
	const [formData, setFormData] = useState({
		company: "",
		job_position: "",
		description: "",
		date: "",
		requirements: "",
		notes: "",
		status: "",
		user_id: userId,
	});

	// GET '/api/applications' -> gets all applications from current user
	const fetchApplications = async () => {
		try {
			const res = await fetch(
				`${API_URL}/api/personaldashboard?userId=${userId}`
			);
			const data = await res.json();
			setApplications(data);
		} catch (err) {
			console.error("Error fetching applications:", err);
		}
	};

	useEffect(() => {
		fetchApplications();
	}, []);

	const handleSubmit = async (e) => {
		e.preventDefault();

		// POST '/api/applications' -> posts new appliation for current user
		try {
			const res = await fetch(`${API_URL}/api/applications`, {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(formData),
			});

			const data = await res.json();
			console.log("Submitted application:", data);

			setShowModal(false);
			setFormData({
				company: "",
				job_position: "",
				description: "",
				date: "",
				requirements: "",
				notes: "",
				status: "",
				user_id: userId,
			});
			fetchApplications();
		} catch (err) {
			console.error("Error submitting application:", err);
		}
	};

	return (
		<div className="p-8">
			<div className="flex justify-between items-center mb-8">
				<h1 className="text-3xl font-bold text-white">
					Personal Dashboard
				</h1>
				<button
					onClick={() => setShowModal(true)}
					className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded shadow"
				>
					Add an Application
				</button>
			</div>

			{/* Modal */}
			{showModal && (
				<ApplicationModal
					handleSubmit={handleSubmit}
					formData={formData}
					setFormData={setFormData}
					setShowModal={setShowModal}
				/>
			)}

			{/* Table */}
			<table className="w-full text-left text-white bg-[#1e1e2f] rounded-lg shadow overflow-hidden">
				<thead className="bg-gray-800">
					<tr>
						<th className="p-3">Company</th>
						<th className="p-3">Position</th>
						<th className="p-3">Date</th>
						<th className="p-3">Requirements</th>
						<th className="p-3">Notes</th>
						<th className="p-3">Status</th>
						{/* <th className="p-3">Actions</th> */}
					</tr>
				</thead>
				<tbody>
					{applications.map((app) => (
						<tr
							key={app.id}
							className="border-t border-gray-700 hover:bg-gray-700 cursor-pointer"
							// onClick={() => window.location.href = `/application/${app.id}`}
						>
							<td className="p-3">{app.company}</td>
							<td className="p-3">{app.job_position}</td>
							<td className="p-3">{app.date}</td>
							<td className="p-3">{app.requirements}</td>
							<td className="p-3">{app.notes}</td>
							<td className="p-3">{app.status}</td>
							{/* <td className="p-3 space-x-2">
                <button
                  className="text-yellow-400 hover:underline"
                  onClick={(e) => {
                    e.stopPropagation();
                    alert('Edit feature coming soon');
                  }}
                >
                  Edit
                </button>
                <button
                  className="text-red-400 hover:underline"
                  onClick={(e) => {
                    e.stopPropagation();
                    alert('Delete feature coming soon');
                  }}
                >
                  Delete
                </button>
              </td> */}
						</tr>
					))}
				</tbody>
			</table>
		</div>
	);
}
