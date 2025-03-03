import { useCart } from "./CartContext";

const CartPage = () => {
  const { cart, dispatch } = useCart();
  const totalPrice = cart.reduce((sum, item) => sum + item.price, 0);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Shopping Cart</h1>
      {cart.length === 0 ? <p>Your cart is empty.</p> : (
        <div>
          {cart.map(item => (
            <div key={item.id} className="flex justify-between border p-4 mb-2">
              <span>{item.name} - ${item.price}</span>
              <button
                className="bg-red-500 text-white px-4 py-1 rounded"
                onClick={() => dispatch({ type: "REMOVE_FROM_CART", payload: item.id })}
              >
                Remove
              </button>
            </div>
          ))}
          <h2 className="mt-4 text-xl">Total: ${totalPrice}</h2>
        </div>
      )}
    </div>
  );
};

export default CartPage;
