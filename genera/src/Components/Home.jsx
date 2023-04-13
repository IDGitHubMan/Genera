import HomeAnim from "./HomeAnim";
import Nav from "./Nav";
export const Home = () => {
  return (
    <div>
      <Nav />
      <div className="flex align-center text-left text-white z-10 relative w-1/2 h-10">
        <div>
          <h1 className="text-5xl">Welcome to Genera</h1>
          <p className="text-xl">
            Genera has multiple "systems/gens" that you can use to create
            abstract and vibrant visuals.
          </p>
        </div>
      </div>
      <HomeAnim />
    </div>
  );
};

export default Home;
