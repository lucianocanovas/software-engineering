interface Product {
  name: string;
  description: string;
  price: number;
  image: string;
}

function ProductCard({ product }: { product: Product }) {
  return (
    <div className="product-card">
      <img src={product.image} alt={product.name} />
      <h2>{product.name}</h2>
      <p>{product.description}</p>
      <span>${product.price.toFixed(2)}</span>
      <button className="edit-button">Edit</button>
      <button className="delete-button">Delete</button>
    </div>
  );
}

export default ProductCard;