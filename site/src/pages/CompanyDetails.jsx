import { useNavigate, useSearchParams } from 'react-router-dom';
import { useState, useEffect } from 'react';

const API_URL = import.meta.env.VITE_API_URL;

export default function CompanyDetails() {
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const companyName = searchParams.get('name') || '';

    const [company, setCompany] = useState(null);

    useEffect(() => {
        if (!companyName) return;

        fetch(`${API_URL}/api/publicdashboard`)
            .then(res => {
                if (!res.ok) throw new Error('Fetch failed');
                return res.json();
            })
            .then(companies => {
                const found = companies.find(
                    c => c.name.toLowerCase() === companyName.toLowerCase()
                );
                setCompany(found || { name: companyName, numApps: 0, jobApps: [] });
            })
            .catch(err => {
                console.error('Error loading company details:', err);
                setCompany({ name: companyName, numApps: 0, jobApps: [] });
            });
    }, [companyName]);

    if (!company) {
        return <p className="p-8 text-white">Please Search For Company</p>;
    }

    return (
        <div className="p-8">
            <button
                className="mb-4 text-sm text-blue-400 hover:underline"
                onClick={() => navigate(-1)}
            >
                ← Back
            </button>

            <h1 className="text-3xl font-bold text-white mb-2">
                Company Details – {company.name}
            </h1>
            <p className="text-gray-300 mb-6">
                Total public posts: {company.numApps}
            </p>

            <table className="w-full text-left text-white bg-[#1e1e2f] rounded-lg shadow overflow-hidden">
                <thead className="bg-gray-800">
                <tr>
                    <th className="p-3">Position</th>
                    <th className="p-3">Deadline</th>
                    <th className="p-3">Requirements</th>
                    <th className="p-3">Notes</th>
                    <th className="p-3">Status</th>
                </tr>
                </thead>
                <tbody>
                {company.jobApps.map(app => (
                    <tr
                        key={app.appId}
                        className="border-t border-gray-700 hover:bg-gray-700"
                    >
                        <td className="p-3">{app.jobPosition}</td>
                        <td className="p-3">{app.dueDate}</td>
                        <td className="p-3">{app.requirements}</td>
                        <td className="p-3">{app.notes}</td>
                        <td className="p-3">{app.appStatus}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
