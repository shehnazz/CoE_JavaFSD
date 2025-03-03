import { Link } from "react-router-dom";
import { useCart } from "./CartContext";

const Navbar = () => {
  const { cart } = useCart();

  return (
    <nav className="p-4 bg-blue-500 text-white flex justify-between">
      <Link to="/" className="text-xl font-bold">E-Commerce</Link>
      <Link to="/cart" className="text-lg">Cart ({cart.length})</Link>
    </nav>
  );
};

export default Navbar;
