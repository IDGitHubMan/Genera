import TestP5 from "./404Stars";
export const NotFound = () => {
  return (
    <div className="align-center text-center">
      <div className="align-center text-white z-10 relative">
        <h1 className="text-9xl tex">404</h1>
        <h1 className="text-7xl">You seem to be lost.</h1>
        <p className="text-xl">
          There's nothing here yet. There might be in some time though.
        </p>
      </div>
      <TestP5 />
    </div>
  );
};

export default NotFound;
