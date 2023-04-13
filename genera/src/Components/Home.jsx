import HomeAnim from "./HomeAnim";
export const Home = () => {
  return (
    <div className="align-center align-middle">
      <div className="align-center text-center text-white z-10 relative">
        <h1 className="text-7xl">Welcome to Genera</h1>
        <p className="text-xl">
          Genera has multiple "systems/gens" that you can use to create abstract
          and vibrant visuals.
        </p>
      </div>
      <HomeAnim />
    </div>
  );
};

export default Home;
