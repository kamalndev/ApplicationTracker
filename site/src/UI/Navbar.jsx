import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <nav className="relative flex items-center justify-between bg-[#1e1e2f] text-white px-8 py-4 shadow-md">
      {/* Left section */}
      <div className="flex items-center">
        <div className="text-xl font-bold text-white">MyApp</div>
      </div>

      {/* Center section */}
      <div className="absolute left-1/2 transform -translate-x-1/2">
        <ul className="flex gap-6 list-none m-0 p-0">
          <li>
            <Link
              to="/dashboard"
              className="text-[#cfcfcf] font-medium transition-colors hover:text-white hover:border-b-2 hover:border-white"
            >
              Personal Dashboard
            </Link>
          </li>
          <li>
            <Link
              to="/search"
              className="text-[#cfcfcf] font-medium transition-colors hover:text-white hover:border-b-2 hover:border-white"
            >
              Public Dashboard
            </Link>
          </li>
        </ul>
      </div>

      {/* Right section */}
      <div className="flex items-center">
        <Link
          to="/"
          className="text-[#cfcfcf] font-medium transition-colors hover:text-white hover:border-b-2 hover:border-white"
        >
          Logout
        </Link>
      </div>
    </nav>
  );
}
