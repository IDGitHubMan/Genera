import { Link } from "react-router-dom";
export const Nav = () => {
  return (
    <div className="text-white z-30 text-xl flex justify-between sticky h-10">
      <div className="flex justify-around w-4/5">
        <Link to="/">Genera</Link>
        <Link to="/gallery">Gallery</Link>
        <Link to="/gens">Generators</Link>
        <Link to="/about">About</Link>
      </div>
    </div>
  );
};

export default Nav;
