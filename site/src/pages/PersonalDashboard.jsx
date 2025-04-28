import { useState, useEffect } from 'react';
import ApplicationModal from '../UI/ApplicationModal';

export default function PersonalDashboard() {
    // temporary for UI
    const sampleApplications = [
      {
        id: 1,
        company: "Stripe",
        job_position: "Full-stack Intern",
        description: "Full-stack intern role focused on internal tools for payment reconciliation.",
        date: "04/10/2025",
        requirements: ["Technical"],
        notes: "System design question came up. Mentioned my work with Flask and Elysia.js.",
        status: "Interviewing",
      },
      {
        id: 2,
        company: "Stripe",
        job_position: "Full-stack Intern",
        description: "Full-stack intern role focused on internal tools for payment reconciliation.",
        date: "04/10/2025",
        requirements: ["Technical"],
        notes: "System design question came up. Mentioned my work with Flask and Elysia.js.",
        status: "Interviewing",
      },
      {
        id: 3,
        company: "Stripe",
        job_position: "Full-stack Intern",
        description: "Full-stack intern role focused on internal tools for payment reconciliation.",
        date: "04/10/2025",
        requirements: ["Technical"],
        notes: "System design question came up. Mentioned my work with Flask and Elysia.js.",
        status: "Interviewing",
      },
      ];

      
  const [showModal, setShowModal] = useState(false);
  const [applications, setApplications] = useState([]);
  const [formData, setFormData] = useState({
    company: '',
    job_position: '',
    job_description: '',
    application_deadline: '',
    application_requirements: '',
    additional_info: '',
    application_status: '',
    user_id: 1,
  });
  

  // GET '/api/applications' -> gets all applications from current user
  const fetchApplications = async () => {
    try {
        const res = await fetch('http://localhost:8080/CSCI201_Final_Project_Backend/api/personaldashboard?userId=1');
        const data = await res.json();
        setApplications(data);
      } catch (err) {
        console.error('Error fetching applications:', err);
      }
  };

  useEffect(() => {
    fetchApplications();
    // setApplications(sampleApplications); // temporary applications for UI
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    // temporary add application for UI
    // const newApplication = {
    //     id: Date.now(), 
    //     company: formData.company,
    //     jobDescription: formData.description,
    //     date: formData.date,
    //     requirements: formData.requirements,
    //     notes: formData.notes,
    //   };
    
      setApplications((prev) => [...prev, newApplication]);
      // setShowModal(false);
      // setFormData({
      //   company: '',
      //   job_position: '',
      //   description: '',
      //   date: '',
      //   requirements: [],
      //   notes: '',
      //   status: '',
      //   user_id: 1,
      // });

    // POST '/api/applications' -> posts new appliation for current user
    try {
        const res = await fetch('http://localhost:8080/CSCI201_Final_Project_Backend/api/applications', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json',
            'Access-Control-Allow-Origin' : '*'
          },
          body: JSON.stringify(formData)
        });
    
        const data = await res.json();
        console.log('Submitted application:', data);
    
        setShowModal(false);
        setFormData({
          company: '',
          job_position: '',
          description: '',
          date: '',
          requirements: [],
          notes: '',
          status: '',
          user_id: 1,
        });
        fetchApplications();
      } catch (err) {
        console.error('Error submitting application:', err);
      }
  };

  return (
    <div className="p-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-white">Personal Dashboard</h1>
        <button
          onClick={() => setShowModal(true)}
          className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded shadow"
        >
          Add an Application
        </button>
      </div>

      {/* Modal */}
      {showModal && <ApplicationModal 
                        handleSubmit={handleSubmit}
                        formData={formData}
                        setFormData={setFormData}
                        setShowModal={setShowModal}
                    />}

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
            <th className="p-3">Actions</th>
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
              <td className="p-3">{app.requirements?.join(', ')}</td>
              <td className="p-3">{app.notes}</td>
              <td className="p-3">{app.status}</td>
              <td className="p-3 space-x-2">
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
              </td>
            </tr>
          ))}
        </tbody>
      </table>

    </div>
  );
}
