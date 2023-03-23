import { Link } from "react-router-dom"
import TestP5 from "./TestP5"
export const NotFound = () => {
    return <div>
        <div className="align-center text-center text-white z-10 relative">
            <h1 className="text-9xl tex">404</h1>
            <h1 className="text-7xl">You seem to be lost.</h1>
        </div>
        <TestP5 />
    </div>
}

export default NotFound;