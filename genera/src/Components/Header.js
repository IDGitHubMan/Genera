export default function Header() {
  return (
    <>
      <h1 className="text-3xl font-bold">Genera</h1>
      <div className="[&>*]:border-2 [&>*]:border-sky-600 [&>*]:mx-8 [&>*]:px-8 justify-end flex">
        <button>Sign In</button>
        <button>Sign Up</button>
        <button>Explore</button>
      </div>
    </>
  );
}
