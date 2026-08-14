import ProductCard from './components/ProductCard';

function App() {
  return (
    <div className="App">
      <h1>Hello, World!</h1>
      <ProductCard product={{
        name: "Sample Product",
        description: "This is a sample product.",
        price: 19.99,
        image: "https://via.placeholder.com/150"
      }} />
    </div>
  )
}

export default App
