export default function ApplicationModal({
	handleSubmit,
	formData,
	setFormData,
	setShowModal,
}) {
	const toggleRequirement = (value) => {
		setFormData((prev) => {
			const newReqs = prev.requirements.includes(value)
				? prev.requirements.filter((v) => v !== value)
				: [...prev.requirements, value];
			return { ...prev, requirements: newReqs };
		});
	};

	return (
		<div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
			<div className="bg-[#1e1e2f] text-white p-6 rounded-lg w-full max-w-lg shadow-lg">
				<h2 className="text-xl font-semibold mb-4">New Application</h2>
				<form onSubmit={handleSubmit} className="space-y-4">
					<input
						type="text"
						placeholder="Company Name"
						value={formData.company}
						onChange={(e) =>
							setFormData({
								...formData,
								company: e.target.value,
							})
						}
						className="w-full p-2 rounded bg-gray-800 border border-gray-600"
						required
					/>
					<input
						type="text"
						placeholder="Job Position"
						value={formData.job_position}
						onChange={(e) =>
							setFormData({
								...formData,
								job_position: e.target.value,
							})
						}
						className="w-full p-2 rounded bg-gray-800 border border-gray-600"
						required
					/>
					<textarea
						placeholder="Job Description"
						value={formData.description}
						onChange={(e) =>
							setFormData({
								...formData,
								description: e.target.value,
							})
						}
						className="w-full p-2 rounded bg-gray-800 border border-gray-600"
					/>
					<input
						type="text"
						placeholder="Application Deadline (YYYY-MM-DD)"
						value={formData.date}
						onChange={(e) =>
							setFormData({ ...formData, date: e.target.value })
						}
						className="w-full p-2 rounded bg-gray-800 border border-gray-600"
					/>
					{/* <div>
            <p className="mb-1">Requirements:</p>
            <div className="flex flex-wrap gap-2">
              {['OA', 'Technical', 'Behavioral', 'Other'].map((req) => (
                <label key={req} className="flex items-center space-x-2">
                  <input
                    type="checkbox"
                    checked={formData.requirements.includes(req)}
                    onChange={() => toggleRequirement(req)}
                  />
                  <span>{req}</span>
                </label>
              ))}
            </div>
          </div> */}
					<input
						type="text"
						placeholder="Requirements: OA, Technical, Behavioral"
						value={formData.requirements}
						onChange={(e) =>
							setFormData({
								...formData,
								requirements: e.target.value,
							})
						}
						className="w-full p-2 rounded bg-gray-800 border border-gray-600"
						required
					/>
					<textarea
						placeholder="Additional Info / Interview Notes"
						value={formData.notes}
						onChange={(e) =>
							setFormData({ ...formData, notes: e.target.value })
						}
						className="w-full p-2 rounded bg-gray-800 border border-gray-600"
					/>
					<select
						value={formData.status}
						onChange={(e) =>
							setFormData({ ...formData, status: e.target.value })
						}
						className="w-full p-2 rounded bg-gray-800 border border-gray-600"
					>
						<option value="">Select Status</option>
						<option value="Applied">Applied</option>
						<option value="Interviewing">Interviewing</option>
						<option value="Offer">Offer</option>
						<option value="Rejected">Rejected</option>
					</select>
					<div className="flex justify-end gap-4">
						<button
							type="button"
							onClick={() => setShowModal(false)}
							className="text-gray-400 hover:text-white"
						>
							Cancel
						</button>
						<button
							type="submit"
							className="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded"
						>
							Submit
						</button>
					</div>
				</form>
			</div>
		</div>
	);
}
