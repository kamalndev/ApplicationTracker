import { useState, useEffect } from 'react';
import ApplicationModal from '../UI/ApplicationModal';

export default function PersonalDashboard() {
    // temporary for UI
    const sampleApplications = [
        {
          id: 1,
          company: "Notion",
          jobDescription: "Frontend Engineer Internship working on UI components and design systems.",
          date: "04/01/2025",
          requirements: ["OA", "Technical"],
          notes: "Asked about my work with custom design systems. OA was LeetCode-style.",
        },
        {
          id: 2,
          company: "Duolingo",
          jobDescription: "Backend Software Engineering Internship focusing on infrastructure and scalability.",
          date: "03/28/2025",
          requirements: ["Behavioral", "Other"],
          notes: "Behavioral was very casual. Talked a lot about data structures and my project Ekko.",
        },
        {
          id: 3,
          company: "Stripe",
          jobDescription: "Full-stack intern role focused on internal tools for payment reconciliation.",
          date: "04/10/2025",
          requirements: ["Technical"],
          notes: "System design question came up. Mentioned my work with Flask and Elysia.js.",
        },
      ];

      
  const [showModal, setShowModal] = useState(false);
  const [applications, setApplications] = useState([]);
  const [formData, setFormData] = useState({
    company: '',
    description: '',
    date: '',
    requirements: [],
    notes: '',
  });

  // once backend ready
  const fetchApplications = async () => {
    try {
        const res = await fetch('/api/applications');
        const data = await res.json();
        setApplications(data);
      } catch (err) {
        console.error('Error fetching applications:', err);
      }
  };

  useEffect(() => {
    // fetchApplications();
    setApplications(sampleApplications); // temporary applications for UI
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    // temporary add application for UI
    const newApplication = {
        id: Date.now(), 
        company: formData.company,
        jobDescription: formData.description,
        date: formData.date,
        requirements: formData.requirements,
        notes: formData.notes,
      };
    
      setApplications((prev) => [...prev, newApplication]);
      setShowModal(false);
      setFormData({
        company: '',
        description: '',
        date: '',
        requirements: [],
        notes: '',
      });

    // once backend is ready 
    // try {
    //     const res = await fetch('/api/applications', {
    //       method: 'POST',
    //       headers: { 'Content-Type': 'application/json' },
    //       body: JSON.stringify(formData)
    //     });
    
    //     const data = await res.json();
    //     console.log('Submitted application:', data);
    
    //     setShowModal(false);
    //     setFormData({
    //         company: '',
    //         description: '',
    //         date: '',
    //         requirements: [],
    //         notes: '',
    //       });
    //     fetchApplications();
    //   } catch (err) {
    //     console.error('Error submitting application:', err);
    //   }
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
            <th className="p-3">Date</th>
            <th className="p-3">Requirements</th>
            <th className="p-3">Actions</th>
          </tr>
        </thead>
        <tbody>
          {applications.map((app) => (
            <tr
              key={app.id}
              className="border-t border-gray-700 hover:bg-gray-700 cursor-pointer"
            //   onClick={() => window.location.href = `/application/${app.id}`}
            >
              <td className="p-3">{app.company}</td>
              <td className="p-3">{app.date}</td>
              <td className="p-3">{app.requirements.join(', ')}</td>
              <td className="p-3 space-x-2">
                <button className="text-yellow-400 hover:underline" onClick={(e) => { e.stopPropagation(); alert('Edit feature coming soon'); }}>Edit</button>
                <button className="text-red-400 hover:underline" onClick={(e) => { e.stopPropagation(); alert('Delete feature coming soon'); }}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
