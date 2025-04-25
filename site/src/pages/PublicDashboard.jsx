// src/pages/PublicDashboard.jsx
import { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

export default function PublicDashboard() {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    // grab the query-string company name (e.g. ?q=google)
    const rawQ = searchParams.get('q') || '';
    const companyQuery = rawQ.trim().toLowerCase();
    // capitalize first letter:
    const displayCompany = companyQuery
        ? companyQuery[0].toUpperCase() + companyQuery.slice(1)
        : '';

    const [term, setTerm] = useState(rawQ);
    const [applications, setApplications] = useState([]);

    // temporary mock data; replace this with your fetch call later
    const samplePublic = [
        { id:1, company: 'Google', jobDescription: 'STEP intern', date: '12-05-2022 12:11', requirements: ['OA'] },
        { id:2, company: 'Google', jobDescription: 'SDE intern - Seattle', date: '12-05-2022 12:11', requirements: ['OA','Tech'] },
        { id:3, company: 'Google', jobDescription: 'Software Eng Intern - LA', date: '12-05-2022 12:11', requirements: ['OA','Behavioral'] },
    ];

    useEffect(() => {
        // filter mock data by companyQuery
        const filtered = samplePublic.filter(a =>
            a.company.toLowerCase().includes(companyQuery)
        );
        setApplications(filtered);
    }, [companyQuery]);

    const handleSearch = e => {
        e.preventDefault();
        if (!term.trim()) return;
        navigate(`/public?q=${encodeURIComponent(term.trim())}`);
    };

    return (
        <div className="p-8">
            {/* header: title on left, search on right */}
            <div className="flex justify-between items-center mb-10">
                <h1 className="text-3xl font-bold text-white">
                    Public Applications{displayCompany ? ` – ${displayCompany}` : ''}
                </h1>

                <form onSubmit={handleSearch} className="flex ">
                    <input
                        type="text"
                        placeholder="Search company…"
                        value={term}
                        onChange={e => setTerm(e.target.value)}
                        className="px-5 py-2 bg-gray-800 border border-gray-700 text-white rounded-l-md focus:outline-none"
                    />
                    <button
                        type="submit"
                        className="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-r-md whitespace-nowrap"
                    >
                        Search
                    </button>
                </form>
            </div>

            {/* table */}
            <table className="w-full text-left text-white bg-[#1e1e2f] rounded-lg shadow overflow-hidden">
                <thead className="bg-gray-800">
                <tr>
                    <th className="p-3">Company</th>
                    <th className="p-3">Job Description</th>
                    <th className="p-3">Date</th>
                    <th className="p-3">Requirements</th>
                    <th className="p-3">Actions</th>
                </tr>
                </thead>
                <tbody>
                {applications.length > 0 ? (
                    applications.map(app => (
                        <tr
                            key={app.id}
                            className="border-t border-gray-700 hover:bg-gray-700"
                        >
                            <td className="p-3">{app.company}</td>
                            <td className="p-3">{app.jobDescription}</td>
                            <td className="p-3">{app.date}</td>
                            <td className="p-3">{app.requirements.join(', ')}</td>
                            <td className="p-3">
                                <button
                                    className="bg-blue-600 hover:bg-blue-700 text-white px-3 py-1 rounded shadow text-sm whitespace-nowrap"
                                    onClick={() => alert('Coming Soon')}
                                >
                                   More Details
                                </button>
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr className="border-t border-gray-700">
                        <td colSpan={5} className="p-6 text-center text-gray-400">
                            No public applications found for that company.
                        </td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
}
