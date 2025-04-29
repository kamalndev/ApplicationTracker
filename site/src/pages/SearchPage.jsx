import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function SearchPage() {
    const [term, setTerm] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!term.trim()) return; // to ignore empty seaches
        navigate(`/public?q=${encodeURIComponent(term.trim())}`);
    };

    return (
        <div className="flex flex-col items-center min-h-screen bg-[#242424] px-4 pt-20">
            <h1 className="text-3xl font-bold text-white text-center">Search For Company</h1>
                <form onSubmit={handleSubmit} className="w-full max-w-xl mx-auto mt-10 flex shadow hover: ">
                    <input
                        type="text"
                        placeholder="Enter Company Name"
                        value={term}
                         onChange={(e) => setTerm(e.target.value)}
                        className="flex-1 px-4 py-3 rounded-l-md border border-gray-700 bg-gray-800 text-white focus:outline-none"
                     />
                     <button type={"submit"} className={"px-6 py-3 bg-blue-600 hover:bg-blue-700 cursor-pointer text-white rounded-r-md"}>
                      Search
                     </button>
                 </form>
              </div>
    );
}