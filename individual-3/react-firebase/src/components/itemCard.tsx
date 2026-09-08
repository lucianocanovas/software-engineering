
interface Item {
  id: string;
  title: string;
  description?: string;
}

interface ItemCardProps {
  item: Item;
  onEdit: (item: Item) => void;
  onDelete: (id: string) => void;
}

function ItemCard({ item, onEdit, onDelete }: ItemCardProps) {
  return (
    <div className="item-card">
      <div className="item-id">ID: {item.id.substring(0, 8)}...</div>
      <h3 className="item-title">{item.title}</h3>
      {item.description && (
        <p className="item-description">{item.description}</p>
      )}
      <div className="item-actions">
        <button className="btn-edit" onClick={() => onEdit(item)}>
          Edit
        </button>
        <button className="btn-delete" onClick={() => onDelete(item.id)}>
          Delete
        </button>
      </div>
    </div>
  );
}

export default ItemCard;